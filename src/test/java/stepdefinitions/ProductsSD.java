package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.JsonUtils;

import java.io.IOException;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static utilities.Authentication.getToken;

public class ProductsSD {
    public static Response response;


    @Then("user sends get request to get products info")
    public void user_sends_get_request_to_get_products_info() {

       // response =  given(spec).body(json).get("/api/products");;
        response=given(spec).get("/api/products");

        response.prettyPrint();
    }
    @Then("user validates status code")
    public void user_validates_status_code() {
        int size=response.jsonPath().getList("Products").size();
       response.then().statusCode(200);
       //assertEquals(18, size);
    }
//-------------------------**POST**---------------------------------
    public static String createdId;
    public static JsonNode json;
    @Given("user sets the payload for new product body")
    public void user_sets_the_payload_for_new_product_body() {
        json= JsonUtils.readJson("/productsPostBody");
        System.out.println("json = " + json);


    }
    @When("user sends post request to new create product")
    public void user_sends_post_request_to_new_create_product() {

        response= given(spec).body(json).post("/api/products/create");
        response.prettyPrint();
    }

    @Then("user validates new created product body")
    public void user_validates_new_created_product_body() {
        response.then().body
                ("product.name",equalTo(json.get("name").asText()),
                        "product.stock",equalTo(100)
                        ,"product.sku",equalTo(json.get("sku").asText()));
        response.prettyPrint();

    }

    @Then("user validates new created product status code")
    public void user_validates_new_created_product_status_code() {
        response.then().statusCode(201);
        //     spec.header("Authorization", "Created_Id " + response.jsonPath().getString("id"));
        createdId = response.jsonPath().getString("product.id");
        System.out.println("Created product_id: " + createdId);
}
//-----------------------**PUT**-----------------------------------
@Given("user sets the payload for new product body update")
public void user_sets_the_payload_for_new_product_body_update() throws IOException {
    json= JsonUtils.readJson("productsPostBody");
    System.out.println(json);
    JsonUtils.setJson(json, "sku",""+Math.random());
    JsonUtils.setJson(json, "name","LG");
    System.out.println(json);

}

    @When("user sends put request to new create product")
    public void user_sends_put_request_to_new_create_product() {
        System.out.println(createdId);
        response=given(spec).body(json).put("/api/products/" + createdId);
        response.prettyPrint();
    }

    @Then("user validates new created product body update")
    public void user_validates_new_created_product_body_update() {
        assertTrue(response.asString().contains("Product updated successfully!"));
    }

    @Then("user validates new created product update status code")
    public void user_validates_new_created_product_update_status_code() {
        response.then().statusCode(200);
    }
//----------------------------**DELETE**-----------------------------

    @When("user sends delete request for the product")
    public void user_sends_delete_request_for_the_product() {
        response=given(spec).delete("api/products/"+createdId);
    }
    @Then("user validates product deletion")
    public void user_validates_product_deletion() {
        assertTrue(response.asString().contains("Product deleted successfully!"));
    }
    @Then("user validates status code is {int}")
    public void user_validates_status_code_is(Integer int1) {
response.then().statusCode(200);
    }
    }