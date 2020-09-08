package practice.asisim.pages.management;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import practice.asisim.pages.BasePage;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SoldierManagementPage extends BasePage {
    @FindBy(css = "#table > tr > td:nth-child(1)")
    private List<WebElement> soldiersIds;
    @FindBy(css = "#table > tr > td:nth-child(2)")
    private List<WebElement> soldersNumbers;

    @FindBy(css = "[data-target='#new']")
    private WebElement newSoldierButton;

    @FindBy(id = "newId")
    private WebElement newId;
    @FindBy(id = "newName")
    private WebElement newName;
    @FindBy(id = "newClinicId")
    private WebElement newClinic;
    @FindBy(id = "newDraftDate")
    private WebElement newDraftDate;
    @FindBy(id = "newReleaseDate")
    private WebElement newReleaseDate;
    @FindBy(id = "newRankId")
    private WebElement newRankId;
    @FindBy(id = "close-modal")
    private WebElement createSoldierButton;

    public SoldierManagementPage(WebDriver driver) {
        super(driver);

        wait.withMessage("The soldiers list did not load in time.")
            .until(numberOfElementsToBeMoreThan(By.cssSelector("#table > tr"), 0));
    }

    public void openNewSoldierScreen() {
        newSoldierButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new")));
    }

    public void fillSoldierDetailsAndSubmit(String soldierId, String name, String clinicName, String draftDate, String releaseDate, String rank) {
        newId.sendKeys(soldierId);
        newName.sendKeys(name);
        new Select(newClinic).selectByVisibleText(clinicName);
        newDraftDate.sendKeys(draftDate);
        newReleaseDate.sendKeys(releaseDate);
        new Select(newRankId).selectByVisibleText(rank);

        createSoldierButton.click();
    }

    public int getSoldierCount() {
        return soldiersIds.size();
    }
}
