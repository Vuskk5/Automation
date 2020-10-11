package tests.testngpractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    {
        System.setProperty("webdriver.chrome.driver", "");
    }

    // Static variable definition
    static WebDriver driver;
    static WebDriverWait wait;
    static String LOG_IN_PATH = "http://dbankdemo.com/login";

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, 10);

        driver.navigate().to(LOG_IN_PATH);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void terminate() {
        driver.quit();
    }
}