package asisimAdvanced.managers;

import asisimAdvanced.models.User;
import io.restassured.http.Cookies;

import static io.restassured.RestAssured.given;

public class Authenticator {
    public static Cookies authenticate(String username, String password) {
        User user = new User(username, password);

        return
            given()
                .header("Content-Type", "application/json")
                .body(user.asJsonCredentials())
            .post("http://localhost:9000/login")
                .getDetailedCookies();
    }
}
