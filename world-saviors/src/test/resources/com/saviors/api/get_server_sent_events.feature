Feature: Register SSE Broadcaster and send confirmation event
  The API should be able to receive a GET request to register the event sink in the broadcaster and return a confirmation SSE event.
 
  Scenario: Register the event sink in the broadcaster
    When a GET request is received
    Then the server should register the event sink in the broadcaster
 
  Scenario: Return confirmation SSE event
    When a GET request is received
    Then server should respond sending an SSE event through the SSE event sink