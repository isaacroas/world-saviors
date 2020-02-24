package com.saviors.domain;

import java.util.List;

public class SseMessage {

  private Long influencerFollowers;
  private Long ufoSightings;
  private Long leaderSupporters;
  private List<Deed> deeds;

  public Long getInfluencerFollowers() {
    return influencerFollowers;
  }

  public void setInfluencerFollowers(Long influencerFollowers) {
    this.influencerFollowers = influencerFollowers;
  }

  public Long getUfoSightings() {
    return ufoSightings;
  }

  public void setUfoSightings(Long ufoSightings) {
    this.ufoSightings = ufoSightings;
  }

  public Long getLeaderSupporters() {
    return leaderSupporters;
  }

  public void setLeaderSupporters(Long leaderSupporters) {
    this.leaderSupporters = leaderSupporters;
  }

  public List<Deed> getDeeds() {
    return deeds;
  }

  public void setDeeds(List<Deed> deeds) {
    this.deeds = deeds;
  }

}
