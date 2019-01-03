package com.pminkov.learnjava;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class Exercise {
  abstract public void run();
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

public class LearnJava {
  public static void main(String[] args) {
      GenericFunctionalInterfaces fi  = new GenericFunctionalInterfaces();
      fi.run();
  }
}
