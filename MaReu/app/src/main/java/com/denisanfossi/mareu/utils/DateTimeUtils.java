package com.denisanfossi.mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final SimpleDateFormat DATE_TIME_FORMAT_PICKERS_DISPLAY =
            new SimpleDateFormat("dd/MM/yy HH:mm", Locale.FRANCE);

    private static final SimpleDateFormat DATE_FORMAT_PICKERS_DISPLAY =
            new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

    private static final SimpleDateFormat DATE_FORMAT_RECYCLER_DISPLAY =
            new SimpleDateFormat("dd MMMM HH:mm", Locale.FRANCE);

    public static Date convertStringToDateTimeForPickersDisplay(String input) {
        try {
            return DATE_TIME_FORMAT_PICKERS_DISPLAY.parse(input);
        } catch (ParseException e) {
            try {
                return DATE_FORMAT_PICKERS_DISPLAY.parse(input);
            } catch (ParseException x) {
                return null;
            }
        }
    }

    public static Date convertStringToDateForSave(String input) {
        try {
            return DATE_FORMAT_PICKERS_DISPLAY.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date convertStringToDateTimeForSave(String input) {
        try {
            return DATE_TIME_FORMAT_PICKERS_DISPLAY.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertDateTimeForPickersDisplay(Date date) {
        return DATE_TIME_FORMAT_PICKERS_DISPLAY.format(date);
    }

    public static String convertDateForPickersDisplay(Date date) {
        return DATE_FORMAT_PICKERS_DISPLAY.format(date);
    }

    public static String convertDateForRecyclerDisplay(Date date) {
        return DATE_FORMAT_RECYCLER_DISPLAY.format(date);
    }

    public static String getStringForDateFilterDisplay(Date filterDate) {
        if (filterDate.getTime() == -3600000) {
            return null;
        } else {
            return DateTimeUtils.convertDateForPickersDisplay(filterDate);
        }
    }

    public static Date setTimeToDate(Date date, int hourOfDay, int minute, int second, boolean future) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.clear();
            calendar.setTime(date);
        }
        if (future)
            calendar.set(calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
        else
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, second);
        calendar.clear(Calendar.MILLISECOND);
        return new Date(calendar.getTimeInMillis());
    }
}
