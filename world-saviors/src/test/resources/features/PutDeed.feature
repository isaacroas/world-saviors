Feature: Add a good deed
  Users should be able to submit a good deed.

  Scenario: User adds a good deed
    When API receives a PUT request containing a good deed
    Then API should return the saved good deed
    And HTTP Status Code should be 201

  Scenario: API receives empty PUT request
    When API receives an empty PUT request
    Then HTTP Status Code should be 400

  Scenario: API receives invalid PUT request
    When API receives an invalid PUT request
    Then HTTP Status Code should be 400
    
  Scenario: An SSE message is sent when a new good deed is added
    When A good deed is added
    Then An SSE message is broadcasted
