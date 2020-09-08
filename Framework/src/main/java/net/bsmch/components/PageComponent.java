package net.bsmch.components;

import net.bsmch.ElementConverter;
import net.bsmch.PageObject;
import net.bsmch.Selectors;
import net.bsmch.elementwait.WebElementWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

import java.util.ArrayList;
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
    private WebElementWait wait;

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

    public WebElementWait getWait() {
        if (wait == null) {
            wait = new WebElementWait(context, 10);
        }
        return wait;
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
