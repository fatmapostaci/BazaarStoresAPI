Feature: Stores

  @Stores
  Scenario: Create A New Store
    Given user gets token
    And user sends POST request to create a store
    Then user verifies status code 201

    @LoginEmail
    Scenario: New
      Given user sends POST request to register
      And  user gets token