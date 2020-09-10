package asisimAdvanced.pages.soldiertreatment.fragment;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.TextBox;

public class SoldierDetails extends PageComponent {
    @Find(id = "soldierId")
    private TextBox soldierId;
    @Find(tagName = "button")
    private Element selectSoldier;
    @Find(id = "soldierRank")
    private TextBox soldierRank;
    @Find(id = "soldierName")
    private TextBox soldierName;

    public SoldierDetails setId(String soldierId) {
        this.soldierId.set(soldierId);
        return this;
    }

    public SoldierDetails selectSoldier() {
        selectSoldier.click();
        return this;
    }

    public String rank() {
        return soldierRank.getAttribute("value");
    }

    public String name() {
        return soldierName.getAttribute("value");
    }

    public boolean isLoaded() {
        return !soldierRank.value().equals("") && !soldierName.value().equals("");
    }
}
