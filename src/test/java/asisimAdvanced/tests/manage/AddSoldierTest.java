package asisimAdvanced.tests.manage;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import asisimAdvanced.support.DataProvider;
import asisimAdvanced.tests.base.IndependentTest;
import net.bsmch.exceptions.PageInstantiationException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddSoldierTest extends IndependentTest {
    private ManageSoldiersPage managementPage;

    @Test(dataProvider = "SoldierData", dataProviderClass = DataProvider.class)
    public void addSoldierWithValidDetails(Soldier soldier) {
        managementPage = NavigateTo.BasisManagement.soldiers();
        managementPage.add(soldier);
        throw new PageInstantiationException(managementPage.getClass(), new IllegalArgumentException(""));
//        SweetAlert alert = managementPage.alert();
//        assertThat(alert.status()).as(alert.message()).isEqualTo(AlertStatus.SUCCESS);
    }
}
