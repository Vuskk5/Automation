package practice.independentTestingSuite.support.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.service.ExtentTestManager;
import net.bsmch.Screenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.Select;

import static com.aventstack.extentreports.markuputils.MarkupHelper.createLabel;
import static com.aventstack.extentreports.markuputils.ExtentColor.*;

import java.util.Arrays;

public class WebDriverListener extends AbstractWebDriverEventListener {
    private static final String SELECTED = createLabel("Selected", PURPLE).getMarkup();
    private static final String CLICK = createLabel("Clicked", GREEN).getMarkup();
    private static final String TYPE = createLabel("Type", CYAN).getMarkup();

    private static ThreadLocal<MediaEntityModelProvider> screenshot = new ThreadLocal<>();

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        screenshot.set(Screenshot.captureElementBase64(element));
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        ExtentTest test = ExtentTestManager.getTest();
        test.info(CLICK, screenshot.get());
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        ExtentTest test = ExtentTestManager.getTest();
        if (keysToSend != null) {
            test.info(TYPE + " " + Arrays.toString(keysToSend) + " into [" + getIdentifier(element) + "]",
                        Screenshot.captureElementBase64(element));
        }
        else {
            // Cleared element
        }
    }

    private String getIdentifier(WebElement element) {
        if (!"".equals(element.getText())) {
            return element.getText();
        }
        else if (!"".equals(element.getAttribute("id"))) {
            return element.getAttribute("id");
        }
        else if (!"".equals(element.getAttribute("name"))) {
            return element.getAttribute("name");
        }
        else if (!"".equals(element.getAttribute("placeholder"))) {
            return element.getAttribute("placeholder");
        }
        else {
            return "";
        }
    }
}
