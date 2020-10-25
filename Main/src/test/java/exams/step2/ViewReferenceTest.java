package exams.step2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static exams.step2.BaseTest.driver;
import static exams.step2.BaseTest.wait;

public class ViewReferenceTest {
    @Test
    public void viewReferencesTest() {
        final String START_DATE  = "01/01/2005";
        final String END_DATE    = "31/12/2005";
        final String SOLDIER_ID  = "6334019";
        final String DOCTOR_NAME = "Ron Shushani";

        Random random = new Random();

        driver.findElement(By.linkText("הפניות")).click();
        // Could also be found with By.cssSelector(".expandable-panel > .panel-heading")
        driver.findElement(By.xpath("//*[contains(text(), \"חיפוש\")]")).click();

        wait.until(visibilityOfElementLocated(By.name("startDate")))
            .sendKeys(START_DATE);
        driver.findElement(By.name("endDate")).sendKeys(END_DATE);
        driver.findElement(By.name("soldierId")).sendKeys(SOLDIER_ID);

        wait.withMessage("There are no doctors to choose.")
            .until(numberOfElementsToBeMoreThan(By.cssSelector(".doctors-select > option"), 1));

        Select doctorList = new Select(driver.findElement(By.name("doctorId")));
        doctorList.selectByVisibleText(DOCTOR_NAME);

        int numberOfReferencesBeforeFiltering =
            wait.withMessage("Reference list is empty.")
                .until(numberOfElementsToBeMoreThan(By.cssSelector("tbody > tr"), 0))
                .size();

        driver.findElement(By.cssSelector("button.pull-left")).click();

        List<WebElement> tableRows =
            wait.withMessage("After filtering, the number of references did not decrease.")
                .until(numberOfElementsToBeLessThan(By.cssSelector("tbody > tr"), numberOfReferencesBeforeFiltering));

        int randomReferenceIndex = random.nextInt(tableRows.size() / 2) * 2;
        WebElement randomReference  = tableRows.get(randomReferenceIndex);
        WebElement referenceDetails = tableRows.get(randomReferenceIndex + 1).findElement(By.className("ref-details"));

        randomReference.findElement(By.className("show-details")).click();

        wait.withMessage("Reference details are not showing.")
            .until(visibilityOf(referenceDetails));
    }
}
