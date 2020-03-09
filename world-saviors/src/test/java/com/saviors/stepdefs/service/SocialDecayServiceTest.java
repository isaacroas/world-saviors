package com.saviors.stepdefs.service;

import static org.junit.Assert.assertTrue;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.saviors.config.Configuration;
import com.saviors.service.LeaderSupporterService;
import com.saviors.service.MockInstagramService;
import com.saviors.service.UfoSightingService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SocialDecayServiceTest {

  @InjectMocks
  private MockInstagramService instagramService;
  
  @InjectMocks
  private LeaderSupporterService leaderSupporterService;
  
  @InjectMocks
  private UfoSightingService ufoSightingService;

  @Spy
  private Configuration configuration;

  private Long outputCount;

  @Before
  public void init() throws NoSuchFieldException, SecurityException {
    MockitoAnnotations.initMocks(this);
  }

  @When("Information about an influencer's followers is requested")
  public void information_about_an_influencer_s_followers_is_requested() {
    outputCount = instagramService.getNextCount();
  }
  
  @When("Information about UFO sightings is requested")
  public void information_about_UFO_sightings_is_requested() {
    outputCount = ufoSightingService.getNextCount();
  }
  
  @When("Information about a world leader's supporters is requested")
  public void information_about_a_world_leader_s_supporters_is_requested() {
    outputCount = leaderSupporterService.getNextCount();
  }

  @Then("Return a positive number")
  public void return_a_positive_number() {
    assertTrue(outputCount >= 0);
  }

}
