package exams.testngpractice;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static exams.testngpractice.BaseTest.driver;
import static exams.testngpractice.BaseTest.wait;

public class AboutWindow {
    @BeforeClass
    public void enterWindow() {
        wait.until(visibilityOfElementLocated
                (By.id("aboutLink"))).click();
    }

    @Test
    public void getInfo() {
        wait.until(visibilityOfElementLocated
                (By.id("aboutModalTitle")));

        Assert.assertNotEquals(driver.findElement
                (By.cssSelector(".modal p")).getText(), "");
    }
}
