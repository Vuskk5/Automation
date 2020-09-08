import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {
    public static ExpectedCondition<WebElement> elementToBeAvailable(WebElement element) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    element.isEnabled();
                    return element;
                }
                catch (NoSuchWindowException ex) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return " element to available in window";
            }
        };
    }

    public static ExpectedCondition<Boolean> alertClose() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.switchTo().alert().accept();
                    return true;
                }
                catch (NoAlertPresentException ex) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return " alert to close.";
            }
        };
    }

    public static ExpectedCondition<WebElement> elementToBeAvailableLocated(By locator) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    return driver.findElement(locator);
                }
                catch (NoSuchWindowException | UnhandledAlertException ex) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return " element to available in window";
            }
        };
    }
}
