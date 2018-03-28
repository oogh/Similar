package me.oogh.similar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oogh on 18-3-19.
 */

public class DateUtils {

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(date);
    }

    public static String format(long date) {
        Date _date = new Date(date);
        return format(_date);
    }

}
