package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.JsonUtils;
import java.util.List;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.Authentication.getToken;
import static utilities.JsonUtils.readJson;

public class FavoritesSD {

    Response response;
    JsonNode json;
    int favId;
    int secondId;
    List<Integer> idList;


    @Given("The user sends a request with the GET method")
    public void the_user_sends_a_request_with_the_get_method() {
        response = given(spec).when().get("/api/favorites");
        response.prettyPrint(); //Ilk get'te response bos dönmeli
    }

    @Then("The user verifies that the status code is {int}")
    public void the_user_verifies_that_the_status_code_is(int statusCode) {
        response
                .then()
                .assertThat()
                .statusCode(statusCode);
    }
    @Then("The user verifies that the Content-Type is json")
    public void the_user_verifies_that_the_content_type_is_json() {
        response
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    @Given("The user saves the IDs received from AllProducts")
    public void the_user_saves_the_i_ds_received_from_all_products() {

        response = given(spec).when().get("/api/products");
        JsonPath json = response.jsonPath();

        idList = json.getList("id");
        System.out.println("idList = " + idList);
        favId = idList.get(1);
        System.out.println("favId = " + favId);
    }

    @When("With the POST method, the user sends one of the saved IDs as a request")
    public void with_the_post_method_the_user_sends_one_of_the_saved_i_ds_as_a_request() {
        json = JsonUtils.readJson("favorites\\favoritesPost"); //request body'mizi json'a ceviriyoruz
        ((ObjectNode) json).put("product_id", favId); //Json dosyamizdaki id degerini degistiriyoruz.
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
        response.prettyPrint(); //"success": "Product added favorites successfully!"

    }

    @Then("The user verifies that the {string} information in the response body is {string}")
    public void the_user_verifies_that_the_information_in_the_response_body_is(String key, String value) {
        response
                .then()
                .assertThat()
                .body(key, equalTo(value));
    }


    @Then("The user verifies that the response body contains the ID of the product added with the POST method.")
    public void the_user_verifies_that_the_response_body_contains_the_id_of_the_product_added_with_the_post_method() {
        response
                .then()
                .assertThat()
                .body("[0].product.id",equalTo(favId));
    }

    @Given("The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method")
    public void the_user_adds_the_id_previously_saved_as_a_variable_from_the_get_all_products_method_to_the_endpoint_using_the_delete_method() {
        System.out.println("fawId = " + favId); //210
        response = given(spec).when().delete("/api/favorites/"+ favId);
       response.prettyPrint();
    }

    @Then("The user verifies that the response body does not contain the ID of the product deleted using the DELETE method.")
    public void the_user_verifies_that_the_response_body_does_not_contain_the_id_of_the_product_deleted_using_the_delete_method() {
        response
                .then()
                .assertThat()
                .body("$", not(hasValue(favId)));
    }

    @When("With the POST method, the user sends one of the saved IDs as a request again")
    public void with_the_post_method_the_user_sends_one_of_the_saved_i_ds_as_a_request_again() {
        secondId = idList.get(1);
        json = JsonUtils.readJson("favorites\\favoritesPost"); //request body'mizi json'a ceviriyoruz
        ((ObjectNode) json).put("product_id", secondId); //Json dosyamizdaki id degerini degistiriyoruz.
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
        response.prettyPrint();

    }

    @Then("The user verifies that the response body contains the IDs of both products added with the POST method")
    public void the_user_verifies_that_the_response_body_contains_the_i_ds_of_both_products_added_with_the_post_method() {
       // response
              //  .then()
              //  .body("$",hasItems(fawId,secondId));

    }

    @Then("delete user")
    public void delete_user() {
//
//        response = given(spec).when().post("/api/logout");
//
//        getToken(ConfigReader.getProperty("email"));
//        response.prettyPrint();
//
//        int id =Integer.parseInt(ConfigReader.getProperty("registerId"));
//        System.out.println("id = " + id);
//
//        response = given(spec).when().delete("/api/users/"+id);
//
//        response.then().assertThat().body("success", equalTo("User deleted successfully!"));



    }



}
