Feature: Getting good deeds
  Users should be able to obtain a list of recent good deeds submitted users.

  @integration
  Scenario: Users request the list of good deeds
    When API receives GET request
    Then API should return a list of deeds
    And HTTP Status Code should be 200

  Scenario: If there are no good deeds, the API should return an empty list
    Given There are no good deeds
    When The list of good deeds is requested
    Then The API returns an empty list

  Scenario: If there are some good deeds, the API should return a populated list
    Given There are some good deeds
    When The list of good deeds is requested
    Then The API returns a list of good deeds

  Scenario: The API should return the good deeds that were added by users
    Given There are some good deeds
    When The list of good deeds is requested
    Then The API returns the good deeds that were added by users

  Scenario: No more than the maximum of good deeds should be returned
    Given Users have added more than the maximum good deeds
    When Good deeds are requested
    Then The maximum number of good deeds is returned
    
  Scenario: The list of good deeds should be returned in reverse order starting from most recent one
    Given Some good deeds have been added
    When Good deeds are requested
    Then Good deeds are returned in reverse order