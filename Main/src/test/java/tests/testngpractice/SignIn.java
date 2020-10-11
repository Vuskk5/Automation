package tests.testngpractice;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static tests.testngpractice.SignUp.EMAIL;
import static tests.testngpractice.SignUp.PASSWORD;
import static tests.testngpractice.BaseTest.driver;
import static tests.testngpractice.BaseTest.wait;

public class SignIn {
    @BeforeClass
    public void enterPage() {
        final String SIGN_IN_PATH = "http://dbankdemo.com/login";
        driver.get(SIGN_IN_PATH);
    }

    @Test
    public void signIn() {
        // Enter info
        wait.until(elementToBeClickable(By.id("username")))
                .sendKeys(EMAIL);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submit")).click();

        // Check if signed-in successfully
        wait.until(urlContains("/home"));
    }
}
