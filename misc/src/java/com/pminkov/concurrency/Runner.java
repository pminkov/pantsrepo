package com.pminkov.concurrency;

public class Runner {
  public static void main(String[] args) {
    WaitOnTasks w = new WaitOnTasks();
    w.run();
  }
}
