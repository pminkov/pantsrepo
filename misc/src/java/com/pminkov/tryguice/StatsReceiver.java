package com.pminkov.tryguice;

import com.google.inject.Inject;

public class StatsReceiver {
  String prefix;

  @Inject
  Clock myclock;

  @Inject
  public StatsReceiver(String prefix) {
    this.prefix = prefix;
  }

  void stat() {
    System.out.println("[" + prefix + "] stat called");
  }
}
