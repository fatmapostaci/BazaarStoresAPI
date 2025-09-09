package stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import static utilities.Authentication.response;

public class CommonSD {


    @Then("The user verifies that the status code is {int}")
    public void the_user_verifies_that_the_status_code_is(int statusCode) {
        response
                .then()
                .assertThat()
                .statusCode(statusCode);
    }

    @Then("The user verifies that the Content-Type is json")
    public void the_user_verifies_that_the_content_type_is_json() {
        response
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
    }

    @Then("The user verifies that the {string} information in the response body is {string}")
    public void the_user_verifies_that_the_information_in_the_response_body_is(String key, String value) {
        response
                .then()
                .assertThat()
                .body(key, equalTo(value));
    }

}
