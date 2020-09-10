package asisimAdvanced.pages.login;

import asisimAdvanced.pages.general.MainPage;
import net.bsmch.PageObject;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.TextBox;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LoginPage extends PageObject {
    @Find(id = "username")
    private TextBox username;
    @Find(id = "password")
    private TextBox password;
    @Find(tagName = "button")
    private Element loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public MainPage loginAs(String username, String password) {
        this.username.set(username);
        this.password.set(password);
        loginButton.click();

        getWait().until(visibilityOfElementLocated(By.id("navbar")));

        return page(MainPage.class);
    }

    public boolean isAt() {
        return username.isEnabled() && password.isEnabled() && loginButton.isEnabled();
    }
}
