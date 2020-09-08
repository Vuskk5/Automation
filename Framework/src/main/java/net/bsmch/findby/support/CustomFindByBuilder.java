package net.bsmch.findby.support;

import net.bsmch.findby.FindAll;
import net.bsmch.findby.Find;
import net.bsmch.findby.Finds;
import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class CustomFindByBuilder extends AbstractFindByBuilder {
    public abstract By buildIt(Object annotation, Field field);

    protected By buildByFromFindBy(Find findBy) {
        assertValidFindBy(findBy);

        return buildByFromShortFindBy(findBy);
    }

    protected By buildByFromShortFindBy(Find findBy) {
        if (!"".equals(findBy.className())) {
            return By.className(findBy.className());
        }

        if (!"".equals(findBy.css())) {
            return By.cssSelector(findBy.css());
        }

        if (!"".equals(findBy.id())) {
            return By.id(findBy.id());
        }

        if (!"".equals(findBy.linkText())) {
            return By.linkText(findBy.linkText());
        }

        if (!"".equals(findBy.name())) {
            return By.name(findBy.name());
        }

        if (!"".equals(findBy.partialLinkText())) {
            return By.partialLinkText(findBy.partialLinkText());
        }

        if (!"".equals(findBy.attribute())) {
            return By.cssSelector("[" + findBy.attribute() + "=\"" + findBy.value() + "\"]");
        }

        if (!"".equals(findBy.value())) {
            return By.cssSelector("[value=\"" + findBy.value() + "\"]");
        }

        if (!"".equals(findBy.xpath())) {
            return By.xpath(findBy.xpath());
        }

        if (!"".equals(findBy.xpathText())) {
            String xpath = findBy.isDescendant() ? "descendant::" : "//";
            xpath += !("".equals(findBy.tagName())) ? findBy.tagName() : "*";
            xpath += findBy.checkContains() ? ("[contains(text(), \"" + findBy.xpathText() + "\")]")
                                            : ("[text()=\"" + findBy.xpathText() + "\"]");
            return By.xpath(xpath);
        }

        if (!"".equals(findBy.xpathString())) {
            String xpath = findBy.isDescendant() ? "descendant::" : "//";
            xpath += !("".equals(findBy.tagName())) ? findBy.tagName() : "*";
            xpath += findBy.checkContains() ? ("[contains(string(), \"" + findBy.xpathString() + "\")]")
                                            : ("[string()=\"" + findBy.xpathText() + "\"]");
            return By.xpath(xpath);
        }

        if (!"".equals(findBy.tagName())) {
            return By.tagName(findBy.tagName());
        }

        // Fall through
        return null;
    }

    protected void assertValidFindBys(Finds findBys) {
        for (Find findBy : findBys.value()) {
            assertValidFindBy(findBy);
        }
    }

    protected void assertValidFindBy(Find findBy) {
        if (findBy.checkContains()) {
            if (findBy.xpathText().equals("") && findBy.xpathString().equals("")) {
                throw new IllegalArgumentException(
                        "If you set 'checkContains' to true, you must also set 'xpathText' or 'xpathString'");
            }
        }

        if (findBy.isDescendant()) {
            if (findBy.xpathText().equals("") && findBy.xpathString().equals("")) {
                throw new IllegalArgumentException(
                        "If you set 'isDescendant' to true, you must also set 'xpathText' or 'xpathString'");
            }
        }

        if (!findBy.attribute().equals("") && findBy.value().equals("")) {
            throw new IllegalArgumentException("If you set 'attribute' you must also set 'value'.");
        }

        Set<String> finders = new HashSet<>();
        if (!"".equals(findBy.className())) finders.add("class name:" + findBy.className());
        if (!"".equals(findBy.css())) finders.add("css:" + findBy.css());
        if (!"".equals(findBy.id())) finders.add("id: " + findBy.id());
        if (!"".equals(findBy.linkText())) finders.add("link text: " + findBy.linkText());
        if (!"".equals(findBy.name())) finders.add("name: " + findBy.name());
        if (!"".equals(findBy.partialLinkText()))
            finders.add("partial link text: " + findBy.partialLinkText());
        if (!"".equals(findBy.tagName())) finders.add("tag name: " + findBy.tagName());
        if (!"".equals(findBy.xpath())) finders.add("xpath: " + findBy.xpath());

        // A zero count is okay: it means to look by name or id.
        if (finders.size() > 1) {
            throw new IllegalArgumentException(
                    String.format("You must specify at most one location strategy. Number found: %d (%s)",
                            finders.size(), finders.toString()));
        }
    }

    protected void assertValidFindAll(FindAll findBys) {
        for (Find findBy : findBys.value()) {
            assertValidFindBy(findBy);
        }
    }
}
