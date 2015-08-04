package pl.snowdog.dzialajlokalnie;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;

import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment_;
import pl.snowdog.dzialajlokalnie.fragment.SettingsFragment;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-08-04.
 */
@PreferenceScreen(R.xml.preferences)
@EActivity
public class SettingsActivity extends PreferenceActivity {
    private static final String TAG = "SettingsActivity";


}