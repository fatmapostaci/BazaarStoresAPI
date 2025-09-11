@UserCRUD
Feature: User CRUD and Login

  @User1
  Scenario: TC_01_ListUsers - All users are listed
    Given user gets token
    When GET request is sent
    Then The user verifies that the status code is 200

  @User1
  Scenario: TC_02_CreateUser - New user is created
    When New user has been created
    Then The user verifies that the status code is 201
    And Created user ID has been saved

  @User1
  Scenario: TC_03_GetUserByID - Retrieve created user by ID
    When Created user is retrieved by ID
    Then The user verifies that the status code is 200
    And The user details are printed

  @User1
  Scenario: TC_04_UpdateUser - Update created user by ID
    When User has been updated
    Then The user verifies that the status code is 200
    And The user details are printed

  @User1
  Scenario: TC_05_DeleteUser - Delete created user by ID
    When User has been deleted
    Then The user verifies that the status code is 200

  @User1
  Scenario: TC_03_GetUserByID - Retrieve created user by ID
    When Created user is retrieved by ID
    Then The user verifies that the status code is 404
    And The user details are printed