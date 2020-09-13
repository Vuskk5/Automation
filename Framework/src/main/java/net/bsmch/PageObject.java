package net.bsmch;

import net.bsmch.components.api.ComponentFactory;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.exceptions.PageInstantiationException;
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

import javax.annotation.Nonnull;
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

    /**
     * Returns true if the given condition for the page "to be loaded" are met.
     * @return {@code true} or {@code false} based of the page status.
     */
    public abstract boolean isAt();

    /**
     * Creates a new page of the given class, with the given class name via reflection.
     * @param page The class to instantiate.
     * @return The instantiated page.
     * @exception PageInstantiationException when the given page could not be instantiated.
     * <br>Check the nested exception for the following:
     * <ul>
     *     <li>{@link NoSuchMethodException} when there is no constructor with {@link WebDriver} parameter</li>
     *     <li>{@link IllegalAccessException} when the constructor is not defined as public</li>
     *     <li>{@link InstantiationException} when the class cannot be instantiated. Either be abstract class, interface, primitive type, or an array class</li>
     *     <li>{@link InvocationTargetException} when an internal exception was thrown in the constructor</li>
     * </ul>
     */
    public static <T extends PageObject> T page(Class<T> page) {
        try {
            Constructor<T> constructor = page.getConstructor(WebDriver.class);
            return constructor.newInstance(DriverManager.getDriver());
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new PageInstantiationException(page, ex);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait driverWait() {
        return wait;
    }
}
