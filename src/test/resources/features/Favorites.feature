Feature: Favorites


  @FirstGetFav @CRUDFav
  Scenario: GET all favorites
    Given user sends POST request to register
    When  user gets token as "registeredEmail"
    Given The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the Content-Type is json

  @PostFav @CRUDFav
  Scenario: Create a favorite product
    Given The user saves the IDs received from AllProducts
    When With the POST method, the user sends one of the saved IDs as a request
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
    Given With the POST method, the user sends one of the saved IDs as a request
    Then The user verifies that the "success" information in the response body is "Product added favorites successfully!"
    When With the POST method, the user sends one of the saved IDs as a request again
    Then The user verifies that the "success" information in the response body is "Product added favorites successfully!"
    And The user sends a request with the GET method
    Then The user verifies that the status code is 200
    Then The user verifies that the response body contains the IDs of both products added with the POST method
    Then The user verifies that the Content-Type is json

  @delete @CRUDFav
  Scenario: Delete all null users
    And delete user


