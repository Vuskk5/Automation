package asisimAdvanced.tests.manage;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import asisimAdvanced.support.DataClass;
import asisimAdvanced.tests.base.IndependentTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddSoldierTest extends IndependentTest {
    private ManageSoldiersPage managementPage;

    @Test(dataProvider = "SoldierData", dataProviderClass = DataClass.class)
    public void addSoldierWithValidDetails(Soldier soldier) {
        managementPage = NavigateTo.BasisManagement.soldiers();
        managementPage.add(soldier);

        SweetAlert alert = managementPage.alert();
        assertThat(alert.status())
                        .as(alert.message())
                        .isEqualTo(SweetAlert.Status.SUCCESS);
    }
}
