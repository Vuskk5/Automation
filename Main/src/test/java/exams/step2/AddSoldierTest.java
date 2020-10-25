package exams.step2;

import com.aventstack.extentreports.service.ExtentTestManager;
import net.bsmch.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static exams.step2.BaseTest.driver;
import static exams.step2.BaseTest.wait;

public class AddSoldierTest {
    private int numberOfSoldiersBeforeAdding;

    @BeforeClass
    public void setUp() {
        // Navigate
        wait.until(elementToBeClickable(By.id("BasisManagement"))).click();
        driver.findElement(By.linkText("ניהול חיילים")).click();
    }

    @Test
    public void addNewSoldierTest() {
        // Save the current amount of soldiers in the list
        numberOfSoldiersBeforeAdding = driver.findElements(By.cssSelector("#table tr")).size();

        wait.until(elementToBeClickable(By.cssSelector("[data-target='#new']"))).click();

        // Define select elements
        Select newRank   = new Select(wait.until(visibilityOfElementLocated(By.id("newRankId"))));
        Select newClinic = new Select(driver.findElement(By.id("newClinicId")));

        // Randomize a number (1000000 - 9999999)
        final String newId = String.valueOf((int)(Math.random() * 8999999 + 1000000));
        final String newName = "Michel Obama";
        final String newDraftDate = "11/09/2001";
        final String newReleaseDate = "11/05/2003";
        final int    newClinicId = (int)(Math.random() * (newClinic.getOptions().size() - 1) + 1);
        final int    newRankId   = (int)(Math.random() * (newRank.getOptions().size() - 1) + 1);

        // Send inputs
        driver.findElement(By.id("newId")).sendKeys(newId);
        driver.findElement(By.id("newName")).sendKeys(newName);
        newClinic.selectByIndex(newClinicId);
        driver.findElement(By.id("newDraftDate")).sendKeys(newDraftDate);
        driver.findElement(By.id("newReleaseDate")).sendKeys(newReleaseDate);
        newRank.selectByIndex(newRankId);
        ExtentTestManager.getTest().info("- Soldier Details -" +
                        "<br>ID: " + newId +
                        "<br>Name: " + newName +
                        "<br>Clinic ID: " + newClinicId +
                        "<br>Draft Date: " + newDraftDate +
                        "<br>Release Date: " + newReleaseDate +
                        "<br>Rank ID: " + newRankId, Screenshot.captureScreen("SoldierDetails", driver));

        driver.findElement(By.cssSelector("[onclick='create()']")).click();

        WebElement messageIcon = wait.until(visibilityOfElementLocated(By.cssSelector(".icon[style=\"display: block;\"]")));

        Assert.assertTrue(messageIcon.getAttribute("class").contains("success"),
                    "The soldier could not have been added.");
    }

    @AfterMethod
    public void checkIfSoldierWasAdded() {
        wait.until(elementToBeClickable(By.className("confirm"))).click();

        // Wait until the screen fades-out and soldier was added
        wait.until(invisibilityOfElementLocated(By.className("sweet-overlay")));

        wait.withMessage("The soldier was not added within 10 seconds.")
            .until(numberOfElementsToBeMoreThan(By.cssSelector("#table tr"), numberOfSoldiersBeforeAdding));
    }

    @AfterClass
    public void returnToHomePage() {
        driver.findElement(By.id("Asisim")).click();
        wait.until(visibilityOfElementLocated(By.id("jumbotron")));
    }
}
