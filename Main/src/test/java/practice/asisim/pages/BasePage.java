package practice.asisim.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePage extends PageObject {
    @FindBy(id = "BasisManagement")
    private WebElement basisManagement;
    @FindBy(linkText = "ניהול חיילים")
    private WebElement soldierManagement;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public void goToSoldierManagement() {
        basisManagement.click();
        soldierManagement.click();
    }
}
