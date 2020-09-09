package asisimAdvanced.main;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.components.navigation.NavigateTo;
import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.enums.Severities;
import asisimAdvanced.models.Soldier;
import asisimAdvanced.pages.manage.soldiers.ManageSoldiersPage;
import asisimAdvanced.pages.soldiertreatment.ReceptionPage;
import asisimAdvanced.support.DateUtil;
import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

public class Main {
    private WebDriver driver;

    @BeforeTest
    public void initialize() {
        driver = DriverManager.startChrome();
        driver.get("http://localhost:9000");
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void login() {
        driver.findElement(By.id("username")).sendKeys("developer");
        driver.findElement(By.id("password")).sendKeys("developer");
        driver.findElement(By.tagName("button")).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("navbar")));
    }

    @Test
    public void test() {
        NavigateTo.SoldierTreatment.newAppointment.run();

        ReceptionPage page = new ReceptionPage(driver);
        page.selectSoldier("5285562")
            .selectSeverity(Severities.CasualAppointment)
            .setDetails("He is sick")
            .selectDate(2015, 10, 10)
            .filterByDoctor("Hadar Namdar")
            .orderAppointment("Hadar Namdar", "8:50:00");

        SweetAlert alert = page.alert();
        assertThat(alert.status()).as(alert.message()).isEqualTo(AlertStatus.SUCCESS);
    }

    @DataProvider(name = "SoldierData")
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

    private ManageSoldiersPage managementPage;
    @Test(dataProvider = "SoldierData")
    public void addSoldierTest(Soldier soldier) {
        managementPage = NavigateTo.BasisManagement.soldiers();
        managementPage.add(soldier);

        SweetAlert alert = managementPage.alert();
        assertThat(alert.status()).as(alert.message()).isEqualTo(AlertStatus.SUCCESS);
    }

    @AfterTest
    public void terminate() {
        driver.quit();
    }
}
