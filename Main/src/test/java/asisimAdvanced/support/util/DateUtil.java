package asisimAdvanced.support.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int get(int field, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    public static Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return new Date(calendar.getTimeInMillis());
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String dateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM:dd:yyyy");
        return format.format(date);
    }

    public static String timeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        return format.format(date);
    }
}
