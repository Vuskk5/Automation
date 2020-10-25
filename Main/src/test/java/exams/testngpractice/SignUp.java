package exams.testngpractice;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static exams.testngpractice.BaseTest.driver;
import static exams.testngpractice.BaseTest.wait;

public class SignUp {
   static final String EMAIL = "email" + ((int) (Math.random() * 999)) + "@mail.com";
   static final String PASSWORD = "A12345678a";

   @BeforeClass
   public void enterPage() {
      wait.until(elementToBeClickable
              (By.cssSelector("p a"))).click();
   }

   @Test
   public void signUp() {
      final String NAME = "NAME";
      final String LAST_NAME = "LAST NAME";
      final String BIRTH_DATE = "12/03/2001";
      final String SECURITY_NUMBER = "111-22-" + ((int) (Math.random() * 9999) + 1000) ;
      final String ADDRESS = "ADDRESS";
      final String LOCALITY = "CITY";
      final String REGION = "REGION";
      final String POSTAL_CODE = "12345";
      final String COUNTRY = "COUNTRY";
      final String HOME_PHONE = "(546) 234-5678";
      final String MOBILE_PHONE = "(556) 234-5678";
      final String WORK_PHONE = "(556) 234-5678";

      // Enter info
      wait.until(elementToBeClickable(By.cssSelector("select")));
      new Select(driver.findElement(By.tagName("select"))).selectByValue("Ms.");
      driver.findElement(By.id("firstName")).sendKeys(NAME);
      driver.findElement(By.id("lastName")).sendKeys(LAST_NAME);
      driver.findElement(By.cssSelector("[value='M']")).click();
      driver.findElement(By.id("dob")).sendKeys(BIRTH_DATE);
      driver.findElement(By.id("ssn")).sendKeys(SECURITY_NUMBER);
      driver.findElement(By.id("emailAddress")).sendKeys(EMAIL);
      driver.findElement(By.id("password")).sendKeys(PASSWORD);
      driver.findElement(By.id("confirmPassword")).sendKeys(PASSWORD);

      // Go to the next page
      driver.findElement(By.tagName("button")).click();

      // Enter info
      wait.until(elementToBeClickable(By.id("address")))
              .sendKeys(ADDRESS);
      driver.findElement(By.id("locality")).sendKeys(LOCALITY);
      driver.findElement(By.id("region")).sendKeys(REGION);
      driver.findElement(By.id("postalCode")).sendKeys(POSTAL_CODE);
      driver.findElement(By.id("country")).sendKeys(COUNTRY);
      driver.findElement(By.id("homePhone")).sendKeys(HOME_PHONE);
      driver.findElement(By.id("mobilePhone")).sendKeys(MOBILE_PHONE);
      driver.findElement(By.id("workPhone")).sendKeys(WORK_PHONE);

      // Agree to terms
      driver.findElement(By.id("agree-terms")).click();

      // Register
      driver.findElement(By.tagName("button")).click();

      // Check registration status
      wait.until(visibilityOfElementLocated
              (By.className("alert-success")));
   }
}
