package practice.serenityTest.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class SearchBar extends PageObject {
    @FindBy(xpath = "//*[text() = \"תאריך יציאה\"]/parent::div//input")
    WebElementFacade leaveDate;

    @FindBy(xpath = "//*[text() = \"תאריך חזרה\"]/parent::div//input")
    WebElementFacade returnDate;

    public void selectOneWayFlightType() {
        $("//*[contains(text(), \"כיוון אחד\")]/parent::div").click();
    }

    public void selectReturnFlightType() {
        $("//*[contains(text(), \"הלוך - חזור\")]/parent::div").click();
    }


}
