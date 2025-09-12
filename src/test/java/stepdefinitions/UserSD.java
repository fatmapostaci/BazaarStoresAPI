package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static utilities.Authentication.response;

public class UserSD {

    private static final String API_BASE_PATH = ConfigReader.getProperty("API_BASE_PATH");
    private static final String LOGIN_PATH = ConfigReader.getProperty("LOGIN_PATH");
    private static final String TOKEN_PATH = ConfigReader.getProperty("TOKEN_JSON_PATH");

    private String activeToken;
    private final Map<String, Object> context = new HashMap<>();      // createdUserId, tokens, vs.
    private Map<String, Object> queryParams;
    private Map<String, Object> pathParams;                        // {id}
    private Map<String, Object> body;                           // request body

    // Login

    @Given("user sends POST request to login with email {string} and password {string}")
    public void loginWithEmailAndPassword(String email, String password) {
        String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        response =
                given()
                        .baseUri(ConfigReader.getProperty("base_url"))
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post(LOGIN_PATH);
        assertThat(response.statusCode()).isBetween(200, 201);
        String token = response.jsonPath().getString(TOKEN_PATH);
        assertThat(token).isNotBlank();
        this.activeToken = token;
    }

    @When("user gets token as role {string}")
    public void userGetsTokenAs(String tokenAlias) {
        assertThat(activeToken).isNotBlank();
        context.put(tokenAlias, activeToken);
    }

    @Given("The user removes token from the request header")
    public void removeTokenFromHeader() {
        this.activeToken = null;
    }

    @Given("The user prepares a unique user payload with:")
    public void prepareUniquePayload(DataTable table) {
        body = overrideTimestampPlaceholder(table.asMap(String.class, String.class));
    }

    @Given("The user prepares a user payload with:")
    public void preparePayload(DataTable table) {
        body = overrideTimestampPlaceholder(table.asMap(String.class, String.class));
    }

    @Given("The user prepares an update payload with:")
    public void prepareUpdatePayload(DataTable table) {
        body = overrideTimestampPlaceholder(table.asMap(String.class, String.class));
    }

    @Given("The user prepares a non-existing id {string}")
    public void prepareNonExistingId(String id) {
        context.put("nonExistingId", id);
    }

    @And("The user has saved {string}")
    public void userHasSaved(String key) {
        assertThat(context.containsKey(key)).isTrue();
    }

    @And("The user ensures an existing user by creating one if needed")
    public void ensureExistingUser() {
        if (context.get("createdUserId") == null) {
            Map<String, Object> newUser = new HashMap<>();
            long currentTimeMillis = System.currentTimeMillis();
            String email = "autouser" + currentTimeMillis + "@mail.com";
            newUser.put("name", "autouser" + currentTimeMillis);
            newUser.put("email", email);
            newUser.put("password", "123456");
            newUser.put("password_confirmation", "123456");
            newUser.put("role", "store_manager");
            postTo("register", newUser);
            assertThat(response.statusCode()).isIn(200, 201);
            String id = response.jsonPath().getString("user.id");
            context.put("createdUserId", id);
            context.put("createdUserEmail", email);
        }
    }

    @Given("The user sends a request with the GET method to {string}")
    public void sendGET(String path) {
        getTo(path);
    }

    @When("With the POST method, the user sends the prepared payload to {string}")
    public void sendPOSTWithPreparedPayload(String path) {
        postTo(path, body);
    }

    @When("The user appends path param {string} with value {string} and sends a request with the GET method to {string}")
    public void sendGETWithPathParam(String paramName, String valueKey, String path) {
        Map<String, Object> pp = createPathParametersMap(paramName, valueKey);
        response = request("GET", path, queryParams, null, pp);
    }

    @When("The user appends path param {string} with value {string} and sends the prepared payload with the PUT method to {string}")
    public void sendPUTWithPathParam(String paramName, String valueKey, String path) {
        Map<String, Object> pp = createPathParametersMap(paramName, valueKey);
        request("PUT", path, queryParams, body, pp);
        response = request("PUT", path, queryParams, body, pp);
    }

    @When("The user appends path param {string} with value {string} and sends a request with the DELETE method to {string}")
    public void sendDELETEWithPathParam(String paramName, String valueKey, String path) {
        Map<String, Object> pp = createPathParametersMap(paramName, valueKey);
        response = request("DELETE", path, queryParams, null, pp);
    }

    @And("The user verifies that the response body contains a non-empty users list")
    public void verifyNonEmptyUsersList() {
        JsonPath jp = response.jsonPath();
        List<?> list = jp.get();
        assertThat(list).isNotNull();
        assertThat(list.size()).isGreaterThan(0);
    }

    @And("The user saves {string} from the response body as {string}")
    public void saveFieldAs(String jsonPath, String key) {
        String value = response.jsonPath().getString(jsonPath);
        assertThat(value).isNotBlank();
        context.put(key, value);
    }

    @And("The user verifies that the {string} information in the response body contains {string}")
    public void verifyFieldContains(String field, String fragment) {
        Object o = response.jsonPath().getString(field);
        String actual = o == null ? null : String.valueOf(o);
        assertThat(actual).contains(fragment);
    }

    @And("The user verifies that the response body contains {string} equals to saved {string}")
    public void verifyBodyFieldEqualsSaved(String field, String savedKey) {
        Object o1 = response.jsonPath().getString(field);
        String actual = o1 == null ? null : String.valueOf(o1);
        Object o = context.get(savedKey);
        String expected = o == null ? null : String.valueOf(o);
        assertThat(actual).isEqualTo(expected);
    }

    @Then("The user verifies that the error message {string} contains {string}")
    public void verifyErrorMessageContains(String field, String expectedFragment) {
        JsonPath jp = response.jsonPath();
        String msg = jp.getString(field);
        assertThat(msg.toLowerCase()).contains(expectedFragment.toLowerCase());

    }

    private void getTo(String path) {
        response = request("GET", path, queryParams, null, null);
    }

    private void postTo(String path, Map<String, Object> body) {
        response = request("POST", path, null, body, null);
    }

    private Response request(String method, String path,
                             Map<String, Object> queryParams,
                             Map<String, Object> body,
                             Map<String, Object> pathParams) {
        var spec2 = given()
                .baseUri(ConfigReader.getProperty("base_url"))
                .basePath(API_BASE_PATH)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        if (activeToken != null && !activeToken.isBlank()) {
            spec2.header("Authorization", "Bearer " + activeToken);
        }

        if (queryParams != null && !queryParams.isEmpty()) spec2.queryParams(queryParams);
        if (pathParams != null && !pathParams.isEmpty()) spec2.pathParams(pathParams);
        if (body != null) spec2.contentType(ContentType.JSON).body(body);

        return switch (method.toUpperCase()) {
            case "GET" -> spec2.when().get(path);
            case "POST" -> spec2.when().post(path);
            case "PUT" -> spec2.when().put(path);
            case "DELETE" -> spec2.when().delete(path);
            default -> throw new IllegalArgumentException("Unsupported method: " + method);
        };
    }

    private Map<String, Object> createPathParametersMap(String name, String valueKey) {
        String key = name.replace("{", "").replace("}", "");
        String value;
        if (context.containsKey(valueKey)) {
            Object o = context.get(valueKey);
            value = o == null ? null : String.valueOf(o);
        } else {
            value = valueKey;
        }
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private static Map<String, Object> overrideTimestampPlaceholder(Map<String, String> in) {
        Map<String, Object> out = new HashMap<>();
        long ts = System.currentTimeMillis();
        in.forEach((k, v) -> out.put(k, v == null ? null : v.replace("<timestamp>", String.valueOf(ts))));
        return out;
    }

}
