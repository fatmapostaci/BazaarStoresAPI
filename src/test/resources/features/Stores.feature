@Stores
Feature: Stores

  @Get
  Scenario: Get All Stores
    When user sends GET request to get all stores and keep ids
    Then user verifies status code 200
    And The user verifies that the Content-Type is json

  @Post
  Scenario: Create A New Store
    Given user gets token
    When user sends POST request to create a store
    And user keeps created id
    Then user verifies status code 201
    And user verifies response has tag as "success"
    And The user verifies that the Content-Type is json

  @Get
  Scenario: Get Store Request With Created Store Id
    When user sends get request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response has tag as "name"
    And user verifies that response id matches with "validStoreId"
    And The user verifies that the Content-Type is json

  @Get
  Scenario: Get Store Request With Invalid Store Id
    When user sends get request with store id as "invalidStoreId"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json

  @PUT
  Scenario: Update A Store With Created Store Id
    Given user sends put request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response contains "Store updated successfully!"
    And The user verifies that the Content-Type is json

  @Put
  Scenario: Update A Store With Invalid Store Id
    Given user sends get request with store id as "0"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json

  @Put
  Scenario: Update A Store  Returns Response As Validation Errors
    Given user sends get request with store id as "0"
    Then user verifies status code 422
    And The user verifies that the Content-Type is json

  @Put
  Scenario: Update A Store Returns Response As Update Failed
    Given user sends get request with store id as "0"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json

  @Delete
  Scenario: Update A Store Returns Response As Update Failed
    Given user sends get request with store id as "0"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json





