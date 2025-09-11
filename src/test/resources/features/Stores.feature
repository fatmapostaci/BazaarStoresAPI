@Stores
Feature: Stores

  @GET
  Scenario: Get All Stores
    Given user gets token
    When user sends GET request to get all stores and keep ids
    Then user verifies status code 200
    And The user verifies that the Content-Type is json

  @POST
  Scenario: Create A New Store
    When user sends POST request to create a store
    And user keeps created id
    Then user verifies status code 201
    And user verifies response has tag as "success"
    And The user verifies that the Content-Type is json

  @GET
  Scenario: Get Store Request With Created Store Id
    When user sends get request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response has tag as "name"
    And user verifies that response id matches with "validStoreId"
    And The user verifies that the Content-Type is json

  @GET
  Scenario: Get Store Request With Invalid Store Id
    When user sends get request with store id as "invalidStoreId"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json

  @PUT
  Scenario: Update Store With Created Store Id
    Given user sends put request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response contains "Store updated successfully!"
    And The user verifies that the Content-Type is json

  @PUT
  Scenario: Update Store With Invalid Store Id
    Given user sends put request with store id as "invalidStoreId"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
    And The user verifies that the Content-Type is json

  @PUT
  Scenario: Update Store  Returns Response As Validation Errors
    Given user updates "admin_id" key with value as "000" of put json file
    And user renames "admin_d" key as "new_key" of put json file
    When user sends put request with store id as "validStoreId"
    Then user verifies status code 422
    And The user verifies that the Content-Type is json

  @PUT
  Scenario: Update Store Returns Response As Update Failed
    Given user deletes "admin_id" key from put json file
    And user deletes "admin_d" key from put json file
    Given user sends put request with store id as "validStoreId"
    Then user verifies status code 500
    And user verifies response contains "Store update failed. Please try again"
    And The user verifies that the Content-Type is json

  @DELETE
  Scenario: Delete Created Store
    Given user sends delete request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response contains "Store deleted successfully!"
    And The user verifies that the Content-Type is json





