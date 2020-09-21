package asisimAdvanced.pages.soldiertreatment;

import asisimAdvanced.pages.general.MainPage;
import asisimAdvanced.pages.soldiertreatment.fragment.AppointmentDetails;
import asisimAdvanced.pages.soldiertreatment.fragment.AppointmentSelection;
import asisimAdvanced.pages.soldiertreatment.fragment.SoldierDetails;
import jxl.write.DateTime;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.Find;
import static org.awaitility.Awaitility.await;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;
import org.selophane.elements.factory.api.ElementFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.util.concurrent.TimeUnit.*;

public class OrderAppointmentPage extends MainPage {
    @Find(xpath = "//legend[text() = \"פרטי חייל:\"]/parent::fieldset")
    private SoldierDetails soldierDetails;

    @Find(xpath = "//legend[text() = \"פרטי הפניה:\"]/parent::fieldset")
    private AppointmentDetails appointmentDetails;

    @Find(xpath = "//legend[text() = \"מועד התור:\"]/parent::fieldset")
    private AppointmentSelection appointmentSelection;

    @Find(attribute = "onclick", value = "createAppointment()")
    private Element createAppointment;

    public OrderAppointmentPage(WebDriver driver) {
        super(driver);
        ComponentFactory.initComponents(getDriver(), this);
        ElementFactory.initElements(getDriver(), this);
    }

    public OrderAppointmentPage chooseSoldier(Long soldierId) {
        soldierDetails.setId(soldierId);
        soldierDetails.selectSoldier();

        await("Soldier details were not loaded in time").atMost(10, SECONDS)
                                                        .until(soldierDetails::isLoaded);

        return this;
    }

    public OrderAppointmentPage selectSeverity(String severity) {
        appointmentDetails.selectSeverity(severity);
        return this;
    }

    public OrderAppointmentPage setDetails(String description) {
        appointmentDetails.setDescription(description);
        return this;
    }

    public OrderAppointmentPage selectDate(LocalDateTime date) {
        appointmentSelection.selectDate(date);
        return this;
    }

    public OrderAppointmentPage filterByDoctor(String doctorName) {
        appointmentSelection.filterByDoctor(doctorName);
        return this;
    }

    public OrderAppointmentPage filterByClinic() {
        appointmentSelection.filterByClinicDoctors();
        return this;
    }

    public OrderAppointmentPage filterNothing() {
        appointmentSelection.showAllAppointments();
        return this;
    }

    public void orderAppointment(String doctorName, LocalDateTime beginTime) {
        LocalTime time = beginTime.toLocalTime();

        appointmentSelection.selectAppointment(doctorName, time);
        createAppointment.click();
    }
}
