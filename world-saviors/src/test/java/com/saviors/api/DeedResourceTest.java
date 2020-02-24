package com.saviors.api;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.saviors.domain.Deed;

public class DeedResourceTest extends RestUnitTestBase {

  @Test
  public void testPingGetDeeds() {
    given().when().get("deeds").then().statusCode(200);
  }

  @Test
  public void testGetDeedsReturnsListOfDeeds() {
    ArrayList<Deed> deeds =
        given().when().get("deeds").then().statusCode(200).extract().as(ArrayList.class);

    assertNotNull(deeds);
  }

  @Test
  public void testPutDeedReturnsOk() {
    Map<String, String> deed = new HashMap<>();
    deed.put("text", "Saved a cat");

    given().contentType("application/json").body(deed).when().put("deeds").then().statusCode(201);
  }

  @Test
  public void testPutDeedReturnsAddedDeed() {
    Map<String, String> deed = new HashMap<>();
    deed.put("text", "Saved a cat");

    Deed actualDeed = given().contentType("application/json").body(deed).when().put("deeds").then()
        .statusCode(201).extract().as(Deed.class);

    Deed expectedDeed = new Deed();
    expectedDeed.setText("Saved a cat");
    assertEquals(expectedDeed, actualDeed);
  }

}
