package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static utilities.Authentication.getToken;

public class ProductsGetAllLIstSD {
    Response response;

    @Then("user sends get request to get products info")
    public void user_sends_get_request_to_get_products_info() {

       // response =  given(spec).body(json).get("/api/products");;
        response=given(spec).get("/api/products");

        //response.prettyPrint();
    }
    @Then("user validates status code")
    public void user_validates_status_code() {
        int size=response.jsonPath().getList("Products").size();
       response.then().statusCode(200);
       assertEquals(18, size);
    }
}
