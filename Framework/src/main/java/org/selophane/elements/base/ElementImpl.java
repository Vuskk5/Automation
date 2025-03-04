package org.selophane.elements.base;

import net.bsmch.support.ByBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * An implementation of the Element interface. Delegates its work to an underlying WebElement instance for
 * custom functionality.
 */
public class ElementImpl implements Element {

    private WebElement element;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public ElementImpl(final WebElement element) {
        this.element = element;
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        element.sendKeys(keysToSend);
    }

    @Override
    public Point getLocation() {
        return element.getLocation();
    }

    @Override
    public void submit() {
        element.submit();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    @Override
    public Dimension getSize() {
        return element.getSize();
    }

    @Override
    public Rectangle getRect() {
        throw new UnsupportedOperationException("getRect() not yet implemented");
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) element).getCoordinates();
    }

    @Override
    public boolean elementWired() {
        return (element != null);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        throw new UnsupportedOperationException("getScreenshotAs() not yet implemented");
    }

    @Override
    public WebDriver getWrappedDriver() {
        return ((WrapsDriver) getWrappedElement()).getWrappedDriver();
    }

    @Override
    public ByChained locators() {
        return ByBuilder.fullLocator(element);
    }

    @Override
    public By locator() {
        return ByBuilder.locator(element);
    }

    @Override
    public void relocate() {
        this.element = getWrappedDriver().findElement(this.locators());
    }

    @Override
    public <T extends Element> T as(Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor(WebElement.class);
            return constructor.newInstance(getWrappedElement());
        }
        catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
            throw new RuntimeException("Could not instantiate element", ex);
        }
    }

    @Override
    public String toString() {
        return getWrappedElement().toString();
    }
}
