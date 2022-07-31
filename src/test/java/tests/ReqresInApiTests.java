package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class ReqresInApiTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    @DisplayName("Get single user")
    public void checkSingleUser() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2));
    }


    @Test
    @DisplayName("Single user not found")
    public void checkSingleUserNegative() {
        given()
                .log().body()
                .when()
                .get("/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }


    @Test
    @DisplayName("Create new user")
    public void checkCreateJson() {
        String body = "{ \"name\": \"Elena\", \"job\": \"QA\" }";
        given()
                .log().body()
                .contentType(JSON)
                .body(body)
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", is("QA"));
    }


    @Test
    @DisplayName("Successful registration")
    public void checkSuccessfulRegistration() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"pistol\" }";
        given()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    @DisplayName("Unsuccessful registration")
    public void checkUnsuccessfulRegistration() {
        String body = "{ \"email\": \"sydney@fife\"}";
        given()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}