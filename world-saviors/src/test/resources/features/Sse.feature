Feature: Send information to consumers periodically
  The API should be able to periodically send information about an influencer, a world leader, and UFO sightings to consumers.

  Scenario: Consumer subscribes to SSE messages
    When API receives a GET request to subscribe a consumer to SSE messages
    Then SSE HTTP Status Code should be 200
    And Content Type should be text/event-stream

  Scenario: API sends SSE messages to subscribers
    When API subscribes a consumer
    Then API sends messages to subscribers
    And messages can be read by subscribers
    And messages contain information about influencer followers
    And messages contain information about UFO sightings
    And messages contain information about a world leader's supporters
    And messages contain information about users' good deeds
