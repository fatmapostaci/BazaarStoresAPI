@UserCRUD @Mehmet
Feature: User API End-to-End Tests

  Scenario: TC_01_USERS_GET List All Users
    Given user gets token
    When GET request is sent
    Then The user verifies that the status code is 200

  Scenario: TC_02_USERS_POST Create User
    When New user has been created
    Then The user verifies that the status code is 201
    And Created user ID has been saved

  Scenario: TC_03_USERS_GET Retrieve User By ID
    When Created user is retrieved by ID
    Then The user verifies that the status code is 200
    And The user details are printed

  Scenario: TC_04_USERS_PUT Update User
    When User has been updated
    Then The user verifies that the status code is 200
    And The user details are printed

  Scenario: TC_05_USERS_DELETE Remove User
    When User has been deleted
    Then The user verifies that the status code is 200

  Scenario: TC_06_USERS_GET Verify Deleted User
    When Created user is retrieved by ID
    Then The user verifies that the status code is 404
    And The user details are printed