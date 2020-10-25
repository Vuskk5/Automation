package asisimAdvanced.pages.login;

import asisimAdvanced.pages.general.MainPage;
import net.bsmch.PageObject;
import net.bsmch.findby.Find;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.TextBox;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class LoginPage extends PageObject {
    @Find(id = "username")
    private TextBox username;
    @Find(id = "password")
    private TextBox password;
    @Find(tagName = "button")
    private Element loginButton;

    public LoginPage() {
        super();
    }

    public MainPage loginAs(String username, String password) {
        this.username.set(username);
        this.password.set(password);
        this.loginButton.click();

        MainPage mainPage = page(MainPage.class);

        await("Page did not load in time").atMost(10, SECONDS)
                                          .until(mainPage::isAt);
        return mainPage;
    }

    @Override
    public boolean isAt() {
        return username.isEnabled() && password.isEnabled() && loginButton.isEnabled();
    }
}
