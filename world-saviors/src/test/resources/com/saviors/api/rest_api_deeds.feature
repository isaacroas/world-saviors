Feature: Get list of recent good deeds added by any user
  The API should be able to receive a GET request to return the most recent good deeds added by any user
 
  Scenario: The API receives a GET request and returns OK
    When a GET request is received
    Then the API should return status code 200
 
  Scenario: The API receives a GET request and returns a list of deeds
    When a GET request is received
    Then server should return a list of deeds