package com.saviors.cucumber;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.saviors.api.DeedsResource;
import com.saviors.domain.Deed;
import com.saviors.service.DeedsService;
import com.saviors.service.SseService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeedsRestStepDefs {
  
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }
  
  @InjectMocks
  private DeedsResource deedsResource;
  
  @Mock
  private DeedsService deedService;
  
  @Mock
  private SseService sseService;
  
  private io.restassured.response.Response restAssuredResponse;
  
  private javax.ws.rs.core.Response rsResponse;
  
  private String DEEDS_ENDPOINT = "http://localhost:8080/world-saviors/api/deeds";

  @Given("There are no good deeds")
  public void there_are_no_good_deeds() {
  }

  @When("GET request is received")
  public void get_request_is_received() {
    restAssuredResponse = given().when().get(DEEDS_ENDPOINT);
  }

  @Then("The server should return an empty list of deeds")
  public void the_server_should_return_an_empty_list_of_deeds() {
    List<Deed> jsonResponse = restAssuredResponse.jsonPath().<List<Deed>>get();
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.size() == 0);
  }

  @Then("HTTP Status Code should be {int}")
  public void http_Status_Code_should_be(Integer statusCode) {
    restAssuredResponse.then().statusCode(HttpStatus.SC_OK);
  }
  
  
  
  @Given("No good deeds")
  public void no_good_deeds() {
      when(deedService.getDeeds()).thenReturn(new ArrayList<Deed>());
  }

  @When("service called")
  public void service_called() {
    rsResponse = deedsResource.getDeeds();
  }

  @Then("return empty list")
  public void return_empty_list() {
      assertNotNull(rsResponse);
  }

}
