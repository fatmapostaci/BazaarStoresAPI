Feature: Users

  Background: : Admin login
    Given user sends POST request to login with email "adminuser@gmail.com" and password "password"
    When  user gets token as role "adminToken"

  @getAllUsers @CRUDUsers
  Scenario: GET all users
    Given The user sends a request with the GET method to "users"
    Then  The user verifies that the status code is 200
    Then  The user verifies that the Content-Type is json
    And   The user verifies that the response body contains a non-empty users list

  @createUser @CRUDUsers
  Scenario: Create a user
    Given The user prepares a unique user payload with:
      | name                  | testuser<timestamp>          |
      | email                 | testuser<timestamp>@mail.com |
      | password              | 123456                       |
      | password_confirmation | 123456                       |
      | role                  | customer                     |
    When  With the POST method, the user sends the prepared payload to "register"
    Then  The user verifies that the status code is 201
    Then  The user verifies that the Content-Type is json
    And   The user saves "user.id" from the response body as "createdUserId"
    And   The user verifies that the "user.email" information in the response body contains "testuser"

  @getUserById @CRUDUsers
  Scenario: GET user by id
    Given The user ensures an existing user by creating one if needed
    And   The user has saved "createdUserId"
    When  The user appends path param "{id}" with value "createdUserId" and sends a request with the GET method to "users/{id}"
    Then  The user verifies that the status code is 200
    And   The user verifies that the response body contains "id" equals to saved "createdUserId"

  @updateUser @CRUDUsers
  Scenario: Update user role and status
    Given The user ensures an existing user by creating one if needed
    And   The user has saved "createdUserId"
    And   The user prepares an update payload with:
      | name                  | updateduser<timestamp>                  |
      | email                 | updateduser<timestamp>@mail.com |
      | password              | 123456                       |
      | password_confirmation | 123456                       |
    When  The user appends path param "{id}" with value "createdUserId" and sends the prepared payload with the PUT method to "users/{id}"
    Then  The user verifies that the status code is 200
    And   user verifies response contains "success"

  @deleteUser @CRUDUsers
  Scenario: Delete user by id
    Given The user ensures an existing user by creating one if needed
    And   The user has saved "createdUserId"
    When  The user appends path param "{id}" with value "createdUserId" and sends a request with the DELETE method to "users/{id}"
    Then  The user verifies that the status code is 200
    Then  The user verifies that the Content-Type is json
    Then  The user verifies that the "success" information in the response body contains "deleted"
    And   The user appends path param "{id}" with value "createdUserId" and sends a request with the GET method to "users/{id}"
    Then  The user verifies that the status code is 404

  # ----------------- NEGATIVE CASES -----------------

  @invalidEmail @negative @users
  Scenario: Create user with invalid email format
    Given The user prepares a user payload with:
      | email    | invalidmail |
      | password | Pass123!    |
      | role     | customer    |
    When  With the POST method, the user sends the prepared payload to "register"
    Then  The user verifies that the status code is 422
    And   The user verifies that the error message "email" contains "valid email"

  @unauthorized @negative @users
  Scenario: Get users without token
    Given The user removes token from the request header
    When  The user sends a request with the GET method to "users"
    Then  The user verifies that the status code is 401
