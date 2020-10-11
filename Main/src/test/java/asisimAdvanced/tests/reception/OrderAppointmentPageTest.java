package asisimAdvanced.tests.reception;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.models.Appointment;
import asisimAdvanced.pages.soldiertreatment.OrderAppointmentPage;
import asisimAdvanced.support.util.DataManager;
import asisimAdvanced.tests.base.DataDrivenTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderAppointmentPageTest extends DataDrivenTest {
    @Test(  dataProvider = "NewAppointmentData", dataProviderClass = DataManager.class,
            testName = "Order Appointment",
            groups = { "a:Kfir Doron", "d:Windows PC", "t:DataDriven", "t:Appointments" })
    public void orderAppointmentWithValidDetails(Appointment appointment) {
        OrderAppointmentPage page = NavigateTo.SoldierTreatment.orderAppointment();

        page.chooseSoldier(appointment.soldierId())
            .selectSeverity(appointment.severityName())
            .setDetails(appointment.reason())
            .selectDate(appointment.beginTime())
            .filterByDoctor(appointment.doctorName())
            .orderAppointment(appointment.doctorName(), appointment.beginTime());

        SweetAlert alert = page.alert();
        assertThat(alert.status())
                    .as(alert.message())
                    .isEqualTo(SweetAlert.Status.SUCCESS);
    }
}
