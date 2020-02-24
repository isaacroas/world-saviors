package com.saviors.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saviors.util.Utils;

public abstract class AbstractDataGeneratorService {

  private static final Logger logger = LogManager.getLogger(AbstractDataGeneratorService.class);
  
  private Long currentCount;
  
  public Long getNextCount() {
    if (this.currentCount == null) {
      this.currentCount = this.generateInitialValue();
    }
    this.currentCount = this.currentCount + this.generateIncrement();
    logger.info("New count is {}", this.currentCount);
    return this.currentCount;
  }
  
  protected abstract Long getInitialCount();
  
  protected abstract Integer getInitialAverageIncrement();
  
  protected abstract Integer getInitialIncrementRange();
  
  protected abstract Integer getAverageIncrement();
  
  protected abstract Integer getIncrementRange();
  
  private Long generateInitialValue() {
    Long intialCount = getInitialCount();
    Integer initialAverageIncrement = getInitialAverageIncrement();
    Integer initialIncrementRange  = getInitialIncrementRange();
    Long result = intialCount + Utils.getRandomIncrementInRange(initialAverageIncrement, initialIncrementRange);
    logger.info("Initial count is {}", result);
    return result;
  }

  private Integer generateIncrement() {
    Integer averageIncrement = getAverageIncrement();
    Integer incrementRange = getIncrementRange();
    return Utils.getRandomIncrementInRange(averageIncrement, incrementRange);
  }

}
