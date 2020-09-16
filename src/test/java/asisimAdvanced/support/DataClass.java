package asisimAdvanced.support;

import asisimAdvanced.models.Appointment;
import asisimAdvanced.models.Soldier;
import org.testng.annotations.DataProvider;

public class DataClass {
    @DataProvider(name = "SoldierData")
    public Object[][] getSoldierData() {
        return JsonUtil.getDataMatrix("test-data/SoldierData.json", Soldier.class);
    }

    @DataProvider(name = "NewAppointmentData")
    public Object[][] getNewAppointmentData() {
        return JsonUtil.getDataMatrix("test-data/NewAppointmentData.json", Appointment.class);
    }
}
