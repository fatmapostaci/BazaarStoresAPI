package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.JsonUtils.readJson;

public class Authentication {

    public static Response response;
    public  static JsonNode json;
    public static void getToken() {

        response = given(spec)
                .body(readJson("authentication\\credentials"))
                .post("/api/login");
        response.prettyPrint();
        spec.header("Authorization", "Bearer " + response.jsonPath().getString("authorisation.token"));

    }
    public static void getToken(String email) {

        json = JsonUtils.readJson("authentication\\credentials");
        JsonUtils.setJson(json,"email",email);
        response = given(spec)
                .body(json)
                .post("/api/login");
        response.prettyPrint();
        spec.header("Authorization", "Bearer " + response.jsonPath().getString("authorisation.token"));

    }


}