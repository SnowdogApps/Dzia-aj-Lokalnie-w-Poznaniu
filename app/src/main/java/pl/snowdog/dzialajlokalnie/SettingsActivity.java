package pl.snowdog.dzialajlokalnie;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;

import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment_;
import pl.snowdog.dzialajlokalnie.fragment.SettingsFragment;
import pl.snowdog.dzialajlokalnie.fragment.SettingsFragment_;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-08-04.
 */
@EActivity(R.layout.activity_settings)
public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";

    @Override
    protected void afterView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.action_settings);

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new SettingsFragment_())
                .commit();
    }

    @OptionsItem(android.R.id.home)
    void homeSelected() {
        this.finish();
    }
}