package asisimAdvanced.tests.manage;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import asisimAdvanced.support.util.DataManager;
import asisimAdvanced.tests.base.MethodLevelRunner;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddSoldierTest extends MethodLevelRunner {
    private ManageSoldiersPage managementPage;

    @Test(  testName = "Add Soldier w/ Valid Details",
            groups = { "a:Kfir Doron", "d:Windows PC", "t:DataDriven", "t:Management" },
            dataProvider = "SoldierData", dataProviderClass = DataManager.class)

    public void addSoldierWithValidDetails(Soldier soldier) {
        open();
        loginAs("developer", "developer");
        managementPage = NavigateTo.BasisManagement.soldiers();
        managementPage.add(soldier);

        SweetAlert alert = managementPage.alert();

        assertThat(alert.status())
                        .as(alert.message())
                        .isEqualTo(SweetAlert.Status.SUCCESS);
    }
}
