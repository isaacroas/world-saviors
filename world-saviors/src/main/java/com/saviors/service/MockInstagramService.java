package com.saviors.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saviors.config.Configuration;

@ApplicationScoped
@Named
public class MockInstagramService extends AbstractDataGeneratorService {

  private static final Logger logger = LogManager.getLogger(MockInstagramService.class);
  
  @Inject
  private Configuration configuration;

  @Override
  protected Long getInitialCount() {
    return this.configuration.getLong(Configuration.INFLUENCER_INITIAL_FOLLOWERS);
  }

  @Override
  protected Integer getInitialAverageIncrement() {
    return this.configuration.getInt(Configuration.INFLUENCER_INITIAL_AVERAGE_INCREMENT);
  }

  @Override
  protected Integer getInitialIncrementRange() {
    return this.configuration.getInt(Configuration.INFLUENCER_INITIAL_INCREMENT_RANGE);
  }

  @Override
  protected Integer getAverageIncrement() {
    return this.configuration.getInt(Configuration.INFLUENCER_AVERAGE_INCREMENT);
  }

  @Override
  protected Integer getIncrementRange() {
    return this.configuration.getInt(Configuration.INFLUENCER_INCREMENT_RANGE);
  }

}
