package com.saviors.util;

import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.saviors.config.Configuration;

public class FileReader {
  
  private static final Logger logger = LogManager.getLogger(FileReader.class);
  
  private FileReader() {}
  
  public static Properties readProperties(String fileName) {

    logger.info("Loading configuration file...");
    
    Properties props;
    try (InputStream input =
        Configuration.class.getClassLoader().getResourceAsStream(fileName)) {

      props = new Properties();
      props.load(input);

      props.forEach((key, value) -> logger.info("{}: {}", key, value));

    } catch (Exception e) {
      logger.error("Error loading configuration file", e);
      throw new RuntimeException(e);
    }
    
    return props;
  }

}
