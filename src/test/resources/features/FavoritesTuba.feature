@FavoriTuba
Feature: Favorites tuba

  Background: : Create user
    Given user sends valid POST request to register
    When  user gets token as "registeredEmail"

  @GetFavorites
  Scenario: GET all favorites
    Given The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json
    And delete user tubaca

  @PostFavorites
  Scenario: Create a favorite product
    Given The user saves the IDs received from AllProducts
    When With the POST method, the user sends one of the saved IDs as a request
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json
    Then The user verifies that the "success" information in the response body is "Product added favorites successfully!"


  @GetFavorites
  Scenario: List of added favorite products
    Given The user saves the IDs received from AllProducts
    When With the POST method, the user sends one of the saved IDs as a request
    And The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the response body contains the ID of the product added with the POST method.


  @deleteFavorites
  Scenario: Delete favorite product with ID
    Given The user saves the IDs received from AllProducts
    When With the POST method, the user sends one of the saved IDs as a request
    And The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json
    Then The user verifies that the "success" information in the response body is "Favorite product deleted successfully!"