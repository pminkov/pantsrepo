package com.pminkov.learnjava;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

abstract class Exercise {
  abstract public void run();
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
      LuckySort ls = new LuckySort();

      ls.run();
  }
}
