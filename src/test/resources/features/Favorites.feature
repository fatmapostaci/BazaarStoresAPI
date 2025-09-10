Feature: Favorites

  @FirstGetFav @CRUDFav
  Scenario: GET all favorites
    Given user sends valid POST request to register
    When  user gets token as "registeredEmail"
    Given The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json

  @PostFav @CRUDFav
  Scenario: Create a favorite product
    Given The user saves the IDs received from AllProducts
    When The user sends a POST request with one of the saved IDs
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json
    Then The user verifies that the "success" information in the response body is "Product added favorites successfully!"

  @secondGetFav @CRUDFav
  Scenario: List of added favorite products
    Given The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the response body contains the ID of the product added with the POST method.

  @deleteFav @CRUDFav
  Scenario: Delete favorite product with ID
    Given The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json
    Then The user verifies that the "success" information in the response body is "Favorite product deleted successfully!"

  @thirdGetFav @CRUDFav
  Scenario: List of deleted favorite products
    Given The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method
    And The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the response body does not contain the ID of the product deleted using the DELETE method.

  @multiplePostFav @CRUDFav
  Scenario: Adding multiple products to favorites and listing of favorite products
    Given The user sends a POST request with one of the saved IDs
    When The user sends a POST request with one of the saved IDs again
    Then The user verifies that the "success" information in the response body is "Product added favorites successfully!"
    And The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the response body contains the IDs of both products added with the POST method
    Then The user verifies that the Content-Type is json

  @PostFavWithInvalidId @CRUDFav
  Scenario: Adding product to favorites with an invalid ID - NEGATIVE
    When The user sends a POST request with the invalid IDs
    Then The user verifies that the status code is not 200
    Then The user verifies that the "error" information in the response body is "Product added favorites failed!"

  @deleteFavWithInvalidId @CRUDFav
  Scenario: Deleting a product from favorites with an invalid ID - NEGATIVE
    Given The user sends a DELETE request with an invalid ID to the endpoint
    Then The user verifies that the status code is not 200
    Then The user verifies that the "error" information in the response body is "Favorite not found."


  @PostFavWithSameProducts @CRUDFav
  Scenario: Adding a product to favorites that is already in favorites NEGATIVE
    Given The user sends a POST request with one of the saved IDs
    Then The user verifies that the "error" information in the response body is "Product is already in favorites."
    Then The user verifies that the status code is not 200

  @delete @CRUDFav
  Scenario: Delete all null users
    And delete user

  @GetFavWithoutToken @CRUDFav
    Scenario: Accessing favorites without token - NEGATIVE
      Given user logs out
      When The user sends a request with the GET method
      Then The user verifies that the "message" information in the response body is "Unauthenticated."
      Then The user verifies that the status code is not 200
