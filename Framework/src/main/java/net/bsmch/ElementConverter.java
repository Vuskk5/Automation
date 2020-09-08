package net.bsmch;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

import java.util.ArrayList;
import java.util.List;

public class ElementConverter {
    public static Element convert(WebElement element) {
        return new ElementImpl(element);
    }

    public static List<Element> convertList(List<WebElement> elements) {
        List<Element> newElements = new ArrayList<>();
        elements.forEach(element -> newElements.add(new ElementImpl(element)));
        return newElements;
    }
}
