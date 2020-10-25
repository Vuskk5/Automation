package net.bsmch.components;

import net.bsmch.PageObject;
import net.bsmch.elementwait.WebElementWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

import java.util.List;

/**
 * A base class representing a component in a page.
 *
 * @author Kfir Doron
 */
public abstract class PageComponent extends PageObject {
    /**
     * An element to be used as a {@code SearchContext}.
     * <br>This is the top element in the component's hierarchy.
     */
    private Element context;
    private WebElementWait contextWait;

    /**
     * Empty constructor to be used via Reflection.
     */
    protected PageComponent() {
        super();
    }

    protected PageComponent(WebElement element) {
        super();
        this.context = new ElementImpl(element);
    }

    public void setContext(Element context) {
        this.context = context;
    }

    public Element getContext() {
        return context;
    }

    @Override
    public boolean isAt() {
        return context != null;
    }

    public WebElementWait compWait() {
        if (contextWait == null) {
            contextWait = new WebElementWait(getContext(), DEFAULT_TIMEOUT_IN_SECONDS);
        }
        return contextWait;
    }

    public Element $(By locator) {
        return find(context, locator);
    }

    public List<Element> $$(By locator) {
        return findAll(context, locator);
    }
}
