package com.saviors.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * This class was mostly copied from https://stackoverflow.com/a/52538591
 */
public class SseClient {

  private static final Logger logger = LogManager.getLogger(SseClient.class);

  private final String targetUrl;
  private final List<InboundEvent> receivedEvents = new ArrayList<>();

  private String clientName = "SSE client";
  private EventSource eventSource;
  private int currentEventPos = 0;

  public SseClient(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public SseClient setClientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

  public SseClient connectFeed() {
    logger.info("[{}] Initializing EventSource...", clientName);
    Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
    eventSource = EventSource.target(client.target(targetUrl))
        .reconnectingEvery(300, TimeUnit.SECONDS).build();

    EventListener listener = inboundEvent -> {
      logger.info("[{}] Event received: name -> {}, data -> {}", clientName, inboundEvent.getName(),
          inboundEvent.readData(String.class));
      receivedEvents.add(inboundEvent);
    };
    eventSource.register(listener);
    eventSource.open();

    logger.info("[{}] EventSource connection opened", clientName);

    return this;
  }

  public int getTotalEventCount() {
    return receivedEvents.size();
  }

  public int getNewEventCount() {
    return receivedEvents.size() - currentEventPos;
  }

  public boolean hasNewEvent() {
    return currentEventPos < receivedEvents.size();
  }

  public InboundEvent getNextEvent() {
    if (currentEventPos >= receivedEvents.size()) {
      return null;
    }
    InboundEvent currentEvent = receivedEvents.get(currentEventPos);
    ++currentEventPos;
    return currentEvent;
  }

  public SseClient closeFeed() {
    if (eventSource != null) {
      eventSource.close();
    }
    return this;
  }
}
