package com.saviors.domain;

import com.google.gson.annotations.SerializedName;

public class User {

  @SerializedName("edge_followed_by")
  private EdgeFollowedBy edgeFollowedBy;

  public EdgeFollowedBy getEdgeFollowedBy() {
    return edgeFollowedBy;
  }

  public void setEdgeFollowedBy(EdgeFollowedBy edgeFollowedBy) {
    this.edgeFollowedBy = edgeFollowedBy;
  }

}
