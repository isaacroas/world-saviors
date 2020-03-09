Feature: Social Decay Data
  API should send information about social decay indicators.

  Scenario: Send information about an influencer's followers
    When Information about an influencer's followers is requested
    Then Return a positive number

  Scenario: Send information about UFO sightings
    When Information about UFO sightings is requested
    Then Return a positive number

  Scenario: Send information about a world leader's supporters
    When Information about a world leader's supporters is requested
    Then Return a positive number
