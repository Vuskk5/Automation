package practice.independentTestingSuite.pages;

import practice.independentTestingSuite.pages.base.PageObject;
import practice.independentTestingSuite.pages.components.form.MultiChoiceComponent;
import practice.independentTestingSuite.pages.components.form.TextComponent;
import practice.independentTestingSuite.pages.components.form.YesNoComponent;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SubmitRequirementsScreen extends PageObject {
    @Find(xpathText = "Let's Go!")
    private Element letsGo;
    @Find(id = "wispform1")
    private TextComponent email;
    @Find(id = "wispform2")
    private TextComponent whatsAppContact;
    @Find(id = "wispform3")
    private TextComponent nameOrBusinessName;
    @Find(id = "wispform4")
    private MultiChoiceComponent offeredServices;
    @Find(id = "wispform5")
    private MultiChoiceComponent requestedPlatforms;
    @Find(id = "wispform6")
    private YesNoComponent alreadyHasAWebSite;
    @Find(className = "Form-nextbutton-button")
    private Element submitChoices;
    @Find(className = "Submit-Button")
    private Element submitForm;
    @Find(className = "CompletePage-Thumb")
    private Element pageCompletionIcon;

    public SubmitRequirementsScreen(WebDriver driver) {
        super(driver);

        wait.withTimeout(Duration.ofSeconds(10))
            .until(frameToBeAvailableAndSwitchToIt(By.className("wispformPlugin-iframe")));

        wait.withTimeout(Duration.ofSeconds(15))
            .until(visibilityOf(letsGo));

        ComponentFactory.initComponents(driver, this);
    }

    public void startForm() {
        wait.until(elementToBeClickable(letsGo)).click();
    }

    public void fillEmail(String email) {
        waitAndSubmitTextBox(this.email, email);
    }

    public void fillContactNumber(String whatsAppContact) {
        waitAndSubmitTextBox(this.whatsAppContact, whatsAppContact);
    }

    public void fillName(String name) {
        waitAndSubmitTextBox(nameOrBusinessName, name);
    }

    public void chooseOfferedServices(String... offeredServicesText) {
        waitAndChooseOptionsInForm(this.offeredServices, offeredServicesText);
    }

    public void chooseOfferedServices(Character... servicesAlphabetIndexes) {
        waitAndChooseOptionsInForm(this.offeredServices, servicesAlphabetIndexes);
    }

    public void chooseRequestedPlatforms(String... requestedPlatformsText) {
        waitAndChooseOptionsInForm(this.requestedPlatforms, requestedPlatformsText);
    }

    public void chooseRequestedPlatforms(Character... platformsAlphabetIndexes) {
        waitAndChooseOptionsInForm(this.requestedPlatforms, platformsAlphabetIndexes);
    }

    public void checkAlreadyHaveWebSite(boolean alreadyHasAWebSite) {
        this.alreadyHasAWebSite.waitUntilActive();

        if (alreadyHasAWebSite) {
            this.alreadyHasAWebSite.checkYes();
        }
        else {
            this.alreadyHasAWebSite.checkNo();
        }
    }

    public void submitForm() {
        moveToElement(submitForm).click();
    }

    private void waitAndSubmitTextBox(TextComponent form, String value) {
        form.waitUntilActive();
        form.textBox().set(value);
        form.submitOk().click();
    }

    private void waitAndChooseOptionsInForm(MultiChoiceComponent multiChoice, String... optionsText) {
        multiChoice.waitUntilActive();

        for (String optionText : optionsText) {
            moveToElement(multiChoice.getOptionByText(optionText)).click();
        }

        wait.until(elementToBeClickable(submitChoices)).click();
    }

    private void waitAndChooseOptionsInForm(MultiChoiceComponent multiChoice, Character... alphabetIndexes) {
        multiChoice.waitUntilActive();

        for (Character option : alphabetIndexes) {
            moveToElement(multiChoice.getOptionByChar(option)).click();
        }

        wait.until(elementToBeClickable(submitChoices)).click();
    }

    public Element getPageCompletionIcon() {
        return pageCompletionIcon;
    }
}
