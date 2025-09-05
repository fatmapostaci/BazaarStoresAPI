package stepdefinitions;

import io.cucumber.java.en.Given;

import static utilities.Authentication.getToken;

public class AuthenticationSD {

    @Given("user gets token")
    public void userGetsToken() {
        getToken();
    }
    @Given("user gets token as {string}")
    public void userGetsTokenAs(String email) {
        getToken(email);
    }

}
