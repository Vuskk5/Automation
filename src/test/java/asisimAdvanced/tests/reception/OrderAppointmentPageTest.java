package asisimAdvanced.tests.reception;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.pages.soldiertreatment.OrderAppointmentPage;
import asisimAdvanced.tests.base.IndependentTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderAppointmentPageTest extends IndependentTest {
    @Test
    public void orderAppointmentWithValidDetails() {
        OrderAppointmentPage page = NavigateTo.SoldierTreatment.orderAppointment();

        page.chooseSoldier("5285562")
            .selectSeverity("לצורך פטור")
            .setDetails("החייל חולה")
            .selectDate(2015, 10, 10)
            .filterByDoctor("כפיר דורון")
            .orderAppointment("כפיר דורון", "8:50:00");

        SweetAlert alert = page.alert();
        assertThat(alert.status())
                    .as(alert.message())
                    .isEqualTo(AlertStatus.SUCCESS);
    }
}
