package asisimAdvanced.components;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.selophane.elements.base.Element;

import static asisimAdvanced.support.CustomConditions.sweetAlertIsDisplayed;

public class SweetAlert extends PageComponent {
    @Find(tagName = "h2")
    private Element message;

    public SweetAlert() {

    }

    public SweetAlert(Element context) {
        super(context);
    }

    public Status status() {
        driverWait().withMessage("alert did not show")
                    .until(sweetAlertIsDisplayed());

        Element displayedIcon = $(".icon[style=\"display: block;\"]");
        String[] classes = displayedIcon.getAttribute("class").split(" ");
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
        CUSTOM();
    }
}
