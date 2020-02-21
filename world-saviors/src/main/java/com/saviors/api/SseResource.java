package com.saviors.api;

import java.util.Timer;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ApplicationScoped
@Path("/api/sse")
public class SseResource {

  private static final Logger logger = LogManager.getLogger(SseResource.class);

  private Sse sse;
  private SseBroadcaster sseBroadcaster;
  private OutboundSseEvent.Builder eventBuilder;
  private Long broadcastingMillisPeriod = 1000L;

  @Context
  public void setSse(Sse sse) {
    this.sse = sse;
    this.eventBuilder = sse.newEventBuilder();
    this.sseBroadcaster = sse.newBroadcaster();
    this.startBroadcasting();
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void live(@Context SseEventSink sseEventSink) {
    this.sseBroadcaster.register(sseEventSink);
    sseEventSink.send(sse.newEvent("Let's start saving the world!!!"));
  }

  private void startBroadcasting() {
    Timer time = new Timer();
    time.schedule(new BroadcastTask(this.eventBuilder, this.sseBroadcaster), 0L,
        this.broadcastingMillisPeriod);
  }

}
