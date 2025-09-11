package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import utilities.ConfigReader;
import utilities.JsonUtils;

import java.io.IOException;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.json;
import static utilities.Authentication.response;

public class UsersSD {
    private static int userId;


    Faker faker = new Faker();

    //TC_01_ListUsers - All users are listed
    @When("GET request is sent")
    public void get_request_is_sent() {
        response = given(spec).get("/api/users");
        response.prettyPrint();
    }

    // TC_02_CreateUser - New user is created
    @When("New user has been created")
    public void newUserHasBeenCreated() {
        // JSON payload dosyasını oku
        json = JsonUtils.readJson("users\\usersPostCreate");
        JsonUtils.setJson(json,"email", Faker.instance().internet().emailAddress());


        // POST request gönder ve response al
        response = given(spec)
                .body(json)
                .post("/api/register");
    }

    @And("Created user ID has been saved")
    public void createdUserIDHasBeenSaved() throws IOException {
        userId = response.jsonPath().getInt("user.id");
    }


    //TC_03_GetUserByID - Retrieve created user by ID
    @When("Created user is retrieved by ID")
    public void createdUserIsRetrievedById() {
        response = given(spec)
                .get("/api/users/" + userId);
    }

    @And("The user details are printed")
    public void printUserDetails() {
        response.prettyPrint();
    }


    //TC_04_UpdateUser - Update created user by ID
    @When("User has been updated")
    public void userHasBeenUpdated() {
    json = JsonUtils.readJson("users\\usersPutBody");

    JsonUtils.setJson(json, "email", Faker.instance().internet().emailAddress());
    response = given(spec).body(json).put("/api/users/"+userId);
    }


    //TC_05_DeleteUser - Delete created user by ID
    @When("User has been deleted")
    public void userHasBeenDeleted() {
        System.out.println("userId = " + userId);
        response = given(spec).delete("/api/users/"+userId);
        response.prettyPrint();
    }
}


