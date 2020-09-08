package asisimAdvanced.pages.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {
    public static ExpectedCondition<Boolean> sweetAlertIsPresent() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.findElement(By.className("sweet-alert")).isDisplayed();
            }

            @Override
            public String toString() {
                return "alert to open";
            }
        };
    }
}
