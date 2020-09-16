package asisimAdvanced.tests.reception;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.models.Appointment;
import asisimAdvanced.pages.soldiertreatment.OrderAppointmentPage;
import asisimAdvanced.support.DataClass;
import asisimAdvanced.tests.base.IndependentTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderAppointmentPageTest extends IndependentTest {
    @Test(dataProvider = "NewAppointmentData", dataProviderClass = DataClass.class)
    public void orderAppointmentWithValidDetails(Appointment appointment) {
        OrderAppointmentPage page = NavigateTo.SoldierTreatment.orderAppointment();

        page.chooseSoldier(appointment.soldierId())
            .selectSeverity(appointment.severityName())
            .setDetails(appointment.soldierDesc())
            .selectDate(appointment.beginTime())
            .filterByDoctor(appointment.doctorName())
            .orderAppointment(appointment.doctorName(), appointment.beginTime());

        SweetAlert alert = page.alert();
        assertThat(alert.status())
                    .as(alert.message())
                    .isEqualTo(SweetAlert.Status.SUCCESS);
    }
}
