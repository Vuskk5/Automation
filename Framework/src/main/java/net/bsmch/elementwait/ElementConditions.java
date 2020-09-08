package net.bsmch.elementwait;

import com.google.common.base.Joiner;
import org.openqa.selenium.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Canned {@link ElementCondition}s which are generally useful within websearchContext tests.
 */
public class ElementConditions {

    private final static Logger log = Logger.getLogger(ElementConditions.class.getName());

    private ElementConditions() {
        // Utility class
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page. This does not
     * necessarily mean that the element is visible.
     *
     * @param locator used to find the element
     * @return the WebElement once it is located
     */
    public static ElementCondition<WebElement> presenceOfElementLocated(final By locator) {
        return new ElementCondition<WebElement>() {
            @Override
            public WebElement apply(SearchContext searchContext) {
                return searchContext.findElement(locator);
            }

            @Override
            public String toString() {
                return "presence of element located by: " + locator;
            }
        };
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is
     * greater than 0.
     *
     * @param locator used to find the element
     * @return the WebElement once it is located and visible
     */
    public static ElementCondition<WebElement> visibilityOfElementLocated(final By locator) {
        return new ElementCondition<WebElement>() {
            @Override
            public WebElement apply(SearchContext searchContext) {
                try {
                    return elementIfVisible(searchContext.findElement(locator));
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    /**
     * An expectation for checking that all elements present on the web page that match the locator
     * are visible. Visibility means that the elements are not only displayed but also have a height
     * and width that is greater than 0.
     *
     * @param locator used to find the element
     * @return the list of WebElements once they are located
     */
    public static ElementCondition<List<WebElement>> visibilityOfAllElementsLocatedBy(
            final By locator) {
        return new ElementCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> elements = searchContext.findElements(locator);
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        return null;
                    }
                }
                return elements.size() > 0 ? elements : null;
            }

            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }

    /**
     * An expectation for checking that all elements present on the web page that match the locator
     * are visible. Visibility means that the elements are not only displayed but also have a height
     * and width that is greater than 0.
     *
     * @param elements list of WebElements
     * @return the list of WebElements once they are located
     */
    public static ElementCondition<List<WebElement>> visibilityOfAllElements(
            final WebElement... elements) {
        return visibilityOfAllElements(Arrays.asList(elements));
    }

    /**
     * An expectation for checking that all elements present on the web page that match the locator
     * are visible. Visibility means that the elements are not only displayed but also have a height
     * and width that is greater than 0.
     *
     * @param elements list of WebElements
     * @return the list of WebElements once they are located
     */
    public static ElementCondition<List<WebElement>> visibilityOfAllElements(
            final List<WebElement> elements) {
        return new ElementCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        return null;
                    }
                }
                return elements.size() > 0 ? elements : null;
            }

            @Override
            public String toString() {
                return "visibility of all " + elements;
            }
        };
    }

    /**
     * An expectation for checking that an element, known to be present on the DOM of a page, is
     * visible. Visibility means that the element is not only displayed but also has a height and
     * width that is greater than 0.
     *
     * @param element the WebElement
     * @return the (same) WebElement once it is visible
     */
    public static ElementCondition<WebElement> visibilityOf(final WebElement element) {
        return new ElementCondition<WebElement>() {
            @Override
            public WebElement apply(SearchContext searchContext) {
                return elementIfVisible(element);
            }

            @Override
            public String toString() {
                return "visibility of " + element;
            }
        };
    }

    /**
     * @return the given element if it is visible and has non-zero size, otherwise null.
     */
    private static WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }

    /**
     * An expectation for checking that there is at least one element present on a web page.
     *
     * @param locator used to find the element
     * @return the list of WebElements once they are located
     */
    public static ElementCondition<List<WebElement>> presenceOfAllElementsLocatedBy(
            final By locator) {
        return new ElementCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> elements = searchContext.findElements(locator);
                return elements.size() > 0 ? elements : null;
            }

            @Override
            public String toString() {
                return "presence of any elements located by " + locator;
            }
        };
    }

    /**
     * An expectation for checking if the given text is present in the specified element.
     *
     * @param element the WebElement
     * @param text    to be present in the element
     * @return true once the element contains the given text
     */
    public static ElementCondition<Boolean> textToBePresentInElement(final WebElement element,
                                                                     final String text) {

        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    String elementText = element.getText();
                    return elementText.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element %s", text, element);
            }
        };
    }

    /**
     * An expectation for checking if the given text is present in the element that matches the given
     * locator.
     *
     * @param locator used to find the element
     * @param text    to be present in the element found by the locator
     * @return true once the first element located by locator contains the given text
     */
    public static ElementCondition<Boolean> textToBePresentInElementLocated(final By locator,
                                                                            final String text) {

        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    String elementText = searchContext.findElement(locator).getText();
                    return elementText.contains(text);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be present in element found by %s",
                        text, locator);
            }
        };
    }

    /**
     * An expectation for checking if the given text is present in the specified elements value
     * attribute.
     *
     * @param element the WebElement
     * @param text    to be present in the element's value attribute
     * @return true once the element's value attribute contains the given text
     */
    public static ElementCondition<Boolean> textToBePresentInElementValue(final WebElement element,
                                                                          final String text) {

        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    String elementText = element.getAttribute("value");
                    if (elementText != null) {
                        return elementText.contains(text);
                    }
                    return false;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be the value of element %s", text, element);
            }
        };
    }

    /**
     * An expectation for checking if the given text is present in the specified elements value
     * attribute.
     *
     * @param locator used to find the element
     * @param text    to be present in the value attribute of the element found by the locator
     * @return true once the value attribute of the first element located by locator contains the
     * given text
     */
    public static ElementCondition<Boolean> textToBePresentInElementValue(final By locator,
                                                                          final String text) {

        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    String elementText = searchContext.findElement(locator).getAttribute("value");
                    if (elementText != null) {
                        return elementText.contains(text);
                    }
                    return false;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("text ('%s') to be the value of element located by %s",
                        text, locator);
            }
        };
    }

    /**
     * An expectation for checking that an element is either invisible or not present on the DOM.
     *
     * @param locator used to find the element
     * @return true if the element is not displayed or the element doesn't exist or stale element
     */
    public static ElementCondition<Boolean> invisibilityOfElementLocated(final By locator) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    return !(searchContext.findElement(locator).isDisplayed());
                } catch (NoSuchElementException e) {
                    // Returns true because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }

            @Override
            public String toString() {
                return "element to no longer be visible: " + locator;
            }
        };
    }

    /**
     * An expectation for checking that an element with text is either invisible or not present on the
     * DOM.
     *
     * @param locator used to find the element
     * @param text    of the element
     * @return true if no such element, stale element or displayed text not equal that provided
     */
    public static ElementCondition<Boolean> invisibilityOfElementWithText(final By locator,
                                                                          final String text) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    return !searchContext.findElement(locator).getText().equals(text);
                } catch (NoSuchElementException e) {
                    // Returns true because the element with text is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }


            @Override
            public String toString() {
                return String.format("element containing '%s' to no longer be visible: %s",
                        text, locator);
            }
        };
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can click it.
     *
     * @param locator used to find the element
     * @return the WebElement once it is located and clickable (visible and enabled)
     */
    public static ElementCondition<WebElement> elementToBeClickable(final By locator) {
        return new ElementCondition<WebElement>() {
            @Override
            public WebElement apply(SearchContext searchContext) {
                WebElement element = visibilityOfElementLocated(locator).apply(searchContext);
                try {
                    if (element != null && element.isEnabled()) {
                        return element;
                    }
                    return null;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be clickable: " + locator;
            }
        };
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can click it.
     *
     * @param element the WebElement
     * @return the (same) WebElement once it is clickable (visible and enabled)
     */
    public static ElementCondition<WebElement> elementToBeClickable(final WebElement element) {
        return new ElementCondition<WebElement>() {

            @Override
            public WebElement apply(SearchContext searchContext) {
                WebElement visibleElement = visibilityOf(element).apply(searchContext);
                try {
                    if (visibleElement != null && visibleElement.isEnabled()) {
                        return visibleElement;
                    }
                    return null;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be clickable: " + element;
            }
        };
    }

    /**
     * Wait until an element is no longer attached to the DOM.
     *
     * @param element The element to wait for.
     * @return false if the element is still attached to the DOM, true otherwise.
     */
    public static ElementCondition<Boolean> stalenessOf(final WebElement element) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext ignored) {
                try {
                    // Calling any method forces a staleness check
                    element.isEnabled();
                    return false;
                } catch (StaleElementReferenceException expected) {
                    return true;
                }
            }

            @Override
            public String toString() {
                return String.format("element (%s) to become stale", element);
            }
        };
    }

    /**
     * Wrapper for a condition, which allows for elements to update by redrawing.
     * <p>
     * This works around the problem of conditions which have two parts: find an element and then
     * check for some condition on it. For these conditions it is possible that an element is located
     * and then subsequently it is redrawn on the client. When this happens a {@link
     * StaleElementReferenceException} is thrown when the second part of the condition is checked.
     *
     * @param condition ElementCondition to wrap
     * @param <T>       return type of the condition provided
     * @return the result of the provided condition
     */
    public static <T> ElementCondition<T> refreshed(final ElementCondition<T> condition) {
        return new ElementCondition<T>() {
            @Override
            public T apply(SearchContext searchContext) {
                try {
                    return condition.apply(searchContext);
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("condition (%s) to be refreshed", condition);
            }
        };
    }

    /**
     * An expectation for checking if the given element is selected.
     *
     * @param element WebElement to be selected
     * @return true once the element is selected
     */
    public static ElementCondition<Boolean> elementToBeSelected(final WebElement element) {
        return elementSelectionStateToBe(element, true);
    }

    /**
     * An expectation for checking if the given element is selected.
     *
     * @param element  WebElement to be selected
     * @param selected boolean state of the selection state of the element
     * @return true once the element's selection stated is that of selected
     */
    public static ElementCondition<Boolean> elementSelectionStateToBe(final WebElement element,
                                                                      final boolean selected) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                return element.isSelected() == selected;
            }

            @Override
            public String toString() {
                return String.format("element (%s) to %sbe selected", element, (selected ? "" : "not "));
            }
        };
    }

    public static ElementCondition<Boolean> elementToBeSelected(final By locator) {
        return elementSelectionStateToBe(locator, true);
    }

    public static ElementCondition<Boolean> elementSelectionStateToBe(final By locator,
                                                                      final boolean selected) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    WebElement element = searchContext.findElement(locator);
                    return element.isSelected() == selected;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("element found by %s to %sbe selected",
                        locator, (selected ? "" : "not "));
            }
        };
    }

    /**
     * An expectation with the logical opposite condition of the given condition.
     * <p>
     * Note that if the Condition you are inverting throws an exception that is caught by the Ignored
     * Exceptions, the inversion will not take place and lead to confusing results.
     *
     * @param condition ElementCondition to be inverted
     * @return true once the condition is satisfied
     */
    public static ElementCondition<Boolean> not(final ElementCondition<?> condition) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                Object result = condition.apply(searchContext);
                return result == null || result.equals(Boolean.FALSE);
            }

            @Override
            public String toString() {
                return "condition to not be valid: " + condition;
            }
        };
    }


    /**
     * An expectation for checking WebElement with given locator has attribute with a specific value
     *
     * @param locator   used to find the element
     * @param attribute used to define css or html attribute
     * @param value     used as expected attribute value
     * @return Boolean true when element has css or html attribute with the value
     */
    public static ElementCondition<Boolean> attributeToBe(final By locator, final String attribute,
                                                          final String value) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                WebElement element = searchContext.findElement(locator);
                currentValue = element.getAttribute(attribute);
                if (currentValue == null || currentValue.isEmpty()) {
                    currentValue = element.getCssValue(attribute);
                }
                return value.equals(currentValue);
            }

            @Override
            public String toString() {
                return String.format("element found by %s to have value \"%s\". Current value: \"%s\"",
                        locator, value, currentValue);
            }
        };
    }

    /**
     * An expectation for checking WebElement with given locator has specific text
     *
     * @param locator used to find the element
     * @param value   used as expected text
     * @return Boolean true when element has text value equal to @value
     */
    public static ElementCondition<Boolean> textToBe(final By locator, final String value) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    currentValue = searchContext.findElement(locator).getText();
                    return currentValue.equals(value);
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("element found by %s to have text \"%s\". Current text: \"%s\"",
                        locator, value, currentValue);
            }
        };
    }

    /**
     * An expectation for checking WebElement with given locator has text with a value as a part of
     * it
     *
     * @param locator used to find the element
     * @param pattern used as expected text matcher pattern
     * @return Boolean true when element has text value containing @value
     */
    public static ElementCondition<Boolean> textMatches(final By locator, final Pattern pattern) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                try {
                    currentValue = searchContext.findElement(locator).getText();
                    return pattern.matcher(currentValue).find();
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String
                        .format("text found by %s to match pattern \"%s\". Current text: \"%s\"",
                                locator, pattern.pattern(), currentValue);
            }
        };
    }

    /**
     * An expectation for checking number of WebElements with given locator being more than defined number
     *
     * @param locator used to find the element
     * @param number  used to define minimum number of elements
     * @return Boolean true when size of elements list is more than defined
     */
    public static ElementCondition<List<WebElement>> numberOfElementsToBeMoreThan(final By locator,
                                                                                  final Integer number) {
        return new ElementCondition<List<WebElement>>() {
            private Integer currentNumber = 0;

            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> elements = searchContext.findElements(locator);
                currentNumber = elements.size();
                return currentNumber > number ? elements : null;
            }

            @Override
            public String toString() {
                return String.format("number of elements found by %s to be more than \"%s\". Current number: \"%s\"",
                        locator, number, currentNumber);
            }
        };
    }

    /**
     * An expectation for checking number of WebElements with given locator being less than defined
     * number
     *
     * @param locator used to find the element
     * @param number  used to define maximum number of elements
     * @return Boolean true when size of elements list is less than defined
     */
    public static ElementCondition<List<WebElement>> numberOfElementsToBeLessThan(final By locator,
                                                                                  final Integer number) {
        return new ElementCondition<List<WebElement>>() {
            private Integer currentNumber = 0;

            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> elements = searchContext.findElements(locator);
                currentNumber = elements.size();
                return currentNumber < number ? elements : null;
            }

            @Override
            public String toString() {
                return String.format("number of elements found by %s to be less than \"%s\". Current number: \"%s\"",
                        locator, number, currentNumber);
            }
        };
    }

    /**
     * An expectation for checking number of WebElements with given locator
     *
     * @param locator used to find the element
     * @param number  used to define number of elements
     * @return Boolean true when size of elements list is equal to defined
     */
    public static ElementCondition<List<WebElement>> numberOfElementsToBe(final By locator,
                                                                          final Integer number) {
        return new ElementCondition<List<WebElement>>() {
            private Integer currentNumber = 0;

            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> elements = searchContext.findElements(locator);
                currentNumber = elements.size();
                return currentNumber.equals(number) ? elements : null;
            }

            @Override
            public String toString() {
                return String
                        .format("number of elements found by %s to be \"%s\". Current number: \"%s\"",
                                locator, number, currentNumber);
            }
        };
    }

    /**
     * An expectation for checking given WebElement has attribute with a specific value
     *
     * @param element   used to check its parameters
     * @param attribute used to define css or html attribute
     * @param value     used as expected attribute value
     * @return Boolean true when element has css or html attribute with the value
     */
    public static ElementCondition<Boolean> attributeToBe(final WebElement element,
                                                          final String attribute,
                                                          final String value) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                currentValue = element.getAttribute(attribute);
                if (currentValue == null || currentValue.isEmpty()) {
                    currentValue = element.getCssValue(attribute);
                }
                return value.equals(currentValue);
            }

            @Override
            public String toString() {
                return String.format(attribute + " to be \"%s\". Current " + attribute + ": \"%s\"", value,
                        currentValue);
            }
        };
    }

    /**
     * An expectation for checking WebElement with given locator has attribute which contains specific
     * value
     *
     * @param element   used to check its parameters
     * @param attribute used to define css or html attribute
     * @param value     used as expected attribute value
     * @return Boolean true when element has css or html attribute which contains the value
     */
    public static ElementCondition<Boolean> attributeContains(final WebElement element,
                                                              final String attribute,
                                                              final String value) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                return getAttributeOrCssValue(element, attribute)
                        .map(seen -> seen.contains(value))
                        .orElse(false);
            }

            @Override
            public String toString() {
                return String.format("value to contain \"%s\". Current value: \"%s\"", value, currentValue);
            }
        };
    }

    /**
     * An expectation for checking WebElement with given locator has attribute which contains specific
     * value
     *
     * @param locator   used to define WebElement to check its parameters
     * @param attribute used to define css or html attribute
     * @param value     used as expected attribute value
     * @return Boolean true when element has css or html attribute which contains the value
     */
    public static ElementCondition<Boolean> attributeContains(final By locator,
                                                              final String attribute,
                                                              final String value) {
        return new ElementCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(SearchContext searchContext) {
                return getAttributeOrCssValue(searchContext.findElement(locator), attribute)
                        .map(seen -> seen.contains(value))
                        .orElse(false);
            }

            @Override
            public String toString() {
                return String.format("value found by %s to contain \"%s\". Current value: \"%s\"",
                        locator, value, currentValue);
            }
        };
    }

    /**
     * An expectation for checking WebElement any non empty value for given attribute
     *
     * @param element   used to check its parameters
     * @param attribute used to define css or html attribute
     * @return Boolean true when element has css or html attribute with non empty value
     */
    public static ElementCondition<Boolean> attributeToBeNotEmpty(final WebElement element,
                                                                  final String attribute) {
        return searchContext -> getAttributeOrCssValue(element, attribute).isPresent();
    }

    private static Optional<String> getAttributeOrCssValue(WebElement element, String name) {
        String value = element.getAttribute(name);
        if (value == null || value.isEmpty()) {
            value = element.getCssValue(name);
        }

        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    /**
     * An expectation for checking child WebElement as a part of parent element to be visible
     *
     * @param parent       used to check parent element. For example table with locator
     *                     By.id("fish")
     * @param childLocator used to find the ultimate child element.
     * @return visible nested element
     */
    public static ElementCondition<List<WebElement>> visibilityOfNestedElementsLocatedBy(
            final By parent,
            final By childLocator) {
        return new ElementCondition<List<WebElement>>() {

            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                WebElement current = searchContext.findElement(parent);

                List<WebElement> allChildren = current.findElements(childLocator);
                // The original code only checked the first element. Fair enough.
                if (!allChildren.isEmpty() && allChildren.get(0).isDisplayed()) {
                    return allChildren;
                }

                return null;
            }

            @Override
            public String toString() {
                return String.format("visibility of elements located by %s -> %s", parent, childLocator);
            }
        };
    }


    /**
     * An expectation for checking child WebElement as a part of parent element to be visible
     *
     * @param element      used as parent element. For example table with locator By.xpath("//table")
     * @param childLocator used to find child element. For example td By.xpath("./tr/td")
     * @return visible subelement
     */
    public static ElementCondition<List<WebElement>> visibilityOfNestedElementsLocatedBy(
            final WebElement element, final By childLocator) {
        return new ElementCondition<List<WebElement>>() {

            @Override
            public List<WebElement> apply(SearchContext ignored) {
                List<WebElement> allChildren = element.findElements(childLocator);
                // The original code only checked the visibility of the first element.
                if (!allChildren.isEmpty() && allChildren.get(0).isDisplayed()) {
                    return allChildren;
                }

                return null;
            }

            @Override
            public String toString() {
                return String.format("visibility of element located by %s -> %s", element, childLocator);
            }
        };
    }


    /**
     * An expectation for checking child WebElement as a part of parent element to present
     *
     * @param locator      used to check parent element. For example table with locator
     *                     By.xpath("//table")
     * @param childLocator used to find child element. For example td By.xpath("./tr/td")
     * @return subelement
     */
    public static ElementCondition<WebElement> presenceOfNestedElementLocatedBy(
            final By locator,
            final By childLocator) {
        return new ElementCondition<WebElement>() {

            @Override
            public WebElement apply(SearchContext searchContext) {
                return searchContext.findElement(locator).findElement(childLocator);
            }

            @Override
            public String toString() {
                return String.format("visibility of element located by %s -> %s", locator, childLocator);
            }
        };
    }

    /**
     * An expectation for checking child WebElement as a part of parent element to be present
     *
     * @param element      used as parent element
     * @param childLocator used to find child element. For example td By.xpath("./tr/td")
     * @return subelement
     */
    public static ElementCondition<WebElement> presenceOfNestedElementLocatedBy(
            final WebElement element,
            final By childLocator) {

        return new ElementCondition<WebElement>() {

            @Override
            public WebElement apply(SearchContext ignored) {
                return element.findElement(childLocator);
            }

            @Override
            public String toString() {
                return String.format("visibility of element located by %s", childLocator);
            }
        };
    }

    /**
     * An expectation for checking child WebElement as a part of parent element to present
     *
     * @param parent       used to check parent element. For example table with locator
     *                     By.xpath("//table")
     * @param childLocator used to find child element. For example td By.xpath("./tr/td")
     * @return subelement
     */
    public static ElementCondition<List<WebElement>> presenceOfNestedElementsLocatedBy(
            final By parent,
            final By childLocator) {
        return new ElementCondition<List<WebElement>>() {

            @Override
            public List<WebElement> apply(SearchContext searchContext) {
                List<WebElement> allChildren = searchContext.findElement(parent).findElements(childLocator);

                return allChildren.isEmpty() ? null : allChildren;
            }

            @Override
            public String toString() {
                return String.format("visibility of element located by %s -> %s", parent, childLocator);
            }
        };
    }

    /**
     * An expectation for checking all elements from given list to be invisible
     *
     * @param elements used to check their invisibility
     * @return Boolean true when all elements are not visible anymore
     */
    public static ElementCondition<Boolean> invisibilityOfAllElements(
            final WebElement... elements) {
        return invisibilityOfAllElements(Arrays.asList(elements));
    }

    /**
     * An expectation for checking all elements from given list to be invisible
     *
     * @param elements used to check their invisibility
     * @return Boolean true when all elements are not visible anymore
     */
    public static ElementCondition<Boolean> invisibilityOfAllElements(
            final List<WebElement> elements) {
        return new ElementCondition<Boolean>() {

            @Override
            public Boolean apply(SearchContext ignored) {
                return elements.stream().allMatch(ElementConditions::isInvisible);
            }

            @Override
            public String toString() {
                return "invisibility of all elements " + elements;
            }
        };
    }

    /**
     * An expectation for checking the element to be invisible
     *
     * @param element used to check its invisibility
     * @return Boolean true when elements is not visible anymore
     */
    public static ElementCondition<Boolean> invisibilityOf(final WebElement element) {
        return new ElementCondition<Boolean>() {

            @Override
            public Boolean apply(SearchContext ignored) {
                return isInvisible(element);
            }

            @Override
            public String toString() {
                return "invisibility of " + element;
            }
        };
    }

    private static boolean isInvisible(final WebElement element) {
        try {
            return !element.isDisplayed();
        } catch (StaleElementReferenceException ignored) {
            // We can assume a stale element isn't displayed.
            return true;
        }
    }

    /**
     * An expectation with the logical or condition of the given list of conditions.
     * <p>
     * Each condition is checked until at least one of them returns true or not null.
     *
     * @param conditions ElementCondition is a list of alternative conditions
     * @return true once one of conditions is satisfied
     */
    public static ElementCondition<Boolean> or(final ElementCondition<?>... conditions) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                RuntimeException lastException = null;
                for (ElementCondition<?> condition : conditions) {
                    try {
                        Object result = condition.apply(searchContext);
                        if (result != null) {
                            if (result instanceof Boolean) {
                                if (Boolean.TRUE.equals(result)) {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                    } catch (RuntimeException e) {
                        lastException = e;
                    }
                }
                if (lastException != null) {
                    throw lastException;
                }
                return false;
            }

            @Override
            public String toString() {
                StringBuilder message = new StringBuilder("at least one condition to be valid: ");
                Joiner.on(" || ").appendTo(message, conditions);
                return message.toString();
            }
        };
    }


    /**
     * An expectation with the logical and condition of the given list of conditions.
     * <p>
     * Each condition is checked until all of them return true or not null
     *
     * @param conditions ElementCondition is a list of alternative conditions
     * @return true once all conditions are satisfied
     */
    public static ElementCondition<Boolean> and(final ElementCondition<?>... conditions) {
        return new ElementCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext searchContext) {
                for (ElementCondition<?> condition : conditions) {
                    Object result = condition.apply(searchContext);

                    if (result instanceof Boolean) {
                        if (Boolean.FALSE.equals(result)) {
                            return false;
                        }
                    }

                    if (result == null) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                StringBuilder message = new StringBuilder("all conditions to be valid: ");
                Joiner.on(" && ").appendTo(message, conditions);
                return message.toString();
            }
        };
    }
}
