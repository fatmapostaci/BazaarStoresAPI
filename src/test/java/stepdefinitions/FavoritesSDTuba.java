package stepdefinitions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.JsonUtils;

import java.util.List;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.Authentication.getToken;
import static utilities.Authentication.response;

public class FavoritesSDTuba {

    Response response;
    private static int favId;
    private static int secondId;
    private static List<Integer> idList;


    @And("delete user tubaca")
    public void deleteUserTubaca() {
        response = given(spec).when().post("/api/logout");

        getToken(ConfigReader.getProperty("email"));
        response.prettyPrint();

        int id =Integer.parseInt(ConfigReader.getProperty("registerId"));
        System.out.println("id = " + id);

        response = given(spec).when().delete("/api/users/"+id);

        response.then().assertThat().body("success", equalTo("User deleted successfully!"));

    }
}
