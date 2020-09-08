package asisimAdvanced.pages.general;

import asisimAdvanced.components.SweetAlert;
import net.bsmch.PageObject;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;

public class MainPage extends PageObject {
    @Find(className = "sweet-alert")
    protected SweetAlert alert;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public SweetAlert alert() {
        return alert;
    }
}
