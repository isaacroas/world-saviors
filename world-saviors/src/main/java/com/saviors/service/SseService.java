package com.saviors.service;

import java.util.Timer;
import java.util.TimerTask;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.saviors.config.Configuration;
import com.saviors.domain.SseMessage;

@ApplicationScoped
@Named
public class SseService {

  private static final Logger logger = LogManager.getLogger(SseService.class);

  private Sse sse;
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
  
  @Inject
  private DeedsService deedsService;

  public void register(Sse sse, SseEventSink sseEventSink) {
    if (this.sse == null) {
      initialize(sse);
    }
    this.sseBroadcaster.register(sseEventSink);
    OutboundSseEvent outboundSseEvent =
        eventBuilder.data(new SseMessage())
        .mediaType(MediaType.APPLICATION_JSON_TYPE)
        .build();
    sseEventSink.send(outboundSseEvent);
  }

  private void initialize(Sse sse) {
    this.sse = sse;
    this.eventBuilder = sse.newEventBuilder();
    this.sseBroadcaster = sse.newBroadcaster();
    this.startBroadcasting();
  }

  private void startBroadcasting() {
    Timer timer = new Timer();
    TimerTask broadcastTask = new TimerTask() {

      @Override
      public void run() {
        sendSseMessage();
      }

    };
    Long sseSchedulerPeriod = this.configuration.getLong(Configuration.SSE_SCHEDULER_PERIOD);
    Long sseSchedulerDelay = this.configuration.getLong(Configuration.SSE_SCHEDULER_DELAY);
    timer.schedule(broadcastTask, sseSchedulerDelay, sseSchedulerPeriod);
    logger.info("Broadcast has been scheduled with delay {}, period {}", sseSchedulerDelay, sseSchedulerPeriod);
  }

  private SseMessage generateSseMessage() {
    SseMessage sseMessage = new SseMessage();
    Long influencerFollowers = null;
    Long leaderSupporters = null;
    Long ufoSightings = null;
    try {
      influencerFollowers = instagramService.getNextCount();
      leaderSupporters = leaderSupporterService.getNextCount();
      ufoSightings = ufoSightingService.getNextCount();
    } catch (Exception e) {
      logger.error(e);
    }
    sseMessage.setInfluencerFollowers(influencerFollowers);
    sseMessage.setLeaderSupporters(leaderSupporters);
    sseMessage.setUfoSightings(ufoSightings);
    sseMessage.setDeeds(deedsService.getDeeds());
    return sseMessage;
  }

  public void sendSseMessage() {
    SseMessage sseMessage = generateSseMessage();
    if (eventBuilder != null) {
      OutboundSseEvent outboundSseEvent =
          eventBuilder.data(sseMessage).mediaType(MediaType.APPLICATION_JSON_TYPE).build();
      logger.info("Broadcasting SSE: {}", sseMessage);
      if (sseBroadcaster != null) {
        sseBroadcaster.broadcast(outboundSseEvent);
      }
    }
  }

}
