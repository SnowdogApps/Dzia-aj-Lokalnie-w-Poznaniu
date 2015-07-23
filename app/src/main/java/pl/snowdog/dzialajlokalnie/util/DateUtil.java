package pl.snowdog.dzialajlokalnie.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bartek on 23.07.15.
 */
public class DateUtil {

    public static String date2String(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(date);
    }
}
