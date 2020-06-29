package com.pminkov.tryguice;

import com.google.inject.Inject;

public class StatsReceiver {
  String prefix;

  @Inject
  Clock myclock;

  @Inject
  public StatsReceiver(String prefix) {
    System.out.println("Creating stats receiver");
    this.prefix = prefix;
  }

  void stat() {
    System.out.println("[" + prefix + "] stat called");
  }
}
