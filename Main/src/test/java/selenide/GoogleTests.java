package selenide;

import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

public class GoogleTests {
    static {
        WebDriverManager.config().setProxy("10.0.0.10:80");
        WebDriverManager.chromedriver().version("86");
    }

    private SelenideElement searchBox = $(By.name("q"));

    @Test
    public void searchTest() {
        open("https://www.google.com/");
        searchBox.val("Selenide").pressEnter();

        $$("#rso > div:not(.g-blk)").shouldHave(sizeGreaterThan(4));
    }
}
