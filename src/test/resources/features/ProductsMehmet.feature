@ProductCRUD @Mehmet
Feature: Product API End-to-End Tests

  Scenario: TC_01_PRODUCTS_GET List All Products
    Given user gets token
    When The user sends a GET request to list all products
    Then The user verifies that the status code is 200
    And The user verifies that the product list is not empty

  Scenario: TC_02_PRODUCTS_POST Create Product
    When New product has been created
    Then The user verifies that the status code is 201
    And Created product ID has been saved

  Scenario: TC_03_PRODUCTS_GET Retrieve Product By ID
    When The created product is retrieved by ID
    Then The user verifies that the status code is 200
    And The product details are printed

  Scenario: TC_04_PRODUCTS_PUT Update Product
    When The product has been updated
    Then The user verifies that the status code is 200
    And The product details are printed

  Scenario: TC_05_PRODUCTS_DELETE Remove Product
    When The product has been deleted
    Then The user verifies that the status code is 200

  Scenario: TC_06_PRODUCTS_GET Verify Deleted Product
    When The created product is retrieved by ID
    Then The user verifies that the status code is 404
    And The product details are printed
