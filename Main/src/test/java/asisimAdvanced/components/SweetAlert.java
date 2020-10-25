package asisimAdvanced.components;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.awaitility.Awaitility;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;

import static asisimAdvanced.support.CustomConditions.sweetAlertIsDisplayed;
import static org.awaitility.Awaitility.*;

public class SweetAlert extends PageComponent {
    @Find(tagName = "h2")
    private Element message;

    public SweetAlert() {

    }

    public SweetAlert(Element context) {
        super(context);
    }

    public Status status() {
        await().until(() -> getContext().isDisplayed());

        Element displayedIcon = $$(".icon") .stream()
                                            .filter(Element::isDisplayed)
                                            .findFirst()
                                            .orElseThrow(() -> new NoSuchElementException("Cannot locate overlay icons"));

        String[] classes = displayedIcon.getAttribute("class").split(" ");
        // 0 - .icon,
        // 1 - .[status],
        // 2 - .[statusAnimation]
        String alertStatus = classes[1];

        return Status.valueOf(alertStatus.toUpperCase());
    }

    public String message() {
        return message.getText();
    }

    public boolean isDisplayed() {
        return getContext().isDisplayed();
    }

    public enum Status {
        ERROR(),
        WARNING(),
        INFO(),
        SUCCESS(),
        CUSTOM()
    }
}
