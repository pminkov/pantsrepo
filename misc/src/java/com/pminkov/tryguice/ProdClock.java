package com.pminkov.tryguice;

public class ProdClock implements Clock {
  String name;

  public ProdClock() {
    this("default");
  }

  public ProdClock(String name) {
    this.name = name;
  }

  @Override
  public void printTime() {
    System.out.println("Time is [prod-" + name + "]");
  }
}
