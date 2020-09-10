package asisimAdvanced.tests.reception;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.enums.Severities;
import asisimAdvanced.managers.Authenticator;
import asisimAdvanced.pages.soldiertreatment.ReceptionPage;
import asisimAdvanced.tests.base.IndependentTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderAppointmentTest extends IndependentTest {
    @Test
    public void orderAppointmentWithValidDetails() {
        NavigateTo.SoldierTreatment.newAppointment.run();

        ReceptionPage page = new ReceptionPage(driver());
        page.selectSoldier("5285562")
            .selectSeverity(Severities.CasualAppointment)
            .setDetails("He is sick")
            .selectDate(2015, 10, 10)
            .filterByDoctor("Hadar Namdar")
            .orderAppointment("Hadar Namdar", "8:50:00");

        SweetAlert alert = page.alert();
        assertThat(alert.status()).as(alert.message()).isEqualTo(AlertStatus.SUCCESS);
    }
}
