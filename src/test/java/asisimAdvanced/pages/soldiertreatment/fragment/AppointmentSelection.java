package asisimAdvanced.pages.soldiertreatment.fragment;

import asisimAdvanced.components.DatePicker;
import asisimAdvanced.support.util.DateUtil;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.InvalidArgumentException;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Label;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;

public class AppointmentSelection extends PageComponent {
    @Find(attribute = "for", value = "specificDoctor")
    private Label doctorFilter;
    @Find(attribute = "for", value = "clinicDoctors")
    private Label clinicFilter;
    @Find(attribute = "for", value = "allDoctors")
    private Label showAll;
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

    public AppointmentSelection filterByClinicDoctors() {
        clinicFilter.click();
        showAvailableAppointments();
        return this;
    }

    public AppointmentSelection filterByDoctor(String doctorName) {
        doctorFilter.click();
        await().until(() -> doctors.getOptions().size() > 1);
        doctors.selectByVisibleText(doctorName);
        showAvailableAppointments();
        return this;
    }

    private void showAvailableAppointments() {
        availableAppointmentsButton.click();

        await().until(() -> $$("#schedule tr").size() > 0);
    }

    public AppointmentSelection selectDate(LocalDateTime date) {
        datePicker.selectYear(date.getYear());
        datePicker.selectMonth(date.getMonthValue());
        datePicker.selectDay(date.getDayOfMonth());
        return this;
    }

    public AppointmentSelection selectAppointment(String doctorName, LocalTime time) {
        List<Element> appointmentsForDoctor = appointments.findRowsWith(doctorName);

        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        if (!appointmentsForDoctor.isEmpty()) {

            List<Element> appointmentsAtTime = appointmentsForDoctor.stream()
                                                .filter(element -> element.getText().contains(formattedTime))
                                                .collect(Collectors.toList());

            if (!appointmentsAtTime.isEmpty()) {
                appointmentsAtTime.get(0).click();
                return this;
            }
        }

        throw new InvalidArgumentException("No available appointment for doctor '" + doctorName + "' at '" + formattedTime + "'");
    }
}
