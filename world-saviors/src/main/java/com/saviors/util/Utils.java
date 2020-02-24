package com.saviors.util;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
  
  private static final Logger logger = LogManager.getLogger(Utils.class);
  
  private Utils() { }
  
  public static int getRandomIncrementInRange(Integer increment, Integer range) {
    Integer max = (range / 2);
    Integer min = (range / 2) - (range / 2 * 2);
    if (increment > 0) {
      max += increment;
    } else {
      min += increment;
    }
    return getRandomIntInRange(min, max);
  }

  private static int getRandomIntInRange(Integer min, Integer max) {
    logger.debug("Generate random number between {} and {}", min, max);
    Integer bound = (max - min);
    Integer randomInt = new Random().nextInt(bound);
    Integer result = randomInt + min;
    logger.debug("The number generated is {}", result);
    return result;
  }

}
