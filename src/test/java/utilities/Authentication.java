package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.JsonUtils.readJson;

public class Authentication {

    public  static JsonNode json;
    public static void getToken() {

        Response response = given(spec)
                .body(readJson("authentication\\credentials"))
                .post("/api/login");
        response.prettyPrint();
        spec.header("Authorization", "Bearer " + response.jsonPath().getString("authorisation.token"));
        // spec.header("Authorization", "Bearer " + response.jsonPath().getString("_id"));

    }
    public static void getToken(String email) {

        json = JsonUtils.readJson("authentication\\credentials");
        JsonUtils.setJson(json,"email",email);
        Response response = given(spec)
                .body(json)
                .post("/api/login");
        response.prettyPrint();
        spec.header("Authorization", "Bearer " + response.jsonPath().getString("authorisation.token"));
        // spec.header("Authorization", "Bearer " + response.jsonPath().getString("_id"));

    }


}