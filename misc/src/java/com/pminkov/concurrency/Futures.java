package com.pminkov.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SquareCalculator {
  private ExecutorService executorService =
      Executors.newSingleThreadExecutor();

  public void run(Integer input) {
    Future<Integer> sqf =  executorService.submit(() -> {
      Thread.sleep(1000);
      return input * input;
    });

    try {
        System.out.println(sqf.get());
    } catch (Exception ex) {}

    executorService.shutdown();
  }
}

class SquareCalculatorWithThreads {
  public void calculate(Integer input) {
    final int[] answer = new int[1];
    Thread t = new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {

      }
      answer[0] = input * input;
    });

    t.start();
    try {
      t.join();
    } catch (InterruptedException ex) {}

    System.out.println(answer[0]);
  }

  public void run() {

  }
}

class InvalidInputException extends Exception {
}

class SquareCalculatorWithCompletableFuture {
  CompletableFuture<Integer> calculate(Integer input) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000);
        System.out.println("Returning value...");
      } catch (InterruptedException ex) { }
      if (input == 25) {
        //throw new InvalidInputException();
      }
      return input * input;
    });
  }
  void run() {
    CompletableFuture<Integer> calcFuture = calculate(15);
    CompletableFuture<Void> printFuture = calcFuture.thenAccept((result) -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {}
      System.out.println("The result is " + result);
    });

    try {
      printFuture.get();
    } catch (Exception ex) {}
  }
}

public class Futures {
  public static void run() {
    SquareCalculatorWithCompletableFuture cc = new SquareCalculatorWithCompletableFuture();

    cc.run();
  }
}
