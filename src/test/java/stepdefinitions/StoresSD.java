package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import utilities.ConfigReader;
import utilities.JsonUtils;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.getToken;
import static utilities.Authentication.response;

public class StoresSD {

    public static JsonNode json;

    @And("user sends POST request to create a store")
    public void userSendsPOSTRequestToCreateAStore() {
        json = JsonUtils.readJson("stores\\storesPostBody");
        response = given(spec).body(json).post("/api/stores/create");

    }
    @And("user keeps created id")
    public void userKeepsCreatedId() {
        ConfigReader.setProperty( "validStoreId", response.jsonPath().getString("product.id") );
        System.out.println("userKeepsCreatedId=" + ConfigReader.getProperty("validStoreId"));
    }

    @Then("user verifies status code {int}")
    public void userVerifiesStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode);
    }

    @When("user sends GET request to get all stores and keep ids")
    public void userSendsGETRequestToGetAllStoresAndKeepIds() {
        response = given(spec).get("/api/stores/");
    }

    @Given("user sends get request with store id as {string}")
    public void userSendsGetRequestWithStoreIdAs(String id) {
        response = given(spec).get("/api/stores/" + ConfigReader.getProperty(id));
    }

    @Given("user sends put request with store id as {string}")
    public void userSendsPutRequestWithStoreIdAs(String id) {

        json = JsonUtils.readJson("stores\\storesPUTBody");
        response = given(spec).body(json).put("/api/stores/"+ConfigReader.getProperty(id));
    }

    @Given("user sends delete request with store id as {string}")
    public void userSendsDeleteRequestWithStoreIdAs(String id) {
        response = given(spec).body(json).delete("/api/stores/"+ConfigReader.getProperty(id));
    }

    @Given("user deletes {string} key from put json file")
    public void userDeletesAKeyFromPutJsonFile(String key) {

        System.out.println("json.toString() setJsonDeleteKey= " + json.toString());
        JsonUtils.setJsonDeleteKey(json, key);
        System.out.println("json.toString() setJsonDeleteKey = " + json.toString());
    }

    @Given("user updates {string} key with value as {string} of put json file")
    public void userUpdatesKeyWithValueAsOfPutJsonFile(String key,String value) {
        JsonUtils.setJson(json,key,value);
        System.out.println("json.toString() = " + json.toString());
    }

    @And("user renames {string} key as {string} of put json file")
    public void userRenamesKeyAsOfPutJsonFile(String oldKey, String newKey) {
        JsonUtils.setJsonRenameKey(json,oldKey,newKey);
        System.out.println("json.toString() = " + json.toString());
    }
}
