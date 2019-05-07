package com.pminkov.learnjava;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.lang.IllegalArgumentException;
import java.util.stream.Collectors;

import static java.util.Optional.of;

abstract class Exercise {
  abstract public void run();
}

class UseScheduledExecutorService extends Exercise {
  private final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(1);

  @Override
  public void run() {
    Runnable saySomething = new Runnable() {
      private int count = 0;

      public void run() {
        count++;
        System.out.println("Just saying " + count);
      }
    };

    scheduler.scheduleWithFixedDelay(saySomething, 0, 1, TimeUnit.SECONDS);

    try {
      Thread.sleep(1000 * 10);
    } catch (InterruptedException ex) {

    }
  }
}

class BasicConcurrency extends Exercise {
  @Override
  public void run() {
    Executor executor = Executors.newCachedThreadPool();
    executor.execute(() -> {
      for (int i = 0; i < 10; i++) {
        System.out.println("Hello");
      }
    });
  }
}

class BasicStreams extends Exercise {
  @Override
  public void run() {
    String contents = null;
    try {
      contents = new String(Files.readAllBytes(
          Paths.get("./learnjava/data/big.txt")
      ));
    } catch (IOException e) {
      System.out.println("Oops. Problem reading input.");
      return;
    }

    List<String> words = Arrays.asList(contents.split("\\PL+"));
    System.out.printf("Number of words: %d", words.size());

//    Object[] longWords = words.stream().filter(w -> w.length() > 12).collect(Collectors.toList());

  }
}

class FunctionalInterfaces extends Exercise {
  interface IntFilter {
    abstract boolean test(int value);
  }

  public boolean test(IntFilter fi, int value) {
    return fi.test(value);
  }

  @Override
  public void run() {
    List<Integer> alist = Arrays.asList(-1, 5, 16, 3, -2, 18);
    ArrayList<Integer> blist = new ArrayList<>();

    for (Integer x : alist) {
      if (test(v -> v > 0, x)) {
        blist.add(x);
      }
    }

    System.out.println(alist.toString());
    System.out.println(blist.toString());
  }
}


class GenericFunctionalInterfaces extends Exercise {
  interface Filter<T> {
    abstract boolean test(T value);
  }

  public <T> boolean test(Filter<T> fi, T value) {
    return fi.test(value);
  }

  void filterInts() {
    List<Integer> alist = Arrays.asList(-1, 5, 16, 3, -2, 18);
    ArrayList<Integer> blist = new ArrayList<>();

    for (Integer x : alist) {
      if (test(v -> v > 0, x)) {
        blist.add(x);
      }
    }

    System.out.println(alist.toString());
    System.out.println(blist.toString());
  }

  void filterStrings() {
    List<String> alist = Arrays.asList("petko", "was", "here", "toWriteSomeJava");
    ArrayList<String> blist = new ArrayList<>();

    for (String x : alist) {
      if (test(v -> v.length() >= 5, x)) {
        blist.add(x);
      }
    }

    System.out.println(alist.toString());
    System.out.println(blist.toString());
  }

  @Override
  public void run() {
    filterInts();
    filterStrings();
  }
}


class LuckySort extends Exercise {
  public void run() {
    System.out.println("running LS");

    int N = 15;
    ArrayList<Integer> data = new ArrayList<>();
    ArrayList<Integer> best = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      data.add(new Integer(i + 1));
      best.add(new Integer(0));
    }

    int bestSortedness = Integer.MAX_VALUE;
    int MIL = 1000000;
    for (int si = 0; si < 150 * MIL; si++) {
      Collections.shuffle(data);

      int sortedness = 0;
      for (int i = 0; i < data.size() - 1; i++) {
        // How close a number is to its desired position.
        sortedness += Math.abs(data.get(i) - 1 - i);
      }

      if (sortedness < bestSortedness) {
        bestSortedness = sortedness;
        Collections.copy(best, data);

        System.out.printf("Iteration %d. Sortedness = %d", si, sortedness);
        System.out.println(Arrays.toString(best.toArray()));
      }
    }
  }
}

class SomethingElse {
  void hello() {
    System.out.println("Hello");

  }
}

class AndMore extends SomethingElse {
  void moreHello() {
    this.hello();
  }
}

class Optionals extends Exercise {
/*
  private Optional<Integer> findUserId(String name) {
    if (name == "petko")
  }
*/
  @Override
  public void run() {
    SomethingElse se = new SomethingElse();

    se.hello();

    AndMore am = new AndMore();

    am.moreHello();
  }
}

class Exceptions extends Exercise {
  private int summer(int a ,int b) throws Exception {
    if (a < 0 || b < 0) {
      throw new IllegalArgumentException("negative numbers");
    } else if (a + b > 100) {
      throw new Exception("sum too big");
    } else {
      return a + b;
    }
  }
  @Override
  public void run() {
    try {
      try {
        int xx = summer(5, -3);
        System.out.println(xx);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        throw e;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Throwable tt) {
      tt.printStackTrace();
    }
  }
}

public class LearnJava {
  public static void main(String[] args) {
      Exceptions exercise = new Exceptions();
      exercise.run();
  }
}
