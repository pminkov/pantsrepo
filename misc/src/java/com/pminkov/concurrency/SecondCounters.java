package com.pminkov.concurrency;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Incrementer {
  private long count;
  public Incrementer() {

  }
  public void incrementContinuously() {
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        System.out.println("Interrupted. Done.");
        break;
      }
      count += 1;
    }
  }
  public void incrementContinuouslyWithThreads() {
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        System.out.println("Interrupted. Done.");
        break;
      }
      Thread t = new Thread(() -> {
        count += 1;
      });
      t.start();
      try {
        t.join();
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public void increment() {
    count += 1;
  }
  public long getCount() {
    return count;
  }
}

public class SecondCounters {
  private static void countWithThread() {
    System.out.println("Counting in a thread:\n");
    Incrementer ti = new Incrementer();
    Thread t = new Thread(ti::incrementContinuously);
    t.start();

    try {
      Thread.sleep(1000);
      t.interrupt();
      t.join();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }

    System.out.printf("%,d\n", ti.getCount());

    long ts = System.currentTimeMillis();
    long count = ti.getCount();
    long newCount = 0;
    // Some elaborate counting that probably won't be figured out by the compiler.
    for (long i = 0; i < count; i++) {
      if (i % 2 == 0) {
        newCount += 2;
      }
      if (i % 5 == 0) {
        newCount += 1;
      }
      if (i % 10 == 0) {
        newCount -= 2;
      }
    }
    long te = System.currentTimeMillis();
    double duration = (te - ts) / 1000.0;

    System.out.printf("Did the same count in a loop for %.3f seconds %d.\n", duration, newCount);
  }

  private static void countWithScheduledExecutorService() {
    System.out.println("Counting with ScheduledExecutorService");
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    Incrementer ti = new Incrementer();
    scheduler.scheduleWithFixedDelay(ti::increment,
        0, 1, TimeUnit.NANOSECONDS
    );
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
    scheduler.shutdown();
    System.out.println("isShutdown: " + scheduler.isShutdown());
    System.out.printf("%,d\n", ti.getCount());
  }

  private static void countWithThreadPerIncrement() {
    System.out.println("Counting by starting a new thread for each increment:\n");
    Incrementer ti = new Incrementer();

    Thread t = new Thread(ti::incrementContinuouslyWithThreads);
    t.start();

    try {
      Thread.sleep(1000);
      t.interrupt();
      t.join();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }

    System.out.printf("%,d\n", ti.getCount());
  }

  private static void countWithMultipleThreads() {
    int TCOUNT = 32;
    Incrementer incrementers[] = new Incrementer[TCOUNT];
    Thread threads[] = new Thread[TCOUNT];

    for (int i = 0; i < TCOUNT; i++) {
      incrementers[i] = new Incrementer();
      threads[i] = new Thread(incrementers[i]::incrementContinuously);
      threads[i].start();
    }

    try {
      Thread.sleep(1000);
      long res = 0;
      for (int i = 0; i < TCOUNT; i++) {
        threads[i].interrupt();
      }

      for (int i = 0; i < TCOUNT; i++) {
        threads[i].join();
        System.out.printf("thread: %d %,d\n", i, incrementers[i].getCount());
        res += incrementers[i].getCount();
      }
      System.out.printf("Final result: %,d", res);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }

  }

  public void run() {
    countWithThread();
    countWithScheduledExecutorService();
    countWithThreadPerIncrement();
    countWithMultipleThreads();
  }
}
