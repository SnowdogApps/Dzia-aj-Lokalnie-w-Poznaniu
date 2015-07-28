package pl.snowdog.dzialajlokalnie.util;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by chomi3 on 2015-07-04.
 */
@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PrefsUtil {
    float lastLat();
    float lastLon();
}
