package practice.independentTestingSuite.tests.base;

import practice.independentTestingSuite.pages.LandingPage;
import support.drivermanager.DriverManager;
import asisimAdvanced.support.listeners.WebDriverListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

public abstract class BaseTest {
    static {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/resources/chromedriver.exe");
    }

    private static final String URL = "https://phptravels.com/";

    private WebDriver driver;
    private WebDriverWait wait;

    protected void initialize(ITestContext context) {
        DriverManager.startChrome();
        DriverManager.attachListener(new WebDriverListener());
        context.setAttribute("driver", DriverManager.getDriver());
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver(), 10);
        driver().manage().window().maximize();
    }

    protected void terminate() {
        if (driver() != null) {
            driver().quit();
        }
    }

    public LandingPage openSystem() {
        driver().navigate().to(URL);
        return new LandingPage(driver());
    }

    protected WebDriver driver() {
        return driver;
    }

    protected WebDriverWait driverWait() {
        return wait;
    }
}
