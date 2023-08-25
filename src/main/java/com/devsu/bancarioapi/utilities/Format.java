package com.devsu.bancarioapi.utilities;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Format {

    /**
     * Formats a date using the specified pattern.
     *
     * @param date
     * @param pattern
     * @return
     */
    public String dateFormat(Timestamp date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    /**
     * Formats a double number into a localized string representation using the
     * default number format.
     * 
     * @param number
     * @return
     */
    public String numberFormat(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
}
