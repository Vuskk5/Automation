package asisimAdvanced.tests.base;

import asisimAdvanced.managers.Authenticator;
import asisimAdvanced.pages.general.MainPage;
import asisimAdvanced.pages.login.LoginPage;
import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import static java.util.concurrent.TimeUnit.SECONDS;
import static net.bsmch.PageObject.*;
import static org.awaitility.Awaitility.await;

public abstract class BaseTest {
    public static final String CONNECTION = "http://";
    public static final String HOST = "localhost";
    public static final int PORT = 8080;
    public static final String URL = CONNECTION + HOST + ":" + PORT;

    private WebDriver driver;

    /**
     * Creates a new browsing session.
     * @param context The TestNG @Test context
     */
    protected void initialize(ITestContext context) {
        driver = DriverManager.startChrome();
    }

    /**
     * Quits this driver, closing every associated window.
     */
    protected void terminate() {
        DriverManager.quit();
    }

    /**
     * Authenticates from the login page using GET /login request.
     * @param username The username of the auth user
     * @param password The password of the auth user
     */
    protected Cookie authenticate(String username, String password) {
        open();

        io.restassured.http.Cookie cookie = Authenticator.authenticate(username, password);

        org.openqa.selenium.Cookie seleniumCookie =
                new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue());

        driver.manage().addCookie(seleniumCookie);

        driver.navigate().to(URL);

        return seleniumCookie;
    }

    protected LoginPage open() {
        driver.navigate().to(URL);
        driver.manage().window().maximize();

        LoginPage loginPage = page(LoginPage.class);

        await().atMost(5, SECONDS)
               .until(loginPage::isAt);

        return loginPage;
    }

    protected MainPage loginAs(String username, String password) {
        LoginPage loginPage = page(LoginPage.class);

        loginPage.loginAs(username, password);

        return page(MainPage.class);
    }

    protected WebDriver driver() {
        return driver;
    }
}
