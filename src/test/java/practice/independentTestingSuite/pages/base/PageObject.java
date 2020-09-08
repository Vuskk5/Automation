package practice.independentTestingSuite.pages.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.factory.api.ElementFactory;

public class PageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 5;

    protected PageObject(WebDriver driver) {
        this(driver, new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS));
    }

    protected PageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;

        ElementFactory.initElements(driver, this);
    }

    protected WebElement moveToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({\n" +
                "            behavior: 'auto',\n" +
                "            block: 'center',\n" +
                "            inline: 'center'\n" +
                "        });", element);

        return element;
    }
}
