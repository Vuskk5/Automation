package practice.independentTestingSuite.pages;

import practice.independentTestingSuite.pages.base.MasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Element;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SubmitRequirementsPage extends MasterPage {
    @FindBy (id = "wispformPlugin-buttonPopup")
    private Element submitRequirementsButton;

    public SubmitRequirementsPage(WebDriver driver) {
        super(driver);
    }

    public SubmitRequirementsScreen openSubmitRequirementsScreen() {
        wait.until(and(elementToBeClickable(submitRequirementsButton),
                        attributeToBeNotEmpty(submitRequirementsButton, "data-id")));
        submitRequirementsButton.click();

        return new SubmitRequirementsScreen(driver);
    }
}
