package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.testng.Assert;
import utilities.JsonUtils;

import java.io.IOException;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.json;
import static utilities.Authentication.response;

public class UsersMehmetSD {
    private static int userId;
    Faker faker = new Faker();

    //TC_01_GET List All Users
    @When("GET request is sent")
    public void get_request_is_sent() {
        response = given(spec).get("/api/users");
    }

    // TC_02_POST Create User
    @When("New user has been created")
    public void newUserHasBeenCreated() {
        json = JsonUtils.readJson("usersMehmet\\createUser");
        JsonUtils.setJson(json, "email", Faker.instance().internet().emailAddress());


        // POST request gönder ve response al
        response = given(spec)
                .body(json)
                .post("/api/register");
    }

    @And("Created user ID has been saved")
    public void createdUserIDHasBeenSaved() throws IOException {
        userId = response.jsonPath().getInt("user.id");
    }

    //TC_03_GET Retrieve User By ID
    @When("Created user is retrieved by ID")
    public void createdUserIsRetrievedById() {
        response = given(spec)
                .get("/api/users/" + userId);
    }

    @And("The user details are printed")
    public void printUserDetails() {
    }

    //TC_04_PUT Update User
    @When("User has been updated")
    public void userHasBeenUpdated() {
        json = JsonUtils.readJson("usersMehmet\\updateUser");

        JsonUtils.setJson(json, "email", Faker.instance().internet().emailAddress());
        response = given(spec).body(json).put("/api/users/" + userId);
    }

/*
    @When("User has been updated")
    public void userHasBeenUpdated() {
        json = JsonUtils.readJson("usersMehmet\\updateUser");
        JsonUtils.setJson(json, "email", Faker.instance().internet().emailAddress());

        for (int i = 0; i < 2; i++) {
            response = given(spec)
                    .contentType(ContentType.JSON)
                    .body(json.toString())
                    .when()
                    .put("/api/users/" + userId);
            if (response.statusCode() == 200) break;
        }

        Assert.assertEquals(response.statusCode(), 200, "PUT request başarısız oldu.");
    }*/

    //TC_05_DELETE Remove User
    @When("User has been deleted")
    public void userHasBeenDeleted() {
        System.out.println("userId = " + userId);
        response = given(spec).delete("/api/users/" + userId);
    }

    //TC_06_GET Verify Deleted User

    //TC_03_GET Retrieve User By ID'de yazildi
}


