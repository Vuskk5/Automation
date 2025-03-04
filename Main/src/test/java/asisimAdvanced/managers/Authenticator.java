package asisimAdvanced.managers;

import asisimAdvanced.models.User;
import io.restassured.http.Cookie;

import static io.restassured.RestAssured.given;

public class Authenticator {
    public static Cookie authenticate(String username, String password) {
        User user = new User(username, password);

        return  given()
                    .port(8080)
                    .contentType("application/json")
                    .body(user.asJsonCredentials())
                .post("/login")
                    .getDetailedCookie("JSESSIONID");
    }
}
