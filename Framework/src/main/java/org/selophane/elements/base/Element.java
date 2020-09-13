package org.selophane.elements.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ByChained;

/**
 * wraps a web element interface with extra functionality. Anything added here will be added to all descendants.
 */
@ImplementedBy()
public interface Element extends WebElement, WrapsElement, Locatable, WrapsDriver {
    /**
     * Returns true when the inner element is ready to be used.
     *
     * @return boolean true for an initialized WebElement, or false if we were somehow passed a null WebElement.
     */
    boolean elementWired();

    /**
     * Returns all locators that were chained for the element.
     * @return ByChained object populated with the locators of this element/
     */
    ByChained locators();

    /**
     * Returns the last locator for this element
     * @return By locator
     */
    By locator();

    /**
     * Relocates the nested WebElement
     */
    void relocate();
}
