@Stores
Feature: Stores

  Background: Get Token
    Given user gets token

  @GetAllStores
  Scenario: Get All Stores
    When user sends GET request to get all stores
    Then user verifies status code 200

  @CreateAStore @GetAStoreById_Succesfull
  Scenario: Create A New Store
    When user sends POST request to create a store
    And user keeps created id
    Then user verifies status code 201
    And user verifies response has tag as "success"

  @GetAStoreById_Succesfull
  Scenario: Get Store Request With Valid Store Id
    When user sends get request with store id as "validStoreId"
    Then user verifies status code 200
    And user verifies response has tag as "name"
    And user verifies that response id matches with "validStoreId"

    @GetAStoreById_StoreNotFound
    Scenario: Get Store Request With Invalid Store Id
      When user sends get request with store id as "invalidStoreId"
      Then user verifies status code 404
      And user verifies response contains "Store not found"


  @UpdateAStoreByID_Succesfull
  Scenario: Update A Store With Valid Store Id
    Given user sends get request with store id as "0"
    Then user verifies status code 404
    And user verifies response contains "Store not found"
