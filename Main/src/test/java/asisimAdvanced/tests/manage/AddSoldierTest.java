package asisimAdvanced.tests.manage;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import asisimAdvanced.support.util.DataManager;
import asisimAdvanced.tests.base.DataDrivenTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddSoldierTest extends DataDrivenTest {
    private ManageSoldiersPage managementPage;

    @Test(  dataProvider = "SoldierData", dataProviderClass = DataManager.class,
            testName = "Add Soldier w/ Valid Details",
            groups = { "a:Kfir Doron", "d:Windows PC", "t:DataDriven", "t:Management" })
    public void addSoldierWithValidDetails(Soldier soldier) {
        managementPage = NavigateTo.BasisManagement.soldiers();
        managementPage.add(soldier);

        SweetAlert alert = managementPage.alert();
        assertThat(alert.status())
                        .as(alert.message())
                        .isEqualTo(SweetAlert.Status.SUCCESS);
    }
}
