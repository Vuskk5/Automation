package practice.asisim.tests.permission;

import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import practice.asisim.tests.BaseTest;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {
    @Test
    public void loginAsDeveloper() {
        openSystem();
        loginAs("developer", "developer");

        Cookie playSession = getDriver().manage().getCookieNamed("PLAY_SESSION");
        String permissionLevel = playSession.getValue().split("&")[2].split("=")[1];
        Assert.assertEquals(permissionLevel, "6", "User does not have valid permissions.");
    }
}
