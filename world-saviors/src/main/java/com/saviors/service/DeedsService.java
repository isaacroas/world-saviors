package com.saviors.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saviors.config.Configuration;
import com.saviors.domain.Deed;

@ApplicationScoped
@Named
public class DeedsService {

  private static final Logger logger = LogManager.getLogger(DeedsService.class);
  
  @Inject
  private Configuration configuration;
  
  private Long currentDeedsCount = 0L;
  private Integer listLimit;
  private List<Deed> deedList = new ArrayList<>();
  
  @PostConstruct
  private void setup() {
    this.listLimit = this.configuration.getInt(Configuration.DEEDS_DISPLAYED_LIMIT);
  }
  
  public List<Deed> getDeeds() {
    return this.deedList;
  }
  
  public Deed addDeed(Deed deed) {
    deedList.add(0, deed);
    if (deedList.size() > this.listLimit) {
      deedList.remove(this.listLimit.intValue());
    }
    currentDeedsCount++;
    return deed;
  }

}
