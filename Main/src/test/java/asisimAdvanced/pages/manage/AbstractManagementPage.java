package asisimAdvanced.pages.manage;

import asisimAdvanced.pages.general.MainPage;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Table;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class AbstractManagementPage<T extends AbstractManagementPage<T, X>, X> extends MainPage {
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

    public abstract void add(X object);
    public abstract void delete(X object);
    public abstract void edit(X object);
    public abstract void select(X object);

    protected abstract T getThis();

    public T clickNew() {
        newButton.click();
        driverWait().until(visibilityOfElementLocated(By.id("new")));
        return getThis();
    }

    public T clickDelete() {
        deleteButton.click();
        return getThis();
    }

    public T clickUpdate() {
        updateButton.click();
        return getThis();
    }

    public Table table() {
        return table;
    }
}
