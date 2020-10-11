package practice.independentTestingSuite.pages.components.form;

import practice.independentTestingSuite.pages.components.form.subform.OptionComponent;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MultiChoiceComponent extends FormComponent {
    @FindBy (className = "UI_MultiChoices-Choice")
    private List<OptionComponent> options;
    @FindBy (tagName = "input")
    private List<WebElement> customValue;

    public WebElement getOptionByText(String text) {
        for (OptionComponent option : options) {
            if (option.getText().equals(text)) {
                return option.getContext();
            }
        }

        throw new InvalidArgumentException("No option with text \"" + text + "\"");
    }

    public WebElement getOptionByChar(Character index) {
        for (OptionComponent option : options) {
            if (option.getIndex().equals(index.toString())) {
                return option.getContext();
            }
        }

        throw new InvalidArgumentException("No option with text \"" + index + "\"");
    }
}
