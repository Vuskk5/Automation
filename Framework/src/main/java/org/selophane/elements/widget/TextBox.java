package org.selophane.elements.widget;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * Text field functionality.
 */
@ImplementedBy(TextBoxImpl.class)
public interface TextBox extends Element {
    /**
     * @param text The text to type into the field.
     */
    void set(String text);

    /**
     * @return The text value of the element
     */
    String value();
}
