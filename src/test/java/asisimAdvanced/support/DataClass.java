package asisimAdvanced.support;

import asisimAdvanced.models.Soldier;
import org.testng.annotations.DataProvider;

public class DataClass {
    @DataProvider(name = "SoldierData")
    public Object[][] getSoldierData() {
        return JsonUtil.getDataAs2dArray("test-data/SoldierData.json", Soldier.class);
    }
}
