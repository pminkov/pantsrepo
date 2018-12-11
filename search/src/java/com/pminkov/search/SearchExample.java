package com.pminkov.search;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

class CustomComparator implements Comparator<Entry<String, ArrayList<Integer>>> {
  @Override
  public int compare(Entry<String, ArrayList<Integer>> a, Entry<String, ArrayList<Integer>> b) {
    return b.getValue().size() - a.getValue().size();
  }
}

class Corpus {
  private HashMap<String, ArrayList<Integer>> invertedIndex;
  private ArrayList<String> documents;

  Corpus(String fileName) {
    try {
      readFile("./search/data/big.txt");
    } catch(IOException e) {
      System.out.println("ioexception");
    }
  }

  void printMostCommonWords(HashMap<String, ArrayList<Integer>> invertedIndex) {
    final int SIZE = 10;
    // Print words with biggest posting lists.
    ArrayList<Entry<String, ArrayList<Integer>>> elements = new ArrayList<>(invertedIndex.entrySet());
    System.out.println("Number of unique words: " + elements.size());
    Collections.sort(elements, new CustomComparator());
    for (int i = 0; i < Math.min(SIZE, elements.size()); i++) {
      Entry<String, ArrayList<Integer>> entry = elements.get(i);
      System.out.println(entry.getKey() + " " + entry.getValue().size());
    }
  }

  private void readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    String contents = new String(encoded, Charset.defaultCharset());

    String[] lines = contents.split("\\n");
    System.out.println("Number of lines: " + lines.length);
    System.out.println("Number of characters: " + contents.length());

    invertedIndex = new HashMap<>();
    documents = new ArrayList<String>();

    for (int li = 0; li < lines.length; li++) {
      String[] words = lines[li].split("\\s+");

      for (int wi = 0; wi < words.length; wi++) {
        String word = words[wi];
        invertedIndex.putIfAbsent(word, new ArrayList<>());
        invertedIndex.get(word).add(li);
      }

      documents.add(lines[li]);
    }

    printMostCommonWords(invertedIndex);
  }

  void runQuery(String query) {
    String[] words = query.split("\\s+");

    Set<Integer> docIds = null;
    ArrayList<Integer> defaultPostingList = new ArrayList<>();
    for (int i = 0; i < words.length; i++) {
      ArrayList<Integer> postingList = invertedIndex.getOrDefault(words[i], defaultPostingList);
      if (docIds == null) {
        docIds = new HashSet<>(postingList);
      } else {
        docIds.retainAll(postingList);
      }
    }

    if (docIds.size() == 0) {
      System.out.println("No results");
    } else {
      System.out.println("Number of results: " + docIds.size());
      int count = 0;
      for (Integer docId : docIds) {
        count++;
        System.out.println(docId + " " + documents.get(docId));
        if (count == 0) {
          break;
        }
      }
    }
  }
}

public class SearchExample {

  public static void main(String[] args) {
    Corpus corpus = new Corpus("./search/data/big.txt");

    Scanner keyboard = new Scanner(System.in);
    try {
      while (true) {
        String line = keyboard.nextLine();
        corpus.runQuery(line);
      }
    } catch (NoSuchElementException e) {
        System.out.println("Thanks!");
    }
  }
}
