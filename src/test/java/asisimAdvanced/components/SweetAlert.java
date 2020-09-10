package asisimAdvanced.components;

import asisimAdvanced.enums.AlertStatus;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;

import static asisimAdvanced.support.CustomConditions.sweetAlertIsPresent;

public class SweetAlert extends PageComponent {
    @Find(tagName = "h2")
    private Element message;

    public SweetAlert() {

    }

    public SweetAlert(Element context) {
        super(context);
    }

    public AlertStatus status() {
        driverWait().withMessage("alert did not show")
                    .until(sweetAlertIsPresent());

        Element displayedIcon = $(".icon[style=\"display: block;\"]");
        String[] classes = displayedIcon.getAttribute("class").split(" ");
        String alertStatus = classes[1];

        return AlertStatus.valueOf(alertStatus.toUpperCase());
    }

    public String message() {
        return message.getText();
    }

    public boolean isDisplayed() {
        return getContext().isDisplayed();
    }
}
