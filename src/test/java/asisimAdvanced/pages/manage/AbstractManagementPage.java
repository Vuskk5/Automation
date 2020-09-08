package asisimAdvanced.pages.manage;

import asisimAdvanced.pages.general.MainPage;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Table;

public abstract class AbstractManagementPage<T> extends MainPage {
    @Find(className = "table-hover")
    private Table table;
    @Find(attribute = "data-target", value = "#new")
    private Element newButton;
    @Find(attribute = "onclick", value = "destroy()")
    private Element deleteButton;
    @Find(attribute = "onclick", value = "update()")
    private Element updateButton;

    protected AbstractManagementPage(WebDriver driver) {
        super(driver);
    }

    public abstract void add(T object);
    public abstract void delete(T object);
    public abstract void edit(T object);
    public abstract void select(T object);

    public AbstractManagementPage<T> clickNew() {
        newButton.click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("new")));
        return this;
    }

    public AbstractManagementPage<T> clickDelete() {
        deleteButton.click();
        return this;
    }

    public AbstractManagementPage<T> clickUpdate() {
        updateButton.click();
        return this;
    }

    public Table table() {
        return table;
    }
}
