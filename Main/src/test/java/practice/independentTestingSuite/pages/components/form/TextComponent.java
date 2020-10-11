package practice.independentTestingSuite.pages.components.form;

import net.bsmch.findby.Find;
import org.openqa.selenium.WebElement;
import org.selophane.elements.widget.TextBox;

public class TextComponent extends FormComponent {
    @Find(tagName = "input")
    private TextBox textBox;
    @Find(className = "TextSubmitButton-OK")
    private WebElement submitOk;

    public TextBox textBox() {
        return textBox;
    }

    public WebElement submitOk() {
        return submitOk;
    }
}
