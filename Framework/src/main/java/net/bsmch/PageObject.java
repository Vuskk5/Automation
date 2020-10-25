package net.bsmch;

import net.bsmch.components.api.ComponentFactory;
import net.bsmch.drivermanager.DriverManager;
import net.bsmch.exceptions.ElementInstantiationException;
import net.bsmch.exceptions.PageInstantiationException;
import net.bsmch.support.ElementFinder;
import net.bsmch.support.Selectors;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.factory.api.ElementFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * A base class representing a page object.
 *
 * @author Kfir Doron
 */
public abstract class PageObject {
    protected static final int DEFAULT_TIMEOUT_IN_SECONDS = 10;

    protected WebDriver driver;
    protected WebDriverWait pageWait;

    public PageObject() {
        this.driver = DriverManager.getDriver();
        this.pageWait = new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS);

        ComponentFactory.initComponents(driver, this);
        ElementFactory.initElements(driver, this);
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
        return Selectors.isXPath(xpathOrCssSelector) ? $(By.xpath(xpathOrCssSelector))
                                                     : $(By.cssSelector(xpathOrCssSelector));
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
        return find(DriverManager.getDriver(), locator);
    }

    protected static Element find(SearchContext context, By locator) {
        return ElementFinder.proxy(context, locator);
    }

    /**
     * Finds a {@link List <WebElement>} and wraps it as a {@link List<Element>}
     * @param xpathOrCssSelector The selector to be used.
     * @return Html elements wrapped as {@link List<Element>}
     */
    protected List<Element> $$(String xpathOrCssSelector) {
        By locator = Selectors.isXPath(xpathOrCssSelector) ? By.xpath(xpathOrCssSelector)
                                                           : By.cssSelector(xpathOrCssSelector);
        return $$(locator);
    }

    protected List<Element> $$(By locator) {
        return findAll(DriverManager.getDriver(), locator);
    }

    protected static List<Element> findAll(SearchContext context, By locator) {
        return ElementFinder.proxyAll(context, locator);
    }

    /**
     * Returns true if the given conditions for the page "to be loaded" are met.
     * @return {@code true} or {@code false} based of the page status.
     */
    protected abstract boolean isAt();

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
            Constructor<T> constructor = page.getConstructor();
            return constructor.newInstance();
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new PageInstantiationException(page, ex);
        }
    }

    private static <T extends Element> T element(WebElement element, Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor(WebElement.class);
            return constructor.newInstance(element);
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new ElementInstantiationException(type, ex);
        }
    }

    protected WebDriver driver() {
        return driver;
    }

    protected WebDriverWait Wait() {
        return pageWait;
    }
}
