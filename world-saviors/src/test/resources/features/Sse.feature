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
    And messages are sent periodically

  @stress
  Scenario: API can send information to 50 users 10 times
    When API subscribes 50 users
    Then APi is able to send information to all users
    And API is able to continue sending messages to all users 10 times
    And all messages contain information about influencer followers
    And all messages contain information about UFO sightings
    And all messages contain information about a world leader's supporters
    And all messages contain information about users' good deeds
