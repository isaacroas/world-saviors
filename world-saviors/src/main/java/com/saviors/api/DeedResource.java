package com.saviors.api;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
import com.saviors.service.DeedService;
import com.saviors.service.SseService;

@Path("/api/deeds")
public class DeedResource {

  private static final Logger logger = LogManager.getLogger(DeedResource.class);
  
  @Inject
  private DeedService deedService;
  
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
  public Response addDeed(@NotNull @Valid Deed deed) {
    Deed createdDeed = this.deedService.addDeed(deed);
    this.sseService.sendSseMessage();
    return Response.status(Status.CREATED).entity(createdDeed).build();
  }

}
