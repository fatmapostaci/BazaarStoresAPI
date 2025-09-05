package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.JsonUtils;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.getToken;

public class StoresSD {

    public static JsonNode json;
    public static Response response;




    @And("user sends POST request")
    public void userSendsPOSTRequest() {
        json = JsonUtils.readJson("stores\\storesPostBody");

        response =  given(spec).body(json).post("/api/stores/create");
         response.prettyPrint();
    }

    @Then("user verifies status code {int}")
    public void userVerifiesStatusCode(int arg) {
        Assert.assertEquals(response.statusCode(),arg);
    }

}
