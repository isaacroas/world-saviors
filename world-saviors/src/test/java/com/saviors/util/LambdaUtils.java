package com.saviors.util;

import java.util.stream.IntStream;

public class LambdaUtils {
  
  private LambdaUtils() { }
  
  public static void repeat(int count, Runnable action) {
    IntStream.range(0, count).forEach(i -> action.run());
  }

}
