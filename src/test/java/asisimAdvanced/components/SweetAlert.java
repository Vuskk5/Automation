package asisimAdvanced.components;

import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.pages.general.CustomConditions;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;

public class SweetAlert extends PageComponent {
    @Find(tagName = "h2")
    private Element message;

    public SweetAlert() {

    }

    public SweetAlert(Element context) {
        super(context);
    }

    public AlertStatus status() {
        new WebDriverWait(getDriver(), 10)
                .withMessage("alert did not show")
                .until(CustomConditions.sweetAlertIsPresent());

        String[] statuses = $(".icon[style=\"display: block;\"]").getAttribute("class").split(" ");

        return AlertStatus.valueOf(statuses[1].toUpperCase());
    }

    public String message() {
        return message.getText();
    }

    public boolean isDisplayed() {
        return getContext().isDisplayed();
    }
}
