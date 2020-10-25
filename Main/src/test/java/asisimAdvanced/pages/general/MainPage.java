package asisimAdvanced.pages.general;

import asisimAdvanced.components.SweetAlert;
import net.bsmch.PageObject;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;

public class MainPage extends PageObject {
    @Find(className = "sweet-alert")
    protected SweetAlert alert;
    @Find(className = "navbar-brand")
    protected Element navbar;

    public MainPage() {
        super();
    }

    @Override
    public boolean isAt() {
        return navbar.isDisplayed();
    }

    public SweetAlert alert() {
        return alert;
    }
}
