package practice.independentTestingSuite.pages.components.form;

import net.bsmch.components.PageComponent;
import net.bsmch.elementwait.ElementConditions;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class FormComponent extends PageComponent {

    public FormComponent() {
        super();
    }

    public FormComponent(WebElement element) {
        super(element);

        String currentWindow = getDriver().getWindowHandle();
        Set<String> handles = getDriver().getWindowHandles();

        for (String childWindow : handles) {
            if (!currentWindow.equals(childWindow)) {
                String currentWindowName = getDriver().getTitle();
                getDriver().switchTo().window(childWindow);
                System.out.println("Switched from " + currentWindowName + " to " + getDriver().getTitle());
                break;
            }
        }

        // Start work on the new window

        // End work on new window - switch to previous window
        getDriver().close();
        getDriver().switchTo().window(currentWindow);
    }

    public void waitUntilActive() {
        contextWait().until(ElementConditions.attributeContains(getContext(), "class", "active"));
    }
}
