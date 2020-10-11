package practice.api;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APITest {
    private static final String HOST = "http://localhost:9000";
    @Test
    public void test() {
        JsonPath path = JsonPath.from("{\"password\":\"Bb123\", \"userName\":\"Admin\"}");

        given().
            port(9000).
            header("Content-Type", "application/json").
            body(path).
        when().
            get("/getInterceptors").
        then().
            assertThat().
            statusCode(200);
    }
}
