package HomeWork1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest{
String imageDeleteHash;
    @BeforeEach
    void setUp() {
        imageDeleteHash = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/img.png"))
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void deleteExistentImageTest() {
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{imageHash}", imageDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
