package com.pminkov.tryguice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TryGuice {
  void run() {
    Injector injector = Guice.createInjector(new BasicModule());

    SegmentManager segmentManager = injector.getInstance(
        SegmentManager.class);
    SegmentManager segmentManager2 = injector.getInstance(
        SegmentManager.class);

    System.out.println(segmentManager);
    System.out.println(segmentManager2);

    segmentManager.hello();
    segmentManager2.hello();
  }

  public static void main(String[] args) {
    TryGuice tg = new TryGuice();
    tg.run();
  }
}
