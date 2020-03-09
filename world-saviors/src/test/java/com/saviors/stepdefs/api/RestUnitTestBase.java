package com.saviors.stepdefs.api;

import io.restassured.RestAssured;

public abstract class RestUnitTestBase {

  public void setup() {
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
