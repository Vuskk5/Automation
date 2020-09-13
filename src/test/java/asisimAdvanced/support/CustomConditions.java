package asisimAdvanced.support;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.selophane.elements.widget.Select;

public class CustomConditions {
    public static CustomCondition<Boolean> sweetAlertIsPresent() {
        return new CustomCondition<Boolean>() {
            @Override
            public Boolean apply(SearchContext context) {
                return context.findElement(By.className("sweet-alert")).isDisplayed();
            }

            @Override
            public String toString() {
                return "alert to open";
            }
        };
    }
}
