package asisimAdvanced.tests.base;

import asisimAdvanced.managers.Authenticator;
import asisimAdvanced.pages.login.LoginPage;
import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public abstract class BaseTest {
    private static final String CONNECTION = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = "9000";
    private static final String URL = CONNECTION + HOST + ":" + PORT;
    private WebDriver driver;

    protected void initialize(ITestContext context) {
        driver = DriverManager.startChrome();

        authenticate("developer", "developer");

        driver().manage().window().maximize();
    }

    protected void terminate() {
        if (driver() != null) {
            driver().quit();
            driver = null;
        }
    }

    protected void authenticate(String username, String password) {
        LoginPage login = openSystem();

        await("").atMost(5, SECONDS)
                 .until(login::isAt);

        io.restassured.http.Cookies cookies = Authenticator.authenticate(username, password);

        cookies.forEach(cookie -> {
                Cookie newCookie = new Cookie(cookie.getName(),
                                            cookie.getValue(),
                                            HOST,
                                            cookie.getPath(),
                                            cookie.getExpiryDate(),
                                            cookie.isSecured(),
                                            cookie.isHttpOnly());
                driver.manage().addCookie(newCookie);
            }
        );

        driver.navigate().to(URL);
    }

    protected LoginPage openSystem() {
        driver.navigate().to(URL);
        return new LoginPage(driver());
    }

    protected WebDriver driver() {
        return driver;
    }
}
