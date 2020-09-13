package asisimAdvanced.pages.soldiertreatment.fragment;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextBox;

import static net.bsmch.elementwait.ElementConditions.numberOfElementsToBeMoreThan;

public class AppointmentDetails extends PageComponent {
    @Find(id = "severities")
    private Select severity;
    @Find(id = "reason")
    private TextBox description;

    public AppointmentDetails selectSeverity(String severityName) {
        Awaitility.await().until(() -> severity.getOptions().size() > 1);
        contextWait().until(driver -> severity.getOptions().size() > 1);
        severity.selectByVisibleText(severityName);
        return this;
    }

    public AppointmentDetails setDescription(String description) {
        this.description.set(description);
        return this;
    }

    public String getSeverity() {
        return severity.getFirstSelectedOption().getText();
    }

    public String getDescription() {
        return description.value();
    }
}
