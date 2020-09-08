package asisimAdvanced.navigation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.drivermanager.DriverManager;

import java.util.concurrent.TimeUnit;

class MenuDropDown {
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

    // TODO: Change return type to page
    protected Void openSubMenu(By subMenu) {
        menuContext.findElement(subMenu).click();
        return null;
    }
}
