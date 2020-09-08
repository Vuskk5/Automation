package asisimAdvanced.pages.manage.soldiers;

import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.AbstractManagementPage;
import org.openqa.selenium.WebDriver;

public class ManageSoldiersPage extends AbstractManagementPage<Soldier> {

    private NewSoldierModal newModal;

    public ManageSoldiersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void add(Soldier soldier) {
        clickNew();
    }

    @Override
    public void delete(Soldier soldier) {
        select(soldier);
    }

    @Override
    public void edit(Soldier soldier) {
        select(soldier);
    }

    @Override
    public void select(Soldier soldier) {
        String soldierId = String.valueOf(soldier.id());
        table().findCellWith(soldierId).click();
    }
}
