package asisimAdvanced.pages.soldiertreatment.fragment;

import asisimAdvanced.components.DatePicker;
import asisimAdvanced.support.DateUtil;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.InvalidArgumentException;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.Table;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.await;

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
        contextWait().until((WebElement) -> doctors.getOptions().size() > 1);
        doctors.selectByVisibleText(doctorName);
        showAvailableAppointments();
        return this;
    }

    private void showAvailableAppointments() {
        availableAppointmentsButton.click();

        await().until(() -> $$("#schedule tr").size() > 0);
    }

    public AppointmentSelection selectDate(Date date) {
        datePicker.selectYear(DateUtil.get(Calendar.YEAR, date));
        datePicker.selectMonth(DateUtil.get(Calendar.MONTH, date));
        datePicker.selectDay(DateUtil.get(Calendar.DAY_OF_MONTH, date));
        return this;
    }

    public AppointmentSelection selectAppointment(String doctorName, Date time) {
        List<Element> appointmentsForDoctor = appointments.findRowsWith(doctorName);

        if (!appointmentsForDoctor.isEmpty()) {
            Optional<Element> appointment = appointmentsForDoctor.stream()
                                 .filter(element -> element.getText().contains(DateUtil.timeString(time)))
                                 .findFirst();

            appointment.ifPresent(Element::click);
            return this;
        }
        else {
            throw new InvalidArgumentException("No available appointment for '" + doctorName + "' at '" + time + "'");
        }
    }
}
