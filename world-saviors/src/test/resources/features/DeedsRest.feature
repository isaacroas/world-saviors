Feature: Deeds Rest feature
  Users should be able to submit good deeds and obtain a list of recent good deeds submitted by any user.

  Scenario: GET request received but there are no good deeds yet
    Given There are no good deeds
    When GET request is received
    Then The server should return an empty list of deeds
    And HTTP Status Code should be 200

    Scenario: No good deeds
    Given No good deeds
    When service called
    Then return empty list