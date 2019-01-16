package com.pminkov.learnjava;

import org.junit.Test;
import org.junit.Assert;

public class TestLearnJava extends Assert {
  @Test
  public void testSomething() {
    System.out.println("ran test");
    assertEquals(5, 16);

  }
}
