package tests.step2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static tests.step2.BaseTest.driver;
import static tests.step2.BaseTest.wait;

public class OrderMedicineTest {
    @Test
    public void orderMedicineTest() {
        final int MAX_AMOUNT_OF_MEDICINE = 10;
        Random random = new Random();

        driver.findElement(By.linkText("ניהול מרפאה")).click();
        driver.findElement(By.linkText("קליטת תרופות")).click();

        wait.withMessage("There are no medicines in the list.")
            .until(numberOfElementsToBeMoreThan(By.className("medicine"), 0));

        List<WebElement> medicines = driver.findElements(By.className("medicine"));
        medicines.get((int)(Math.random() * medicines.size())).click();

        WebElement amountToOrder = driver.findElement(By.className("details-med-amount"));
        amountToOrder.clear();
        amountToOrder.sendKeys(String.valueOf(random.nextInt(MAX_AMOUNT_OF_MEDICINE) + 1));

        driver.findElement(By.className("save")).click();
        wait.withMessage("Success alert was not displayed.")
            .until(visibilityOfElementLocated(By.className("alert-success")));
    }

    @AfterMethod
    public void returnHome() {
        driver.findElement(By.id("Asisim")).click();
        wait.withMessage("Home page did not load.")
            .until(visibilityOfElementLocated(By.id("jumbotron")));
    }
}
