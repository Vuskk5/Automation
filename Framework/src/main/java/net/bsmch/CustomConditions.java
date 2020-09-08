package net.bsmch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.selophane.elements.base.Element;

public class CustomConditions {
    public static ExpectedCondition<Element> elementToHaveValue(Element element) {
        return new ExpectedCondition<Element>() {
            @Override
            public Element apply(WebDriver input) {
                String value = element.getAttribute("value");
                if (value != null && !value.equals("")) {
                    return null;
                }

                return element;
            }

            @Override
            public String toString() {
                return null;
            }
        };
    }
}
