package com.saviors.stepdefs.api;

import static com.saviors.util.LambdaUtils.repeat;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.mockito.MockitoAnnotations;
import com.google.gson.Gson;
import com.saviors.domain.SseMessage;
import com.saviors.service.SseService;
import com.saviors.util.SseClient;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SseResourceTest extends RestUnitTestBase {

  private static final Logger logger = LogManager.getLogger(SseService.class);

  private static final String SSE_ENDPOINT = "sse";
  private Response restAssuredResponse;
  private SseClient sseClient;
  private List<SseMessage> newMessages;
  private Gson gson = new Gson();
  private List<SseClient> sseClientList;
  private List<SseMessage> messagesToSeveralUsers;

  @Before
  public void setup() {
    super.setup();
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void closeSseClients() {
    logger.debug("Close SSE clients");
    
    if (sseClient != null) {
      sseClient.closeFeed();
    }
    
    if (sseClientList != null) {
      sseClientList.forEach(client -> {
        // Using threads because closing one client takes 5 seconds
        // and one of the tests opens 50 clients.
        Thread thread = new Thread(() -> client.closeFeed());
        thread.start();
      });
    }

    logger.debug("SSE clients closed");
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
    waitForMessages(this.sseClient);

    assertTrue(sseClient.hasNewEvent());
    assertTrue(sseClient.getNewEventCount() > 0);
  }

  @Then("messages can be read by subscribers")
  public void messages_can_be_read_by_subscribers() {
    readNewMessages(this.sseClient);
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

  @Then("messages are sent periodically")
  public void messages_are_sent_periodically() {
    repeat(10, () -> {
      waitForMessages(this.sseClient);
      assertTrue(newMessages.size() > 0);
    });
  }

  @When("API subscribes {int} users")
  public void api_subscribes_users(Integer usersCount) {
    logger.debug("Subscribe {} users", usersCount);
    sseClientList = new ArrayList<SseClient>();
    repeat(usersCount, () -> {
      SseClient sseClient = new SseClient(obtainApiUrl() + SSE_ENDPOINT).connectFeed();
      assertNotNull(sseClient);
      sseClientList.add(sseClient);
    });
    logger.debug("The API has subscribed {} users", sseClientList.size());
  }

  @Then("APi is able to send information to all users")
  public void api_is_able_to_send_information_to_all_users() {
    logger.debug("Send information to all {} users", sseClientList.size());
    messagesToSeveralUsers = new ArrayList<>();
    Date start = new Date();
    receiveMessagesFromAllClients();
    Date end = new Date();
    logger.debug("All {} users have received their messages in {} seconds", sseClientList.size(),
        (end.getTime() - start.getTime()) / 1000);
  }

  @Then("API is able to continue sending messages to all users {int} times")
  public void api_is_able_to_continue_sending_messages_to_all_users_times(Integer numberOfTimes) {
    logger.debug("Send information to all {} users {} times", sseClientList.size(), numberOfTimes);
    Counter counter = new Counter();
    repeat(numberOfTimes, () -> {
      Date start = new Date();
      receiveMessagesFromAllClients();
      Date end = new Date();
      counter.increment();
      logger.debug("{} - All {} users have received their messages in {} seconds",
          counter.getCounter(), sseClientList.size(), (end.getTime() - start.getTime()) / 1000);
    });
  }

  @Then("all messages contain information about influencer followers")
  public void all_messages_contain_information_about_influencer_followers() {
    logger.debug("Check all messages contained information about influencer followers");
    assertTrue(messagesToSeveralUsers.stream()
        .allMatch(message -> message.getInfluencerFollowers() != null));
  }

  @Then("all messages contain information about UFO sightings")
  public void all_messages_contain_information_about_UFO_sightings() {
    logger.debug("Check all messages contain information about UFO sightings");
    assertTrue(
        messagesToSeveralUsers.stream().allMatch(message -> message.getUfoSightings() != null));
  }

  @Then("all messages contain information about a world leader's supporters")
  public void all_messages_contain_information_about_a_world_leader_s_supporters() {
    logger.debug("Check all messages contain information about a world leader's supporters");
    assertTrue(
        messagesToSeveralUsers.stream().allMatch(message -> message.getLeaderSupporters() != null));
  }

  @Then("all messages contain information about users' good deeds")
  public void all_messages_contain_information_about_users_good_deeds() {
    logger.debug("Check all messages contain information about users' good deeds");
    assertTrue(messagesToSeveralUsers.stream()
        .allMatch(message -> message.getDeeds() != null && !message.getDeeds().isEmpty()));
  }

  private String obtainApiUrl() {
    return RestAssured.baseURI + ":" + RestAssured.port + RestAssured.basePath;
  }

  private void waitForMessages(SseClient client) {
    await().atMost(5, SECONDS).untilAsserted(() -> assertTrue(client.hasNewEvent()));
  }

  private void readNewMessages(SseClient client) {
    newMessages = new ArrayList<SseMessage>();
    repeat(client.getNewEventCount(), () -> {
      InboundEvent inboundEvent = client.getNextEvent();
      SseMessage sseMessage = gson.fromJson(inboundEvent.readData(), SseMessage.class);
      newMessages.add(sseMessage);
    });
  }

  private void receiveMessagesFromAllClients() {
    sseClientList.forEach(client -> {
      waitForMessages(client);
      assertTrue(client.hasNewEvent());
      assertTrue(client.getNewEventCount() > 0);
      readNewMessages(client);
      assertTrue(newMessages.size() > 0);
      messagesToSeveralUsers.addAll(this.newMessages);
    });
  }

  class Counter {
    private Integer counter = 0;

    public void increment() {
      counter++;
    }

    public Integer getCounter() {
      return this.counter;
    }
  }

}
