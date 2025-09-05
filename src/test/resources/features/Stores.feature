Feature: Stores

  @Stores
  Scenario: Create A New Store
    Given user gets token
    And user sends POST request
    Then user verifies status code 201

    @LoginEmail
    Scenario: New
      Given user gets token as "customer@outlook.com"