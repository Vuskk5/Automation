package asisimAdvanced.support;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return new Date(calendar.getTimeInMillis());
    }

    public static String toString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return year + month + day;
    }
}
