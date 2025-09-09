@Authentication
Feature: Authentication
  
  Scenario: Register A New User With Succesfull Response
    Given user gets token
    When user sends valid POST request to register
    Then user verifies status code 200

  Scenario: Register A New User With Validation Error Response
    When user sends invalid POST request to register
    Then user verifies status code 422

  Scenario: Log In A User With Succesfull Response
    When user logs in successfully
    Then user verifies status code 200

  Scenario: Log In A User With Invalid Credentials
    When user logs in with invalid credentials
    Then user verifies status code 401

  Scenario: Log In A User With Response Could Not Create Token
    When user logs in incorrectly
    Then user verifies status code 500

  Scenario: Logout
    Given user logs out
    Then user verifies status code 200

    Scenario: Get The Current Authenticated User
      Given user sends GET request to get current authenticated user
      Then user verifies status code 200
