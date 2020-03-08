package com.saviors.stepdefs.api;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.saviors.api.DeedResource;
import com.saviors.config.Configuration;
import com.saviors.domain.Deed;
import com.saviors.service.DeedsService;
import com.saviors.service.SseService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeedsRestTest extends RestUnitTestBase {

  @InjectMocks
  private DeedResource deedResource;

  @Mock
  private DeedsService deedService;

  @Mock
  private SseService sseService;

  @Mock
  private Configuration configuration;

  private io.restassured.response.Response restAssuredResponse;

  private javax.ws.rs.core.Response rsResponse;

  private String DEEDS_ENDPOINT = "deeds";

  private ArrayList<Deed> inputList;

  private Deed inputDeed;

  @Before
  public void setup() {
    super.setup();
    MockitoAnnotations.initMocks(this);
  }

  @When("API receives GET request")
  public void api_receives_get_request() {
    restAssuredResponse =
        given().contentType(MediaType.APPLICATION_JSON).when().get(DEEDS_ENDPOINT);
  }

  @Then("API should return a list of deeds")
  public void api_should_return_a_list_of_deeds() {
    Deed[] deeds = restAssuredResponse.then().extract().as(Deed[].class);
    assertNotNull(deeds);
  }

  @Then("HTTP Status Code should be {int}")
  public void http_Status_Code_should_be(Integer expectedStatusCode) {
    restAssuredResponse.then().statusCode(expectedStatusCode);
  }

  @Given("There are no good deeds")
  public void there_are_no_good_deeds() {
    when(deedService.getDeeds()).thenReturn(new ArrayList<>());
  }

  @When("The list of good deeds is requested")
  public void the_list_of_good_deeds_is_requested() {
    rsResponse = deedResource.getDeeds();
  }

  @Then("The API returns an empty list")
  public void the_api_returns_an_empty_list() {
    List<Deed> outputList = rsResponse.readEntity(new GenericType<List<Deed>>() {});
    assertTrue(outputList.size() == 0);
  }

  @Given("There are some good deeds")
  public void there_are_some_good_deeds() {
    inputList = new ArrayList<>();
    inputList.add(new Deed("foo"));
    inputList.add(new Deed("bar"));
    when(deedService.getDeeds()).thenReturn(inputList);
  }

  @Then("The API returns a list of good deeds")
  public void the_API_returns_the_list_of_good_deeds() {
    List<Deed> outputList = rsResponse.readEntity(new GenericType<List<Deed>>() {});
    assertTrue(outputList.size() > 0);
  }

  @Then("The API returns the good deeds that were added by users")
  public void the_API_returns_the_list_of_good_deeds_that_were_added_by_users() {
    List<Deed> outputList = rsResponse.readEntity(new GenericType<List<Deed>>() {});
    assertTrue(outputList.size() == 2 && outputList.containsAll(inputList));
  }

  @When("API receives a PUT request containing a good deed")
  public void api_receives_a_put_request_containing_a_good_deed() {
    inputDeed = new Deed("new deed");
    restAssuredResponse =
        given().contentType(MediaType.APPLICATION_JSON).body(inputDeed).when().put(DEEDS_ENDPOINT);
  }

  @Then("API should return the saved good deed")
  public void api_should_return_the_saved_good_deed() {
    Deed outputDeed = restAssuredResponse.then().extract().as(Deed.class);
    assertEquals(inputDeed, outputDeed);
  }

  @When("API receives an empty PUT request")
  public void api_receives_an_empty_PUT_request() {
    restAssuredResponse =
        given().contentType(MediaType.APPLICATION_JSON).when().put(DEEDS_ENDPOINT);
  }

  @When("API receives an invalid PUT request")
  public void api_receives_an_invalid_PUT_request() {
    inputDeed = new Deed("");
    restAssuredResponse =
        given().contentType(MediaType.APPLICATION_JSON).body(inputDeed).when().put(DEEDS_ENDPOINT);
  }

  @When("A good deed is added")
  public void a_good_deed_is_added() {
    deedResource.addDeed(new Deed("foo"));
  }

  @Then("An SSE message is broadcasted")
  public void an_SSE_message_is_broadcasted() {
    verify(sseService).sendSseMessage();
  }

}
