package practice.independentTestingSuite.pages.components.form.subform;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.selophane.elements.base.Element;

public class OptionComponent extends PageComponent {
    @Find(className = "UI_Choice-content-text")
    private Element text;
    @Find(className = "UI_Choice-index")
    private Element index;

    public String getText() {
        return this.text.getText().trim();
    }

    public String getIndex() {
        return this.index.getText().trim();
    }

    public Element getContext() {
        return super.getContext();
    }

    @Override
    public String toString() {
        return this.index.getText() + " - " + this.text.getText();
    }
}
