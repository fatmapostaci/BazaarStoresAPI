@CRUD_Products
Feature: Products

  Scenario: : Get Products
  Given user gets token as "customer@outlook.com"
  Then user sends get request to get products info
  And user validates status code

  Scenario: NewCreatProduct
    Given user sets the payload for new product body
    When user sends post request to new create product
    Then user validates new created product body
    And user validates new created product status code

  Scenario: UpdateNewCreatProduct
    Given user sets the payload for new product body update
    When user sends put request to new create product
    Then user validates new created product body update
    And user validates new created product update status code

  Scenario: Delete an existing product
    When user sends delete request for the product
    Then user validates product deletion
    And user validates status code is 200