package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.JsonUtils;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.getToken;

public class AuthenticationFatmaSD {

    public static JsonNode json;
    public static Response response;


    @Given("user gets token")
    public void userGetsToken() {
        getToken();
    }
    @Given("user gets token as {string}")
    public void userGetsTokenAs(String email) {
        getToken(ConfigReader.getProperty(email));
    }

    @And("user sends valid POST request to register")
    public void userSendsValidPOSTRequestToRegister() {

        json = JsonUtils.readJson("authentication\\register");
        JsonUtils.setJson(json,"email", ("user"+ (int)(Math.random()*1000)+"@gmail.com") );  //email update edilir.
        response = given(spec)
                    .body(json)
                    .post("/api/register");

        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        ConfigReader.setProperty( "registeredEmail", jsonPath.get("user.email") );  //değer confite saklanır
        ConfigReader.setProperty( "registerId", jsonPath.get("user.id")+"");

        System.out.println(ConfigReader.getProperty("registeredEmail"));
        System.out.println("Register ID = "+ConfigReader.getProperty("registerId"));

    }

    @When("user sends invalid POST request to register")
    public void userSendsInvalidPOSTRequestToRegister() {
        json = JsonUtils.readJson("authentication\\register");
        JsonUtils.setJson(json,"email", "invaliduseremail" );  //email update edilir.
        response = given(spec)
                .body(json)
                .post("/api/register");

        response.prettyPrint();

    }

    @Given("user logs in successfully")
    public void userLogsInSuccessfully() {
        json = JsonUtils.readJson("authentication\\credentials");
        JsonUtils.setJson(json,"email",ConfigReader.getProperty("registeredEmail"));

        response = given(spec).body(json).post("/api/login");
        response.prettyPrint();

    }
    @When("user logs in with invalid credentials")
    public void userLogsInWithInvalidCredentials() {

        json = JsonUtils.readJson("authentication\\credentials");
        JsonUtils.setJson(json,"email",ConfigReader.getProperty("registeredEmail"));
        JsonUtils.setJson(json,"password","wrongpassword");

        response = given(spec).body(json).post("/api/login");
        response.prettyPrint();
    }

    @When("user logs in incorrectly")
    public void userLogsInIncorrectly() {
        json = JsonUtils.readJson("authentication\\error");
        JsonUtils.setJson(json,"email","");
        JsonUtils.setJson(json,"password","");

        response = given(spec).body(json).post("/api/login");

        response.prettyPrint();
    }

    @Given("user logs out")
    public void userLogsOut() {

        response = given(spec).post("/api/logout/");
        response.prettyPrint();
    }


    @Given("user sends GET request to get current authenticated user")
    public void userSendsGETRequestToGetCurrentAuthenticatedUser() {
        response = given(spec).get("/api/me");
        response.prettyPrint();
    }



}
