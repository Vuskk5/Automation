package net.bsmch.components;

import net.bsmch.support.ElementConverter;
import net.bsmch.support.Selectors;
import net.bsmch.elementwait.WebElementWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

import java.util.List;

/**
 * A base class representing a component in a page.
 *
 * @author Kfir Doron
 */
public abstract class PageComponent {
    /**
     * An element to be used as a {@code SearchContext}.
     * <br>This is the top element in the component's hierarchy.
     */
    private Element context;
    private WebDriver driver;
    private WebElementWait elementWait;
    private WebDriverWait driverWait;
    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 10;
    /**
     * Empty constructor to be used via Reflection.
     */
    protected PageComponent() {
        super();
    }

    protected PageComponent(WebElement element) {
        this.driver = (((WrapsDriver) element).getWrappedDriver());
        this.context = new ElementImpl(element);
    }

    public Element getContext() {
        return context;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = context.getWrappedDriver();
        }
        return driver;
    }

    public WebElementWait contextWait() {
        if (elementWait == null) {
            elementWait = new WebElementWait(getContext(), DEFAULT_TIMEOUT_IN_SECONDS);
        }
        return elementWait;
    }

    public WebDriverWait driverWait() {
        if (driverWait == null) {
            driverWait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT_IN_SECONDS);
        }
        return driverWait;
    }

    protected Element $(String xpathOrCssSelector) {
        if (Selectors.isXPath(xpathOrCssSelector)) {
            return $(By.xpath(xpathOrCssSelector));
        }
        else {
            return $(By.cssSelector(xpathOrCssSelector));
        }
    }

    protected Element $(By locator) {
        return ElementConverter.convert(getContext().findElement(locator));
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
        return ElementConverter.convertList(getContext().findElements(locator));
    }
}
