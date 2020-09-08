package asisimAdvanced.pages.soldiertreatment;

import asisimAdvanced.enums.Severities;
import asisimAdvanced.pages.general.CustomConditions;
import asisimAdvanced.pages.general.MainPage;
import asisimAdvanced.pages.soldiertreatment.fragment.AppointmentDetails;
import asisimAdvanced.pages.soldiertreatment.fragment.AppointmentSelection;
import asisimAdvanced.pages.soldiertreatment.fragment.SoldierDetails;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selophane.elements.base.Element;
import org.selophane.elements.factory.api.ElementFactory;

public class ReceptionPage extends MainPage {
    @Find(xpath = "//legend[text() = \"פרטי חייל:\"]/parent::fieldset")
    private SoldierDetails soldierDetails;

    @Find(xpath = "//legend[text() = \"פרטי הפניה:\"]/parent::fieldset")
    private AppointmentDetails appointmentDetails;

    @Find(xpath = "//legend[text() = \"מועד התור:\"]/parent::fieldset")
    private AppointmentSelection appointmentSelection;

    @Find(attribute = "onclick", value = "createAppointment()")
    private Element createAppointment;

    public ReceptionPage(WebDriver driver) {
        super(driver);
        ComponentFactory.initComponents(getDriver(), this);
        ElementFactory.initElements(driver, this);
    }

    public ReceptionPage selectSoldier(String soldierId) {
        soldierDetails.setId(soldierId);
        soldierDetails.selectSoldier();

        (new WebDriverWait(getDriver(), 10))
                .withMessage("soldier details were not loaded in time")
                .until(WebDriver -> soldierDetails.isLoaded());
        return this;
    }

    public ReceptionPage selectSeverity(Severities severity) {
        appointmentDetails.selectSeverity(severity);
        return this;
    }

    public ReceptionPage setDetails(String description) {
        appointmentDetails.setDescription(description);
        return this;
    }

    public ReceptionPage selectDate(int year, int month, int day) {
        appointmentSelection.selectDate(year, month, day);
        return this;
    }

    public ReceptionPage filterByDoctor(String doctorName) {
        appointmentSelection.filterByDoctor(doctorName);
        return this;
    }

    public ReceptionPage filterByClinic() {
        appointmentSelection.filterByClinic();
        return this;
    }

    public ReceptionPage filterNothing() {
        appointmentSelection.showAllAppointments();
        return this;
    }

    public void orderAppointment(String doctorName, String time) {
        appointmentSelection.selectAppointment(doctorName, time);
        createAppointment.click();
    }
}
