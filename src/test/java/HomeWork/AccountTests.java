package HomeWork;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static HomeWork.Endpoints.GET_ACCOUNT;

public class AccountTests extends BaseTest{





    @Test
    void getAccountInfoTest() {
        given()
                .header("Authorization", token)
                .when()
                .get("https://api.imgur.com/3/account/{username}", username);
    }
    @Test
    void getAccountInfoWithLoggingTest() {
        given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}", username)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getAccountInfoWithAssertionsInGivenTest() {
        given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .statusCode(200)
                .body("data.url", equalTo(username))
                .body("success", equalTo(true))
                .body("status", equalTo(200))
                .contentType("application/json")
                .when()
                .get("https://api.imgur.com/3/account/{username}", username)
                .prettyPeek();

    }

    @Test
    void getAccountInfoWithAssertionsAfterTest() {
        Response response = given()
                .header("Authorization", token)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get(GET_ACCOUNT, username)
                .prettyPeek();
        assertThat(response.jsonPath().get("data.url"), equalTo(username));
    }

    @Test
    void getAccountInfoWithoutTokenTest() {
        ResponseSpecification spec = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
        given()
                .when()
                .get("https://api.imgur.com/3/account/{username}", username)
        ;
    }

    private static void getProperties(){
        try (InputStream output = new FileInputStream("src/test/resources/application.properties")) {
            properties.load(output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
