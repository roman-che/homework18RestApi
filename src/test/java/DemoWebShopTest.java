import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class DemoWebShopTest {
    @BeforeAll
    static void initBaseURIandURL() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
    }

    @Test
    void getCookieAndTestProfile(){

            String authorizationCookie =
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", "tester@qa.guru")
                            .formParam("Password", "tester@qa.guru")
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

                    open("/Themes/DefaultClean/Content/images/logo.png");

                    getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie));

                    open("/customer/info");

                    $("#gender-male").shouldBe(checked);
                    $("#FirstName").shouldHave(value("tester"));
                    $("#LastName").shouldHave(value("testeroff"));
                    $("#Email").shouldHave(value("tester@qa.guru"));
    }
}
