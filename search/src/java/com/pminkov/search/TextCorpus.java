package com.pminkov.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import com.google.common.base.Stopwatch;


public class TextCorpus {
  private String[] words;
  private final Random random = new Random(12321);

  public TextCorpus() {
    this.words = null;
  }

  void load() {
    Stopwatch stopwatch = Stopwatch.createStarted();

    Path path = Paths.get(Search.DIR_BASE, "big.txt");
    String corpus;
    try {
      corpus = new String(Files.readAllBytes(path));
      words = corpus.split(" ");
      System.out.println("Number of words: " + words.length);
    } catch (IOException ex) {
      System.out.println("PROBLEM!");
      System.exit(1);
    }

    System.out.println("Elapsed: " + stopwatch.toString());
  }

  String giveMeWord() {
    return this.words[random.nextInt(this.words.length)];
  }

  String generateTweet() {
    int numWords = 10 + random.nextInt(10);
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < numWords; i++) {
      if (i > 0) {
        stringBuilder.append(' ');
      }
      stringBuilder.append(giveMeWord());
    }
    return stringBuilder.toString();
  }
}
