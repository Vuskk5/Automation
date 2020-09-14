package net.bsmch;

import net.bsmch.components.api.ComponentFactory;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.exceptions.PageInstantiationException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.factory.api.ElementFactory;

import java.lang.reflect.Constructor;
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
     * Find the first {@link WebElement} using the given method.
     * This method is affected by the 'implicit wait' times in force at the time of execution.
     * The findElement(..) invocation will return a matching row, or try again repeatedly until
     * the configured timeout is reached.
     *
     * $ should not be used to look for non-present elements, use {@link #$$(By)}
     * and assert zero length response instead.
     *
     * @param xpathOrCssSelector XPath or CssSelector to used to find the element
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    protected Element $(String xpathOrCssSelector) {
        if (Selectors.isXPath(xpathOrCssSelector)) {
            return $(By.xpath(xpathOrCssSelector));
        }
        else {
            return $(By.cssSelector(xpathOrCssSelector));
        }
    }

    /**
     * Find the first {@link WebElement} using the given method.
     * This method is affected by the 'implicit wait' times in force at the time of execution.
     * The findElement(..) invocation will return a matching row, or try again repeatedly until
     * the configured timeout is reached.
     *
     * $ should not be used to look for non-present elements, use {@link #$$(By)}
     * and assert zero length response instead.
     *
     * @param locator The locating mechanism
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    protected Element $(By locator) {
        return new ElementImpl(getDriver().findElement(locator));
    }

    /**
     * Find all elements within the current page using the given mechanism.
     * This method is affected by the 'implicit wait' times in force at the time of execution. When
     * implicitly waiting, this method will return as soon as there are more than 0 items in the
     * found collection, or will return an empty list if the timeout is reached.
     *
     * @param xpathOrCssSelector XPath or CssSelector to used to find the element
     * @return A list of all {@link WebElement}s, or an empty list if nothing matches
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    protected List<Element> $$(String xpathOrCssSelector) {
        By locator = Selectors.isXPath(xpathOrCssSelector) ? By.xpath(xpathOrCssSelector)
                                                 : By.cssSelector(xpathOrCssSelector);
        return $$(locator);
    }

    /**
     * Find all elements within the current page using the given mechanism.
     * This method is affected by the 'implicit wait' times in force at the time of execution. When
     * implicitly waiting, this method will return as soon as there are more than 0 items in the
     * found collection, or will return an empty list if the timeout is reached.
     *
     * @param locator The locating mechanism to use
     * @return A list of all {@link WebElement}s, or an empty list if nothing matches
     * @see org.openqa.selenium.By
     * @see org.openqa.selenium.WebDriver.Timeouts
     */
    protected List<Element> $$(By locator) {
        List<WebElement> seleniumElements = getDriver().findElements(locator);
        List<Element> convertedElements = new ArrayList<>();
        seleniumElements.forEach(element -> convertedElements.add(new ElementImpl(element)));

        return convertedElements;
    }

    /**
     * Returns true if the given conditions for the page "to be loaded" are met.
     * @return {@code true} or {@code false} based of the page status.
     */
    public abstract boolean isAt();

    /**
     * Creates a new page of the given class via reflection.
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
