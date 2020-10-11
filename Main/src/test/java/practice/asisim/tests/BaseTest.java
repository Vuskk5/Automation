package practice.asisim.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import practice.asisim.pages.permission.LoginPage;
import support.drivermanager.DriverManager;

public class BaseTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void initialize() {
        driver = DriverManager.startChrome();
        wait = new WebDriverWait(driver, 10);
    }

    public void openSystem() {
        final String PATH = "http://localhost:9000/";

        driver.navigate().to(PATH);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void terminate() {
        driver.quit();
    }

    protected void loginAs(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginAs(username, password);
    }

    protected WebDriver getDriver() {
        return this.driver;
    }

    protected WebDriverWait getWait() {
        return this.wait;
    }
}
