package net.bsmch;

import net.bsmch.components.api.ComponentFactory;
import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.factory.api.ElementFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A base class representing a page object.
 *
 * @author Kfir Doron
 */
public abstract class PageObject {
    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 10;

    private WebDriver driver;
    private WebDriverWait wait;

    public PageObject() {}

    public PageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS);

        ComponentFactory.initComponents(driver, this);
        ElementFactory.initElements(driver, this);
    }

    public WebDriver.Timeouts setImplicitTimeout(int duration, TimeUnit unit) {
        return driver.manage().timeouts().implicitlyWait(duration, unit);
    }

    /**
     * Finds a {@link WebElement} and wraps it as an {@link Element}
     * @param xpathOrCssSelector The selector to be used.
     * @return Html element wrapped as {@link Element}
     */
    protected Element $(String xpathOrCssSelector) {
        if (Selectors.isXPath(xpathOrCssSelector)) {
            return $(By.xpath(xpathOrCssSelector));
        }
        else {
            return $(By.cssSelector(xpathOrCssSelector));
        }
    }

    protected Element $(By locator) {
        return new ElementImpl(getDriver().findElement(locator));
    }

    /**
     * Finds a {@link List<WebElement>} and wraps it as a {@link List<Element>}
     * @param xpathOrCssSelector The selector to be used.
     * @return Html elements wrapped as {@link List<Element>}
     */
    protected List<Element> $$(String xpathOrCssSelector) {
        By locator = Selectors.isXPath(xpathOrCssSelector) ? By.xpath(xpathOrCssSelector)
                                                 : By.cssSelector(xpathOrCssSelector);
        return $$(locator);
    }

    protected List<Element> $$(By locator) {
        List<WebElement> seleniumElements = getDriver().findElements(locator);
        List<Element> convertedElements = new ArrayList<>();
        seleniumElements.forEach(element -> convertedElements.add(new ElementImpl(element)));

        return convertedElements;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait driverWait() {
        return wait;
    }

    public static <T extends PageObject> T page(Class<T> clazz) {
        try {
            Constructor<T> con = clazz.getConstructor(WebDriver.class);
            return con.newInstance(DriverManager.getDriver());
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
