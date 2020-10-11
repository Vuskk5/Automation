package asisimAdvanced.components;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;

import java.time.Month;

import static java.lang.Integer.*;

public class DatePicker extends PageComponent {
    @Find(attribute = "data-handler", value = "next")
    private Element nextMonth;

    @Find(attribute = "data-handler", value = "prev")
    private Element previousMonth;

    private By yearLocator = By.className("ui-datepicker-year");
    private By monthLocator = By.className("ui-datepicker-month");

    public DatePicker selectYear(String year) {
        return selectYear(parseInt(year));
    }

    public DatePicker selectYear(int year) {
        int currentYear = parseInt($(yearLocator).getText());

        if (currentYear != year) {
            WebElement move = (currentYear < year) ? nextMonth : previousMonth;

            int monthDifference = 12 * Math.abs(currentYear - year);

            for (int i = 0; i < monthDifference; i++) {
                move.click();
            }
        }

        return this;
    }

    public DatePicker selectMonth(String monthName) {
        // Get 1-based month index
        int monthIndex = Month.valueOf(monthName.toUpperCase()).getValue();
        return selectMonth(monthIndex);
    }

    public DatePicker selectMonth(int month) {
        int currentMonth = Month.valueOf($(monthLocator).getText().toUpperCase()).getValue();

        if (currentMonth != month) {
            Element move = (currentMonth < month) ? nextMonth : previousMonth;

            for (int i = 0; i < Math.abs(currentMonth - month); i++) {
                move.click();
            }
        }

        return this;
    }

    public DatePicker selectDay(String day) {
        return selectDay(parseInt(day));
    }

    public DatePicker selectDay(int day) {
        $("//a[text() = \"" + day + "\"]").click();

        return this;
    }
}
