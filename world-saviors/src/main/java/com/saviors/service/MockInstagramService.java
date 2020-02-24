package com.saviors.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.saviors.config.Configuration;
import com.saviors.util.Utils;

@ApplicationScoped
@Named
public class MockInstagramService {

  private static final Logger logger = LogManager.getLogger(MockInstagramService.class);
  
  @Inject
  private Configuration configuration;

  private Long currentfollowersCount;

  @PostConstruct
  private void postConstruct() {
    this.currentfollowersCount = this.generateInitialValue();
    logger.info("Influencer initial followers number is {}", this.currentfollowersCount);
  }
  
  public Long getFollowersCount() {
    this.currentfollowersCount = this.currentfollowersCount + this.generateIncrement();
    logger.info("Influencer new followers count is {}", this.currentfollowersCount);
    return this.currentfollowersCount;
  }
  
  private Long generateInitialValue() {
    Long influencerInitialFollowers = this.configuration.getLong(Configuration.INFLUENCER_INTIAL_FOLLOWERS);
    Integer influencerInitialAverageIncrement = this.configuration.getInt(Configuration.INFLUENCER_INTIAL_AVERAGE_INCREMENT);
    Integer influencerInitialIncrementRange  = this.configuration.getInt(Configuration.INFLUENCER_INTIAL_INCREMENT_RANGE);
    return influencerInitialFollowers + Utils.getRandomIncrementInRange(influencerInitialAverageIncrement, influencerInitialIncrementRange);
  }

  private Integer generateIncrement() {
    Integer influencerAverageIncrement = this.configuration.getInt(Configuration.INFLUENCER_AVERAGE_INCREMENT);
    Integer influencerIncrementRange = this.configuration.getInt(Configuration.INFLUENCER_INCREMENT_RANGE);
    return Utils.getRandomIncrementInRange(influencerAverageIncrement, influencerIncrementRange);
  }

}
