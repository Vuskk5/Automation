package asisimAdvanced.pages.soldiertreatment.fragment;

import asisimAdvanced.enums.Severities;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextBox;

public class AppointmentDetails extends PageComponent {
    @Find(id = "severities")
    private Select severity;
    @Find(id = "reason")
    private TextBox description;

    public AppointmentDetails selectSeverity(Severities severity) {
        this.severity.selectByVisibleText(severity.getValue());
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
