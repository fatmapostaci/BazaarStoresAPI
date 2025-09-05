package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.JsonUtils;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.getToken;

public class AuthenticationSD {

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

    @And("user sends POST request to register")
    public void userSendsPOSTRequestToRegister() {

        json = JsonUtils.readJson("authentication\\register");
        JsonUtils.setJson(json,"email", ("user"+ (int)(Math.random()*1000)+"@gmail.com") );  //email update edilir.
        response = given(spec)
                    .body(json)
                    .post("/api/register");
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();
        ConfigReader.setProperty( "registeredEmail", jsonPath.get("user.email") );  //değer confite saklanır

        System.out.println(ConfigReader.getProperty("registeredEmail"));

    }


}
