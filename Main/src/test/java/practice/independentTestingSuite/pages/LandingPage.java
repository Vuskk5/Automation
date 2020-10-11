package practice.independentTestingSuite.pages;

import practice.independentTestingSuite.pages.base.MasterPage;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LandingPage extends MasterPage {
    @Find(xpathString = "Submit Requirements", tagName = "a", checkContains = true)
    private Element submitRequirements;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public SubmitRequirementsPage clickOnSubmitRequirements() {
        moveToElement(wait.until(visibilityOf(submitRequirements))).click();

        return new SubmitRequirementsPage(driver);
    }
}
