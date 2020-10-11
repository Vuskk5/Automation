package support.drivermanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class DriverManager {
    static {
        WebDriverManager.config().setProxy("10.0.0.10:80");
    }

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver attachListener(WebDriverEventListener eventListener) {
        driver.set(new EventFiringWebDriver(getDriver()));
        ((EventFiringWebDriver) getDriver()).register(eventListener);
        return getDriver();
    }

    public static WebDriver start(DriverType driverType) {
        switch (driverType) {
            case EDGE:
                break;
            case OPERA:
                break;
            case CHROME:
                return startChrome();
            case SAFARI:
                break;
            case FIREFOX:
                break;
            case INTERNET_EXPLORER:
                return startIExplorer();
        }

        return null;
    }

    public static WebDriver startChrome() {
        WebDriverManager.chromedriver().version("84");
        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver());
        return getDriver();
    }

    public static WebDriver startIExplorer() {
        WebDriverManager.iedriver().setup();
        driver.set(new InternetExplorerDriver());
        return getDriver();
    }

    public static ThreadLocal<WebDriver> getThreadedDriver() {
        return driver;
    }

    public static WebDriver getDriver() {
        return DriverManager.driver.get();
    }
}
