package practice.asisim.tests.management;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import practice.asisim.pages.BasePage;
import practice.asisim.pages.management.SoldierManagementPage;
import practice.asisim.tests.BaseTest;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class AddSoldierTest extends BaseTest {
    @BeforeMethod
    public void setUp() {
        openSystem();
        loginAs("developer", "developer");

        new BasePage(getDriver()).goToSoldierManagement();
    }

    @Test
    public void addSoldier() {
        SoldierManagementPage page = new SoldierManagementPage(getDriver());

        int initialSoldierCount = page.getSoldierCount();

        page.openNewSoldierScreen();
        page.fillSoldierDetailsAndSubmit("8700000", "John Biryon",
                                        "Medical Corps", "01/01/2020",
                                        "01/01/2022", "Private");

        getWait().withMessage("The soldier was not added in time.")
                 .until(numberOfElementsToBe(By.cssSelector("#table > tr"),
                initialSoldierCount + 1));
    }
}
