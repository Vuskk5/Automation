package practice.independentTestingSuite.tests;

import practice.independentTestingSuite.pages.LandingPage;
import practice.independentTestingSuite.pages.SubmitRequirementsScreen;
import practice.independentTestingSuite.tests.base.SterileTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class SubmitRequirementsTest extends SterileTest {
    private LandingPage mainPage;
    private SubmitRequirementsScreen submitScreen;

    @BeforeMethod
    public void setUp() {
        mainPage = openSystem();
        mainPage.navBar().hoverOnMenu("Features").andClickOption("Hotels Module");
    }

    @Test(  description = "Validate that the form works with valid input.",
            groups = {"author:Kfir Doron", "tag:Sanity", "device:Windows Computer"})
    public void submitValidRequirements() {
        submitScreen = mainPage .clickOnSubmitRequirements()
                                .openSubmitRequirementsScreen();
        submitScreen.startForm();
        submitScreen.fillEmail("vuskk5@gmail.com");
        submitScreen.fillContactNumber("0549576063");
        submitScreen.fillName("Kfir Doron");
        submitScreen.chooseOfferedServices("Hotel Rooms", "Cruises or Boats");
        submitScreen.chooseRequestedPlatforms('A', 'C', 'E');
        submitScreen.checkAlreadyHaveWebSite(false);
        submitScreen.submitForm();

        driverWait().withMessage("Form was not submitted.")
                .until(visibilityOf(submitScreen.getPageCompletionIcon()));
    }
}
