package com.denisanfossi.mareu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT_PICKERS_DISPLAY =
            new SimpleDateFormat("dd/MM/yy HH:mm", Locale.FRANCE);

    private static final SimpleDateFormat DATE_FORMAT_RECYCLER_DISPLAY =
            new SimpleDateFormat("dd MMMM HH:mm", Locale.FRANCE);

    public static String convertDateForPickersDisplay(Date date) {
        return DATE_FORMAT_PICKERS_DISPLAY.format(date);
    }

    public static String convertDateForRecyclerDisplay(Date date) {
        return DATE_FORMAT_RECYCLER_DISPLAY.format(date);
    }

}
