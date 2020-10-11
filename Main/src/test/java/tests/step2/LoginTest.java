package tests.step2;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static tests.step2.BaseTest.driver;

public class LoginTest {
    @Test
    public void loginAsDeveloper() {
        final String USERNAME = "developer";
        final String PASSWORD = "developer";

        driver.findElement(By.name("username")).sendKeys(USERNAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);

        driver.findElement(By.tagName("button")).click();
    }
}
