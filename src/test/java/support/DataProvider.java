package support;

import asisimAdvanced.models.Soldier;
import asisimAdvanced.support.DateUtil;

public class DataProvider {
    @org.testng.annotations.DataProvider(name = "SoldierData")
    public Object[][] getSoldierData() {
        return new Object[][] {
                new Soldier[]{
                        new Soldier(8702558L, "Kfir Doron",
                                DateUtil.getDate(2018, 8, 7),
                                DateUtil.getDate(2021, 4, 6),
                                15L, 3L)
                }
        };
    }
}
