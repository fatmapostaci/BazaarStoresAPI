package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.testng.annotations.BeforeSuite;
import utilities.Authentication;

public class Hooks {
    @BeforeAll
    public static void getToken(){
        Authentication.getToken();
    }
}
