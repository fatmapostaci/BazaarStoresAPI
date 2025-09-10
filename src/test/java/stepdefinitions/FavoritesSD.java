package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import utilities.ConfigReader;
import java.util.List;
import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.Authentication.json;
import static utilities.Authentication.response;
import static utilities.JsonUtils.readJson;
import static utilities.JsonUtils.setJson;

public class FavoritesSD {

    private static List<Integer> idList;

    @Given("The user sends a request with the GET method")
    public void the_user_sends_a_request_with_the_get_method() {
        response = given(spec).when().get("/api/favorites");
    }

    @Given("The user saves the IDs received from AllProducts")
    public void the_user_saves_the_i_ds_received_from_all_products() { //Tüm ürünleri get ile cagirip id'lerini bir liste kaydediyorum
        response = given(spec).when().get("/api/products");
        JsonPath json = response.jsonPath();
        idList = json.getList("id");
        ConfigReader.setProperty("favId", String.valueOf(idList.get(1)));
    }

    @Given("With the POST method, the user sends one of the saved IDs as a request")
    public void with_the_post_method_the_user_sends_one_of_the_saved_i_ds_as_a_request() {
        json = readJson("favorites\\favoritesPost"); //request body'mizi json'a ceviriyoruz
        setJson(json,"product_id",ConfigReader.getProperty("favId"));//Json dosyamizdaki id degerini degistiriyoruz.
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
    }

    @Then("The user verifies that the response body contains the ID of the product added with the POST method.")
    public void the_user_verifies_that_the_response_body_contains_the_id_of_the_product_added_with_the_post_method() {
        response
                .then()
                .assertThat()
                .body("[0].product.id",equalTo(Integer.parseInt(ConfigReader.getProperty("favId"))));
    }

    @Given("The user adds the ID, previously saved as a variable from the Get AllProducts method, to the endpoint using the DELETE method")
    public void the_user_adds_the_id_previously_saved_as_a_variable_from_the_get_all_products_method_to_the_endpoint_using_the_delete_method() {
        response = given(spec).when().delete("/api/favorites/"+ ConfigReader.getProperty("favId"));
    }

    @Then("The user verifies that the response body does not contain the ID of the product deleted using the DELETE method.")
    public void the_user_verifies_that_the_response_body_does_not_contain_the_id_of_the_product_deleted_using_the_delete_method() {
        response
                .then()
                .assertThat()
                .body("$", not(hasValue(ConfigReader.getProperty("favId"))));
    }

    @When("The user sends a POST request with one of the saved IDs again")
    public void the_user_sends_a_post_request_with_one_of_the_saved_i_ds_again() {
        ConfigReader.setProperty("secondId", String.valueOf(idList.get(2)));
        json = readJson("favorites\\favoritesPost"); //request body'mizi json'a ceviriyoruz
        setJson(json,"product_id",ConfigReader.getProperty("secondId"));
        //((ObjectNode) json).put("product_id", ConfigReader.getProperty("secondId")); //Json dosyamizdaki id degerini degistiriyoruz.
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
    }

    @Then("The user verifies that the response body contains the IDs of both products added with the POST method")
    public void the_user_verifies_that_the_response_body_contains_the_i_ds_of_both_products_added_with_the_post_method() {
        response.then()
                .assertThat()
                .body("[0].product.id", equalTo(Integer.parseInt(ConfigReader.getProperty("favId"))));
    }

    @Then("delete user")
    public void delete_user() throws InterruptedException {

        response = given(spec).get("/api/users");
        List<Integer> nullIds = response.jsonPath().getList("findAll{it.email_verified_at==null}.id");
        for (int id : nullIds) {
            given(spec).delete("api/users/" + id);
        }
    }

    @When("The user sends a POST request with the invalid IDs")
    public void the_user_sends_a_post_request_with_the_invalid_i_ds() {
        json = readJson("favorites\\favoritesPost");
        response = given(spec)
                .body(json)
                .post("/api/favorites/create");
    }

    @Then("The user verifies that the status code is not {int}")
    public void the_user_verifies_that_the_status_code_is_not(Integer statusCode) {
        Assert.assertNotEquals(response.getStatusCode(),statusCode);
    }

    @Given("The user sends a DELETE request with an invalid ID to the endpoint")
    public void the_user_sends_a_delete_request_with_an_invalid_id_to_the_endpoint() {
        json = readJson("favorites\\favoritesPost");
        String invalidId = json.get("product_id").asText();
        response = given(spec).when().delete("/api/favorites/"+ invalidId);
    }

    }
