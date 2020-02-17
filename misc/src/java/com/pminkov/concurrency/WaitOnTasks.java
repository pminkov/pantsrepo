package com.pminkov.concurrency;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


class Task implements Runnable {
  private boolean ranOnce = false;
  private CountDownLatch countDownLatch;
  private Random random;

  public Task(Random random, CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
    this.random = random;
  }

  @Override
  public void run() {
    int sleepAmount = 1000 + random.nextInt(1000);
    try {
      System.out.println("Sleeping for " + sleepAmount);
      Thread.sleep(sleepAmount);
      System.out.println("Done sleeping for " + sleepAmount);
    } catch (InterruptedException ex) {
      System.out.println("Interrupted, not finishing the sleep");
    }

    if (!ranOnce) {
      countDownLatch.countDown();
      System.out.println("Decreased CDL to: " + countDownLatch.getCount());
    }
    ranOnce = true;
  }
}

class TaskList {
  private ArrayList<ScheduledFuture<?>> futures = new ArrayList<ScheduledFuture<?>>();
  private CountDownLatch countDownLatch;
  private final CompletableFuture<String> allRanOnceOrCancelled;

  public TaskList(ScheduledExecutorService executorService, int n) {
    Random random = new Random();
    countDownLatch = new CountDownLatch(n);

    allRanOnceOrCancelled = CompletableFuture.supplyAsync(() -> {
      try {
        countDownLatch.await();
      } catch (InterruptedException ex) {}
      System.out.println("all ran once finished");
      return "completed";
    });

    for (int i = 0; i < n; i++) {
      Task t = new Task(random, countDownLatch);
      futures.add(executorService.scheduleWithFixedDelay(t,
          0, 200, TimeUnit.MILLISECONDS));
    }
  }

  public void cancel() {
    for (int i = 0; i < futures.size(); i++) {
      futures.get(i).cancel(false);
    }
    allRanOnceOrCancelled.complete("cancelled");
  }

  public CompletableFuture<String> getAllRanOnceOrCanclledFuture() {
    return allRanOnceOrCancelled;
  }
}

class TaskListManager {
  private ScheduledExecutorService executorService =
      Executors.newScheduledThreadPool(8);

  void run() {
    TaskList tl = new TaskList(executorService,2);

    tl.getAllRanOnceOrCanclledFuture().thenAcceptAsync((String message) -> {
      System.out.println("DONE:" + message);
    });

    try {
      Thread.sleep(500);
      tl.cancel();
    } catch (InterruptedException ex) {}

    executorService.shutdown();
  }
}

public class WaitOnTasks {
  public void run() {
    TaskListManager tlm = new TaskListManager();
    tlm.run();
  }
}
