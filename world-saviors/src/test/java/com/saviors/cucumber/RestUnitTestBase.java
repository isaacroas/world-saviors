package com.saviors.cucumber;

import org.junit.BeforeClass;
import io.restassured.RestAssured;

public class RestUnitTestBase {

  @BeforeClass
  public static void setup() {

    String baseHost = System.getProperty("server.host");
    if (baseHost == null) {
      baseHost = "http://localhost";
    }
    RestAssured.baseURI = baseHost;
    
    String port = System.getProperty("server.port");
    if (port == null) {
      RestAssured.port = Integer.valueOf(8080);
    } else {
      RestAssured.port = Integer.valueOf(port);
    }
    
    String basePath = System.getProperty("server.base");
    if (basePath == null) {
      basePath = "/world-saviors/api/";
    }
    RestAssured.basePath = basePath;
  }

}
