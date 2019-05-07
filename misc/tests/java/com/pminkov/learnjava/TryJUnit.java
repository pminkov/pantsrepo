package com.pminkov.learnjava;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TryJUnit extends Assert {
  public class Flower {
    public Flower() {
      System.out.println("Flower constructed");
    }
  }

  private Flower flower = new Flower();

  private java.util.List emptyList;

  /**
   * Sets up the test fixture.
   * (Called before every test case method.)
   */
  @Before
  public void setUp() {
    System.out.println("setUp");
    emptyList = new java.util.ArrayList();
  }

  /**
   * Tears down the test fixture.
   * (Called after every test case method.)
   */
  @After
  public void tearDown() {
    System.out.println("tearDown");
    emptyList = null;
  }

  @Test
  public void testSomeBehavior() {
    System.out.println("testSomeBehavior");
    assertEquals("Empty list should have 0 elements", 0, emptyList.size());
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testForException() {
    System.out.println("testForException");
    Object o = emptyList.get(0);
  }
}
