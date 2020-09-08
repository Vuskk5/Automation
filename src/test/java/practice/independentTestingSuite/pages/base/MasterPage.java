package practice.independentTestingSuite.pages.base;

import practice.independentTestingSuite.pages.components.navigation.NavigationBar;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;

public class MasterPage extends PageObject {
    @Find(id = "main-menu")
    private NavigationBar navBar;

    protected MasterPage(WebDriver driver) {
        super(driver);

        ComponentFactory.initComponents(driver, this);
    }

    public NavigationBar navBar() {
        return this.navBar;
    }
}
