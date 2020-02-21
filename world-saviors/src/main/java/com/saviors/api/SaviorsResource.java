package com.saviors.api;

import javax.enterprise.context.ApplicationScoped;
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

import com.saviors.domain.Savior;

@ApplicationScoped
@Path("/api/saviors")
public class SaviorsResource {

  private static final Logger logger = LogManager.getLogger(SaviorsResource.class);

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  public Response getSavior() {
    logger.debug("This method was called");
    return Response.status(Status.OK).entity(new Savior("Powerful Savior")).build();
  }

  @PUT
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public Response putSavior(Savior savior) {
    return Response.status(Status.CREATED).entity(savior).build();
  }

}
