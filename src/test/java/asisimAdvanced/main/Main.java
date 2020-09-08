package asisimAdvanced.main;

import asisimAdvanced.components.SweetAlert;
import asisimAdvanced.enums.AlertStatus;
import asisimAdvanced.enums.Severities;
import asisimAdvanced.navigation.NavigateTo;
import asisimAdvanced.pages.soldiertreatment.ReceptionPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import support.drivermanager.DriverManager;

public class Main {
    private WebDriver driver;

    @BeforeTest
    public void initialize() {
        WebDriverManager.config().setProxy("10.0.0.10:80");
        WebDriverManager.chromedriver().setup();
        driver = DriverManager.startChrome();
        driver.get("http://localhost:9000");
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void login() {
        driver.findElement(By.name("username")).sendKeys("developer");
        driver.findElement(By.name("password")).sendKeys("developer");
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

    @AfterTest
    public void terminate() {
        driver.quit();
    }
}
