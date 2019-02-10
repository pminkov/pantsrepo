package com.pminkov.learnjava;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Assert;

class UseScheduledExecutorService {
  private final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(1);

  public void run() {
    Runnable saySomething = new Runnable() {
      private int count = 0;

      public void run() {
        count++;
        System.out.println("Just saying " + count);
      }
    };

    scheduler.scheduleWithFixedDelay(saySomething, 0, 1, TimeUnit.SECONDS);

  }
}

public class TestLearnJava extends Assert {
  @Test
  public void testSomething() {
    UseScheduledExecutorService ss = new UseScheduledExecutorService();
    ss.run();

    try {
      Thread.sleep(1000 * 10);
    } catch (InterruptedException ex) {}

  }
}
