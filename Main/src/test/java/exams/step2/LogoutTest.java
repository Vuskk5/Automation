package exams.step2;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static exams.step2.BaseTest.driver;
import static exams.step2.BaseTest.wait;

public class LogoutTest {
    @Test
    public void logout() {
        driver.findElement(By.partialLinkText("יציאה")).click();

        wait.withMessage("Login screen is not displayed.")
            .until(visibilityOfElementLocated(By.className("login-button")));
    }
}
