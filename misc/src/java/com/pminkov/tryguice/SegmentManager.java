package com.pminkov.tryguice;

import com.google.inject.Inject;

public class SegmentManager {
  @Inject
  Clock myClock;

  Clock backupClock;

  public SegmentManager() {
    System.out.println("constructor");

    backupClock = new ProdClock("backup");
  }

  void hello() {
    System.out.println("SegmentManager::hello");
    myClock.printTime();
    backupClock.printTime();
  }
}
