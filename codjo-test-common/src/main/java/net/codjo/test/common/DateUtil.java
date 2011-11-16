package net.codjo.test.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 */
public final class DateUtil {
    public static final String FORMAT = "yyyy-MM-dd";


    private DateUtil() {
    }


    public static Date createDate(String dateAsString) throws ParseException {
        return new SimpleDateFormat(FORMAT).parse(dateAsString);
    }


    public static String createDateString(Date date) {
        return new SimpleDateFormat(FORMAT).format(date);
    }
}
