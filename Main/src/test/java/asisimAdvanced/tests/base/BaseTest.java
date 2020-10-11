package asisimAdvanced.tests.base;

import asisimAdvanced.managers.Authenticator;
import asisimAdvanced.pages.login.LoginPage;
import net.bsmch.drivermanager.DriverManager;
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

    /**
     * Creates a new browsing session.
     * @param context The TestNG @Test context
     */
    protected void initialize(ITestContext context) {
        driver = DriverManager.startChrome();

        authenticate("developer", "developer");
    }

    /**
     * Quits this driver, closing every associated window.
     */
    protected void terminate() {
        if (driver() != null) {
            driver().quit();
            driver = null;
        }
    }

    /**
     * Authenticates from the login page using GET /login request.
     * @param username The username of the auth user
     * @param password The password of the auth user
     */
    protected void authenticate(String username, String password) {
        openSystem();

        io.restassured.http.Cookie cookie = Authenticator.authenticate(username, password);

        driver.manage().addCookie(
                new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue())
        );

        driver.navigate().to(URL);
    }

    protected LoginPage openSystem() {
        driver.navigate().to(URL);
        driver.manage().window().maximize();

        LoginPage loginPage = new LoginPage(driver());

        await("").atMost(5, SECONDS)
                 .until(loginPage::isAt);

        return loginPage;
    }

    protected WebDriver driver() {
        return driver;
    }
}
