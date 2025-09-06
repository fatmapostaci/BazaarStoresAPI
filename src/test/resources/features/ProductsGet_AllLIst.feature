Feature: Products
@GetProducts @CRUD_Products
  Scenario: Get Products
  Given user gets token as "customer@outlook.com"
  Then user sends get request to get products info
  And user validates status code
