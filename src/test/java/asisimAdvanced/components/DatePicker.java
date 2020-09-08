package asisimAdvanced.components;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;

import java.time.Month;
import java.util.stream.IntStream;

public class DatePicker extends PageComponent {
    @Find(attribute = "data-handler", value = "next")
    private Element next;

    @Find(attribute = "data-handler", value = "prev")
    private Element prev;

    private By yearLocator = By.className("ui-datepicker-year");
    private By monthLocator = By.className("ui-datepicker-month");

    public DatePicker selectYear(String year) {
        return selectYear(Integer.parseInt(year));
    }

    public DatePicker selectYear(int year) {
        int currentYear = Integer.parseInt(getContext().findElement(yearLocator).getText());

        if (currentYear != year) {
            WebElement move = (currentYear < year) ? next : prev;

            IntStream.rangeClosed(1, 12 * (currentYear - year))
                     .forEach(i -> move.click());
        }

        return this;
    }

    public DatePicker selectMonth(String month) {
        int numericMonth = Month.valueOf(month.toUpperCase()).getValue();
        return selectMonth(numericMonth);
    }

    public DatePicker selectMonth(int month) {
        int currentMonth = Month.valueOf(getContext().findElement(monthLocator).getText().toUpperCase()).getValue();

        if (currentMonth != month) {
            WebElement move = (currentMonth < month) ? next : prev;

            IntStream.rangeClosed(1, Math.abs(currentMonth - month))
                     .forEach(i -> move.click());
        }

        return this;
    }

    public DatePicker selectDay(String day) {
        return selectDay(Integer.parseInt(day));
    }

    public DatePicker selectDay(int day) {
        getContext().findElement(By.xpath("//a[text() = \"" + day + "\"]")).click();

        return this;
    }
}
