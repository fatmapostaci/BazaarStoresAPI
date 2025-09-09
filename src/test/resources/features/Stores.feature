@Stores
Feature: Stores

  Scenario: Create A New Store
    Given user gets token
    When user sends POST request to create a store
    And user keeps created id
    Then user verifies status code 201
    And user verifies response has tag as "success"

  Scenario: Get Store Request With Valid Store Id
    When user sends get request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response has tag as "name"
    And user verifies that response id matches with "validStoreId"

  Scenario: Get Store Request With Invalid Store Id
    When user sends get request with store id as "invalidStoreId"
    Then user verifies status code 404
    And user verifies response contains "Store not found"

  Scenario: Update A Store With Valid Store Id
    Given user sends get request with store id as "0"
    Then user verifies status code 404
    And user verifies response contains "Store not found"

  Scenario: Get All Stores
    When user sends GET request to get all stores and keep ids
    Then user verifies status code 200



