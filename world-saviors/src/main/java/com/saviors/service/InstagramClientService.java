package com.saviors.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import com.saviors.config.Configuration;
import com.saviors.domain.InstagramResponse;

@ApplicationScoped
@Named
public class InstagramClientService {

  @Inject
  private Configuration configuration;

  private static final Logger logger = LogManager.getLogger(InstagramClientService.class);

  public Long getFollowersCount() {
    Client client = null;
    Long count = null;
    String influencerUrl = this.configuration.get(Configuration.INFLUENCER_URL);
    try {
      client = ClientBuilder.newClient();
      WebTarget webTarget = client.target(influencerUrl);
      String response = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
      Gson gson = new Gson();
      InstagramResponse instagramResponse = gson.fromJson(response, InstagramResponse.class);
      count = this.extractCount(instagramResponse);
    } catch(Exception e) {
      logger.error("Error sending request to: {}", influencerUrl, e);
      throw new WebApplicationException("The followers count couldn't be retrieved from " + influencerUrl, Status.BAD_REQUEST);
    } finally {
      if (client != null) {
        client.close();
      }
    }
    return count;
  }

  private Long extractCount(InstagramResponse instagramResponse) {
    Long count = null;
    if (instagramResponse != null) {
      try {
        count = instagramResponse.getGraphql().getUser().getEdgeFollowedBy().getCount();
      } catch (NullPointerException e) {
        logger.error("Instagram followers count was not retrieved", e);
      }
    }
    return count;
  }

}
