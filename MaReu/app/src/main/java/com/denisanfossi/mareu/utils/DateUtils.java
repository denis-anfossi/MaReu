package com.denisanfossi.mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat DATE_TIME_FORMAT_PICKERS_DISPLAY =
            new SimpleDateFormat("dd/MM/yy HH:mm", Locale.FRANCE);

    private static final SimpleDateFormat DATE_FORMAT_PICKERS_DISPLAY =
            new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

    private static final SimpleDateFormat DATE_FORMAT_RECYCLER_DISPLAY =
            new SimpleDateFormat("dd MMMM HH:mm", Locale.FRANCE);

    public static String convertDateForPickersDisplay(Date date) {
        return DATE_TIME_FORMAT_PICKERS_DISPLAY.format(date);
    }

    public static Date convertStringToDateForPickersDisplay(String input) {
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
            return DATE_TIME_FORMAT_PICKERS_DISPLAY.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertDateForRecyclerDisplay(Date date) {
        return DATE_FORMAT_RECYCLER_DISPLAY.format(date);
    }

}
