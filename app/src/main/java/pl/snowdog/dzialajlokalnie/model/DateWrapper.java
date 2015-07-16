package pl.snowdog.dzialajlokalnie.model;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Helper class to wrap Date around Serializable to pass it with FragmentArg to fragment builder
 * Created by chomi3 on 2015-07-15.
 */
public class DateWrapper implements Serializable {
    Date date;

    public DateWrapper(Date date) {
        this.date = date;
    }

    public DateWrapper() {

    }

    public String getDateString(Context context) {
        final SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.simple_date_format));
        return formatter.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
