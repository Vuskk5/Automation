package asisimAdvanced.pages.soldiertreatment.fragment;

import asisimAdvanced.components.DatePicker;
import io.cucumber.java.an.E;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.Table;

import java.util.List;
import java.util.Optional;

public class AppointmentSelection extends PageComponent {
    @Find(id = "specificDoctor")
    private Element doctorFilter;
    @Find(id = "clinicDoctors")
    private Element clinicFilter;
    @Find(id = "allDoctors")
    private Element showAll;
    @Find(id = "doctors")
    private Select doctors;
    @Find(attribute = "onclick", value = "getAppointments()")
    private Element availableAppointmentsButton;
    @Find(id = "datepicker")
    private DatePicker datePicker;
    @Find(className = "table-hover")
    private Table appointments;

    public AppointmentSelection showAllAppointments() {
        showAll.click();
        showAvailableAppointments();
        return this;
    }

    public AppointmentSelection filterByClinic() {
        clinicFilter.click();
        showAvailableAppointments();
        return this;
    }

    public AppointmentSelection filterByDoctor(String doctorName) {
        doctorFilter.click();
        getWait().until((WebElement) -> doctors.getOptions().size() > 1);
        doctors.selectByVisibleText(doctorName);
        showAvailableAppointments();
        return this;
    }

    private void showAvailableAppointments() {
        availableAppointmentsButton.click();

        getWait().until(WebElement -> getContext().findElements(By.cssSelector("#schedule tr")).size() > 0);
    }

    public AppointmentSelection selectDate(int year, int month, int day) {
        datePicker.selectYear(year);
        datePicker.selectMonth(month);
        datePicker.selectDay(day);
        return this;
    }

    public AppointmentSelection selectAppointment(String doctorName, String time) {
        List<Element> appointmentsForDoctor = appointments.findRowsWith(doctorName);

        if (!appointmentsForDoctor.isEmpty()) {
            Optional<Element> appointment = appointmentsForDoctor.stream()
                                 .filter(element -> element.getText().contains(time))
                                 .findFirst();

            appointment.ifPresent(WebElement::click);
            return this;
        }
        else {
            throw new InvalidArgumentException("No available appointment for '" + doctorName + "' at '" + time + "'");
        }
    }
}
