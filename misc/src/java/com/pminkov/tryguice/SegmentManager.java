package com.pminkov.tryguice;

import com.google.inject.Inject;

public class SegmentManager {
  @Inject
  private Clock myClock;

  StatsReceiver statsReceiver;

  Clock backupClock;

  @Inject
  public SegmentManager(StatsReceiver statsReceiver) {
    System.out.println("constructor");

    backupClock = new ProdClock("backup");
    this.statsReceiver = statsReceiver;
  }

  void hello() {
    System.out.println("SegmentManager::hello");
    myClock.printTime();
    backupClock.printTime();

    statsReceiver.stat();
  }
}
