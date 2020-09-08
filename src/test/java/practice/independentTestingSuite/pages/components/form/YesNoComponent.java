package practice.independentTestingSuite.pages.components.form;

import net.bsmch.findby.Find;
import net.bsmch.findby.Finds;
import org.selophane.elements.base.Element;

public class YesNoComponent extends FormComponent {
    @Finds({
            @Find(className = "UI_YesNo-InteractableOption"),
            @Find(xpath = "descendant::*[string()=\"Yes\"]")
    })
    private Element yes;

    @Finds({
            @Find(className = "UI_YesNo-InteractableOption"),
            @Find(xpath = "descendant::*[string()=\"No\"]")
    })
    private Element no;

    public void checkYes() {
        yes.click();
    }

    public void checkNo() {
        no.click();
    }
}
