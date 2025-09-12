package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.testng.Assert;
import utilities.JsonUtils;

import java.util.List;

import static base_urls.BazaarStoresBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilities.Authentication.json;
import static utilities.Authentication.response;


public class ProductsMehmetSD {
    private static int productId;
    Faker faker = new Faker();

    //TC_01_GET List All Products
    @When("The user sends a GET request to list all products")
    public void theUserSendsAGETRequestToListAllProducts() {
        response = given(spec)
                .when()
                .get("/api/products");
    }

    @And("The user verifies that the product list is not empty")
    public void theUserVerifiesThatTheProductListIsNotEmpty() {
        List<Integer> productIds = response.jsonPath().getList("id");
        Assert.assertTrue(productIds.size() > 0, "Product list is empty!");
    }

    //TC_02_POST Create Product
    @When("New product has been created")
    public void newProductHasBeenCreated() {
        json = JsonUtils.readJson("productsMehmet\\createProduct");

        JsonUtils.setJson(json, "name", "Product_" + Faker.instance().commerce().productName());
        JsonUtils.setJson(json, "description", "Description " + Faker.instance().lorem().sentence());
        JsonUtils.setJson(json, "price", String.valueOf(Faker.instance().number().randomDouble(2, 10, 500))); // float
        JsonUtils.setJson(json, "stock", String.valueOf(Faker.instance().number().numberBetween(1, 200))); // integer
        JsonUtils.setJson(json, "sku", "SKU_" + System.currentTimeMillis());
        JsonUtils.setJson(json, "category_id", String.valueOf(1)); // sabit geçerli kategori
        JsonUtils.setJson(json, "manufacturer", "Manufacturer " + Faker.instance().company().name());
        JsonUtils.setJson(json, "image_url", "images/" + System.currentTimeMillis() + ".jpg");
        JsonUtils.setJson(json, "discount", String.valueOf(Faker.instance().number().randomDouble(2, 0, 1))); // opsiyonel

        response = given(spec)
                .body(json)
                .post("/api/products/create");
    }

    @And("Created product ID has been saved")
    public void createdProductIDHasBeenSaved() {
        productId = response.jsonPath().getInt("product.id");
    }

    //TC_03_GET Retrieve Product By ID
    @When("The created product is retrieved by ID")
    public void the_created_product_is_retrieved_by_ID() {
        response = given(spec)
                .get("/api/products/" + productId);
    }

    @Then("The product details are printed")
    public void the_product_details_are_printed() {
    }

    //TC_04_PUT Update Product
    @When("The product has been updated")
    public void theProductHasBeenUpdated() {
        json = JsonUtils.readJson("productsMehmet\\updateProduct");

        JsonUtils.setJson(json, "name", "Product_" + Faker.instance().commerce().productName());
        JsonUtils.setJson(json, "description", "Description " + Faker.instance().lorem().sentence());
        JsonUtils.setJson(json, "price", String.valueOf(Faker.instance().number().randomDouble(2, 1, 1000))); // float olarak gönder
        JsonUtils.setJson(json, "stock", String.valueOf(Faker.instance().number().numberBetween(10, 500))); // integer
        JsonUtils.setJson(json, "sku", "SKU_" + System.currentTimeMillis()); // benzersiz olmalı
        JsonUtils.setJson(json, "category_id", String.valueOf(1)); // sabit geçerli kategori
        JsonUtils.setJson(json, "manufacturer", Faker.instance().company().name());
        JsonUtils.setJson(json, "discount", String.valueOf(Faker.instance().number().randomDouble(2, 0, 1))); // opsiyonel
        JsonUtils.setJson(json, "image_url", null); // opsiyonel

        response = given(spec)
                .contentType(ContentType.JSON)
                .body(json.toString())
                .when()
                .put("/api/products/" + productId);
    }

    //TC_05_DELETE Remove Product
    @When("The product has been deleted")
    public void theProductHasBeenDeleted() {
        System.out.println("userId = " + productId);
        response = given(spec).delete("/api/products/" + productId);
    }

    //TC_06_GET Verify Deleted Product

    //TC_03_GET Retrieve Product By ID'de yazildi
}
