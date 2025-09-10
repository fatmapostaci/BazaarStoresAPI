@CRUD_Products
Feature: Products
@GETProducts
  Scenario: : Get Products
  Given user gets token
  Then user sends get request to get products info
  And The user verifies that the status code is 200
@POSTProducts
  Scenario: NewCreatProduct
    Given user sets the payload for new product body
    When user sends post request to new create product
    Then user validates new created product body
    And The user verifies that the status code is 201

  Scenario: GeTproductID
    When user sends get request to new create product with id
    Then user validates new created product with id
    And The user verifies that the status code is 200
  @PUTProducts
  Scenario: UpdateNewCreatProduct
    Given user sets the payload for new product body update
    When user sends put request to new create product
    Then user validates new created product body update
    And The user verifies that the status code is 200
  @DELETEProducts
  Scenario: Delete an existing product
    When user sends delete request for the product
    Then user validates product deletion
    And The user verifies that the status code is 200