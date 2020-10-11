package practice.independentTestingSuite.pages.components.navigation;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.interactions.Actions;
import org.selophane.elements.base.Element;

import java.util.List;

public class DropdownMenu extends PageComponent {
    @Find(css = "span > span")
    private Element menuName;
    @Find(tagName = "li")
    private List<Element> options;

    public void andClickOption(String optionName) {
        new Actions(getDriver()).moveToElement(getOptionByName(optionName)).click().build().perform();
    }

    private Element getOptionByName(String optionName) {
        for (Element option : options) {
            if (option.getText().equals(optionName)) {
                return option;
            }
        }
        return null;
    }

    public String getMenuName() {
        return menuName.getText();
    }

    private List<Element> getOptions() {
        return options;
    }
}
