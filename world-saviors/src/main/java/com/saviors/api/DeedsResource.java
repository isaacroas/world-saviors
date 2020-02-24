package com.saviors.api;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.saviors.domain.Deed;
import com.saviors.service.DeedsService;
import com.saviors.service.SseService;

@ApplicationScoped
@Path("/api/deeds")
public class DeedsResource {

  private static final Logger logger = LogManager.getLogger(DeedsResource.class);
  
  @Inject
  private DeedsService deedService;
  
  @Inject
  private SseService sseService;

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Response getDeeds() {
    List<Deed> deedList = this.deedService.getDeeds();
    return Response.status(Status.OK).entity(deedList).build();
  }

  @PUT
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public Response addDeed(Deed deed) {
    Deed createdDeed = this.deedService.addDeed(deed);
    this.sseService.sendSseMessage();
    return Response.status(Status.CREATED).entity(createdDeed).build();
  }

}