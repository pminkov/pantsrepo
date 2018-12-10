
// Copyright 2015 Pants project contributors (see CONTRIBUTORS.md).
// Licensed under the Apache License, Version 2.0 (see LICENSE).

package com.pminkov.search;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple example that is runnable from within pants:
 *
 * ./pants run examples/src/java/org/pantsbuild/example/hello/simple
 *
 */

public class HelloWorld {
  public static String readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    String contents = new String(encoded, Charset.defaultCharset());

    String[] lines = contents.split("\\n");
    System.out.println("Number of lines: " + lines.length);
    System.out.println("Number of characters: " + contents.length());

    HashMap<String, ArrayList<Integer>> invertedIndex = new HashMap<>();

    for (int li = 0; li < lines.length; li++) {
        String[] words = lines[li].split("\\s+");

        for (int wi = 0; wi < words.length; wi++) {
          String word = words[wi];
          invertedIndex.putIfAbsent(word, new ArrayList<>());
          invertedIndex.get(word).add(li);
        }
    }

    // Print words with biggest posting lists.
    //for (Map.Entry<String, ArrayList<Integer>> invertedIndex: map.entrySet())

    return contents;
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
    System.out.println("Working Directory = " +
        System.getProperty("user.dir"));

    try {
      readFile("./search/data/big.txt");
    } catch(IOException e) {
      System.out.println("ioexception");
    }

  }
}
