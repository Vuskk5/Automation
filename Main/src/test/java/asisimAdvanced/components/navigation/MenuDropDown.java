package asisimAdvanced.components.navigation;

import net.bsmch.drivermanager.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

abstract class MenuDropDown {
    protected WebDriver driver;
    protected WebElement menuContext;

    protected MenuDropDown(By menuLocator) {
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        menuContext = driver.findElement(menuLocator);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        open();
    }

    protected boolean isOpen() {
        String attribute = menuContext.getAttribute("aria-expanded");
        return attribute != null && attribute.equals("true");
    }

    protected void toggle() {
        menuContext.click();
    }

    protected void close() {
        if (isOpen()) {
            toggle();
        }
    }

    protected void open() {
        if (!isOpen()) {
            toggle();
        }
    }

    protected void clickSubOption(By subMenu) {
        menuContext.findElement(subMenu).click();
    }
}
