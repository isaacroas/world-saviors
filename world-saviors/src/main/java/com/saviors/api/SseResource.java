package com.saviors.api;

import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;
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
import com.saviors.config.Configuration;
import com.saviors.domain.SseMessage;
import com.saviors.service.LeaderSupporterService;
import com.saviors.service.MockInstagramService;
import com.saviors.service.UfoSightingService;


@Path("/api/sse")
public class SseResource {

  private static final Logger logger = LogManager.getLogger(SseResource.class);

  private SseBroadcaster sseBroadcaster;
  private OutboundSseEvent.Builder eventBuilder;

  @Inject
  private Configuration configuration;

  @Inject
  private MockInstagramService instagramService;
  
  @Inject
  private UfoSightingService ufoSightingService;
  
  @Inject
  private LeaderSupporterService leaderSupporterService;

  @Context
  public void setSse(Sse sse) {
    this.eventBuilder = sse.newEventBuilder();
    this.sseBroadcaster = sse.newBroadcaster();
    this.startBroadcasting();
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void register(@Context SseEventSink sseEventSink) {
    this.sseBroadcaster.register(sseEventSink);
    OutboundSseEvent outboundSseEvent = eventBuilder.data(new SseMessage())
        .mediaType(MediaType.APPLICATION_JSON_TYPE)
        .build();
    sseEventSink.send(outboundSseEvent);
  }

  private void startBroadcasting() {
    Timer timer = new Timer();
    TimerTask broadcastTask = new TimerTask() {

      Long sseCount = 0L;

      @Override
      public void run() {
        sseCount++;
        SseMessage sseMessage = generateSseMessage();
        OutboundSseEvent outboundSseEvent = eventBuilder.data(sseMessage)
            .mediaType(MediaType.APPLICATION_JSON_TYPE)
            .build();
        logger.info("Broadcasting SSE: {}", sseMessage);
        sseBroadcaster.broadcast(outboundSseEvent);
      }

    };
    Long sseSchedulerPeriod = this.configuration.getLong(Configuration.SSE_SCHEDULER_PERIOD);
    Long sseSchedulerDelay = this.configuration.getLong(Configuration.SSE_SCHEDULER_DELAY);
    timer.schedule(broadcastTask, sseSchedulerDelay, sseSchedulerPeriod);
  }

  private SseMessage generateSseMessage() {
    SseMessage sseMessage = new SseMessage();
    Long influencerFollowers = instagramService.getNextCount();
    Long ufoSightings = ufoSightingService.getNextCount();
    Long leaderSupporters = leaderSupporterService.getNextCount();
    sseMessage.setInfluencerFollowers(influencerFollowers);
    sseMessage.setUfoSightings(ufoSightings);
    sseMessage.setLeaderSupporters(leaderSupporters);
    return sseMessage;
  }

}
