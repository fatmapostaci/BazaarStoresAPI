package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import utilities.ConfigReader;
import utilities.JsonUtils;
import java.util.List;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.Authentication.getToken;
import static utilities.Authentication.response;

public class FavoritesSD {


    JsonNode json;
    private static int favId;
    private static int secondId;
    private static List<Integer> idList;



    @Given("The user sends a request with the GET method")
    public void the_user_sends_a_request_with_the_get_method() {
        response = given(spec).when().get("/api/favorites");
        response.prettyPrint(); //Ilk get'te response bos dönmeli
    }


    @Given("The user saves the IDs received from AllProducts")
    public void the_user_saves_the_i_ds_received_from_all_products() {

        response = given(spec).when().get("/api/products");
        response.prettyPrint();
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
        System.out.println("favId = " + favId);
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
        response.prettyPrint(); //"success": "Product added favorites successfully!"

    }

    @Then("The user verifies that the response body contains the ID of the product added with the POST method.")
    public void the_user_verifies_that_the_response_body_contains_the_id_of_the_product_added_with_the_post_method() {
        System.out.println("favId = " + favId);
        response
                .then()
                .assertThat()
                .body("[0].product.id",equalTo(favId));
    }

    @Given("The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method")
    public void the_user_adds_the_id_previously_saved_as_a_variable_from_the_get_all_products_method_to_the_endpoint_using_the_delete_method() {
        System.out.println("favId = " + favId); //210
        response = given(spec).when().delete("/api/favorites/"+ favId);
        response.prettyPrint();
    }

    @Then("The user verifies that the response body does not contain the ID of the product deleted using the DELETE method.")
    public void the_user_verifies_that_the_response_body_does_not_contain_the_id_of_the_product_deleted_using_the_delete_method() {
        System.out.println("favId = " + favId);
        response
                .then()
                .assertThat()
                .body("$", not(hasValue(favId)));
        response.prettyPrint();
    }

    @When("With the POST method, the user sends one of the saved IDs as a request again")
    public void with_the_post_method_the_user_sends_one_of_the_saved_i_ds_as_a_request_again() {
         secondId = idList.get(2);
        json = JsonUtils.readJson("favorites\\favoritesPost"); //request body'mizi json'a ceviriyoruz
        ((ObjectNode) json).put("product_id", secondId); //Json dosyamizdaki id degerini degistiriyoruz.
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
        response.prettyPrint();

    }

    @Then("The user verifies that the response body contains the IDs of both products added with the POST method")
    public void the_user_verifies_that_the_response_body_contains_the_i_ds_of_both_products_added_with_the_post_method() {
        response.prettyPrint();
        System.out.println("favId = "+ favId);
        System.out.println("secondId = "+ secondId);
        response
                .then()
                .body("product.id", hasItems(favId, secondId));


    }

    @Then("delete user")
    public void delete_user() throws InterruptedException {

        int ids =Integer.parseInt(ConfigReader.getProperty("registerId"));
        System.out.println("id = " + ids);

        response = given(spec).get("/api/users");
        List<Integer> nullIds = response.jsonPath().getList("findAll{it.email_verified_at==null}.id");
        System.out.println("nullIds = " + nullIds);
        System.out.println("nullIds.size() = " + nullIds.size());

        for (int id : nullIds) {
            given(spec).delete("api/users/" + id);

        }




    }





}
