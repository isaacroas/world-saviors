package com.saviors.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.saviors.service.SseService;


@Path("/api/sse")
public class SseResource {

  private static final Logger logger = LogManager.getLogger(SseResource.class);

  @Inject
  private SseService sseService;

  private Sse sse;

  @Context
  public void setSse(Sse sse) {
    this.sse = sse;
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void register(@Context SseEventSink sseEventSink) {
    this.sseService.register(sse, sseEventSink);
  }

}
