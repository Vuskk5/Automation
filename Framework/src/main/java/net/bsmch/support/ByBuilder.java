package net.bsmch.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.selophane.elements.base.Element;

public class ByBuilder {
    public static ByChained fullLocator(WebElement element) {
        By[] locators = getLocators(element);

        return new ByChained(locators);
    }

    public static ByChained fullLocator(Element element) {
        return fullLocator(element.getWrappedElement());
    }

    public static By locator(WebElement element) {
        By[] locators = getLocators(element);

        return locators[locators.length - 1];
    }

    public static By locator(Element element) {
        return locator(element.getWrappedElement());
    }

    private static By[] getLocators(WebElement element) {
        String[] locatorsAsText = element.toString()
                .replaceAll("\\]|\\[", "")
                .split(" -> ");

        By[] locators = new By[locatorsAsText.length - 1];

        for (int i = 1; i < locatorsAsText.length; i++) {
            String how = locatorsAsText[i].split(":")[0].trim();
            String using = locatorsAsText[i].split(":")[1].trim();
            locators[i - 1] = buildByFrom(how, using);
        }

        return locators;
    }

    private static By buildByFrom(String how, String using) {
        switch (how) {
            case "class name":
                return By.className(using);
            case "id":
                return By.id(using);
            case "link text":
                return By.linkText(using);
            case "name":
                return By.name(using);
            case "partial link text":
                return By.partialLinkText(using);
            case "tag name":
                return By.tagName(using);
            case "css selector":
                return By.cssSelector(using);
            case "xpath":
                return By.xpath(using);
            default:
                return null;
        }
    }
}
