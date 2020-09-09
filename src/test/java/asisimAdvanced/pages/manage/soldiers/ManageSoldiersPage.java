package asisimAdvanced.pages.manage.soldiers;

import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.AbstractManagementPage;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;

public class ManageSoldiersPage extends AbstractManagementPage<ManageSoldiersPage, Soldier> {
    @Find(id = "new")
    private NewSoldierModal newModal;

    public ManageSoldiersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void add(Soldier soldier) {
        clickNew();
        newModal.setId(soldier.id())
                .setName(soldier.name())
                .selectClinic(soldier.clinicName())
                .setDraftDate(soldier.draftDate())
                .setReleaseDate(soldier.releaseDate())
                .selectRank(soldier.rankName())
                .create();
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

    @Override
    protected ManageSoldiersPage getThis() {
        return this;
    }
}
