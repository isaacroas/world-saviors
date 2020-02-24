package com.saviors.config;

import java.io.InputStream;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
@Named
public class Configuration {
  
  private static final String CONFIG_PROPERTIES_FILE = "config.properties";
  public static final String SSE_SCHEDULER_DELAY = "sseSchedulerDelay";
  public static final String SSE_SCHEDULER_PERIOD = "sseSchedulerPeriod";
  public static final String INFLUENCER_URL = "influencerUrl";
  public static final String INFLUENCER_INTIAL_FOLLOWERS = "influencerInitialFollowers";
  public static final String INFLUENCER_INTIAL_AVERAGE_INCREMENT = "influencerInitialAverageIncrement";
  public static final String INFLUENCER_INTIAL_INCREMENT_RANGE = "influencerInitialIncrementRange";
  public static final String INFLUENCER_AVERAGE_INCREMENT = "influencerAverageIncrement";
  public static final String INFLUENCER_INCREMENT_RANGE = "influencerIncrementRange";

  private Properties props;

  private static final Logger logger = LogManager.getLogger(Configuration.class);

  public Configuration() {

    logger.info("Loading configuration file...");
    try (InputStream input =
        Configuration.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILE)) {

      props = new Properties();
      props.load(input);

      props.forEach((key, value) -> logger.info("{}: {}", key, value));

    } catch (Exception e) {
      logger.error("Error loading configuration file", e);
      throw new RuntimeException(e);
    }
  }

  public String get(String key) {
    return props.getProperty(key);
  }
  
  public Long getLong(String key) {
    return Long.parseLong(get(key));
  }
  
  public Integer getInt(String key) {
    return Integer.parseInt(get(key));
  }

}
