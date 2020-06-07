package com.pminkov.tryguice;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TryGuice {
  void run() {
    Injector injector = Guice.createInjector(new BasicModule());

    SegmentManager segmentManager = injector.getInstance(
        SegmentManager.class);

    System.out.println(segmentManager);

    segmentManager.hello();

  }

  public static void main(String[] args) {
    System.out.println("what's up");

    TryGuice tg = new TryGuice();
    tg.run();
  }
}