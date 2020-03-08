package com.saviors.stepdefs.api;

import static com.saviors.util.LambdaUtils.repeat;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.mockito.MockitoAnnotations;
import com.google.gson.Gson;
import com.saviors.domain.SseMessage;
import com.saviors.util.SseClient;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SseResourceTest extends RestUnitTestBase {

  private static final String SSE_ENDPOINT = "sse";
  private Response restAssuredResponse;
  private SseClient sseClient;
  private ArrayList<SseMessage> newMessages;
  private Gson gson = new Gson();

  @Before
  public void setup() {
    super.setup();
    MockitoAnnotations.initMocks(this);
  }

  @When("API receives a GET request to subscribe a consumer to SSE messages")
  public void api_receives_a_GET_request_to_subscribe_a_consumer_to_SSE_messages() {
    restAssuredResponse = given().when().get(SSE_ENDPOINT);
  }

  @Then("SSE HTTP Status Code should be {int}")
  public void sse_HTTP_Status_Code_should_be(Integer expectedStatusCode) {
    restAssuredResponse.then().assertThat().statusCode(expectedStatusCode);
  }

  @Then("Content Type should be text\\/event-stream")
  public void content_Type_should_be_text_event_stream() {
    restAssuredResponse.then().assertThat().contentType(MediaType.SERVER_SENT_EVENTS);
  }

  @When("API subscribes a consumer")
  public void api_subscribes_a_consumer() {
    sseClient = new SseClient(obtainApiUrl() + SSE_ENDPOINT).connectFeed();
    assertNotNull(sseClient);
  }

  @Then("API sends messages to subscribers")
  public void api_sends_messages_to_subscribers() throws InterruptedException {
    waitForMessages();

    assertTrue(sseClient.hasNewEvent());
    assertTrue(sseClient.getNewEventCount() > 0);
  }

  @Then("messages can be read by subscribers")
  public void messages_can_be_read_by_subscribers() {
    readNewMessages();
    assertTrue(newMessages.size() > 0);
  }

  @Then("messages contain information about influencer followers")
  public void messages_contain_information_about_influencer_followers() {
    assertTrue(newMessages.stream().allMatch(message -> message.getInfluencerFollowers() != null));
  }

  @Then("messages contain information about UFO sightings")
  public void messages_contain_information_about_UFO_sightings() {
    assertTrue(newMessages.stream().allMatch(message -> message.getUfoSightings() != null));
  }

  @Then("messages contain information about a world leader's supporters")
  public void messages_contain_information_about_a_world_leader_s_supporters() {
    assertTrue(newMessages.stream().allMatch(message -> message.getLeaderSupporters() != null));
  }

  @Then("messages contain information about users' good deeds")
  public void messages_contain_information_about_users_good_deeds() {
    assertTrue(newMessages.stream()
        .allMatch(message -> message.getDeeds() != null && !message.getDeeds().isEmpty()));
  }

  private String obtainApiUrl() {
    return RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
  }

  private void waitForMessages() {
    await().atMost(5, SECONDS).untilAsserted(() -> assertTrue(sseClient.hasNewEvent()));
  }

  private void readNewMessages() {
    newMessages = new ArrayList<SseMessage>();
    repeat(sseClient.getNewEventCount(), () -> {
      InboundEvent inboundEvent = sseClient.getNextEvent();
      SseMessage sseMessage = gson.fromJson(inboundEvent.readData(), SseMessage.class);
      newMessages.add(sseMessage);
    });
  }
}
