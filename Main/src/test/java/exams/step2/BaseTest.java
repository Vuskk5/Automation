package exams.step2;

import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    static {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeTest
    public void initialize() {
        driver = DriverManager.startChrome();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void openSystem() {
        SoftAssert softAssert = new SoftAssert();

        final String PATH = "http://localhost:9000/";

        driver.navigate().to(PATH);
        driver.manage().window().maximize();

        softAssert.assertTrue(driver.findElement(By.name("username")).isEnabled(), "Username field is disabled.");
        softAssert.assertTrue(driver.findElement(By.name("password")).isEnabled(), "Password field is disabled.");
        softAssert.assertTrue(driver.findElement(By.tagName("button")).isEnabled(), "Login button is disabled.");

        softAssert.assertAll();
    }

    @AfterTest
    public void terminate() {
        driver.quit();
    }
}
