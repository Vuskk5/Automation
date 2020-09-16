package asisimAdvanced.pages.manage.soldiers;

import asisimAdvanced.support.DateUtil;
import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextBox;

import java.util.Date;

public class NewSoldierModal extends PageComponent {
    private TextBox newId;
    private TextBox newName;
    private Select newClinicId;
    private TextBox newDraftDate;
    private TextBox newReleaseDate;
    private Select newRankId;
    @Find(attribute = "onclick", value = "create()")
    private Element createButton;

    public NewSoldierModal setId(Long id) {
        newId.set(id.toString());
        return this;
    }

    public NewSoldierModal setName(String name) {
        newName.set(name);
        return this;
    }

    public NewSoldierModal selectClinic(String clinicName) {
        newClinicId.selectByVisibleText(clinicName);
        return this;
    }

    public NewSoldierModal setDraftDate(Date draftDate) {
        newDraftDate.set(DateUtil.dateString(draftDate));
        return this;
    }

    public NewSoldierModal setReleaseDate(Date releaseDate) {
        newReleaseDate.set(DateUtil.dateString(releaseDate));
        return this;
    }

    public NewSoldierModal selectRank(String rank) {
        newRankId.selectByVisibleText(rank);
        return this;
    }

    public void create() {
        createButton.click();
    }
}
