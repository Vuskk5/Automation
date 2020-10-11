package practice.asisim.pages.permission;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import practice.asisim.pages.PageObject;

public class LoginPage extends PageObject {
    @FindBy(id = "username")
    private WebElement usernameBox;
    @FindBy(id = "password")
    private WebElement passwordBox;
    @FindBy(className = "login-button")
    private WebElement loginButton;
    @FindBy(id = "navbar")
    private WebElement navbar;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginAs(String username, String password) {
        usernameBox.sendKeys(username);
        passwordBox.sendKeys(password);
        loginButton.click();

        wait.until(ExpectedConditions.visibilityOf(navbar));
    }
}
