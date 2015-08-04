package pl.snowdog.dzialajlokalnie.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-08-04.
 */
@PreferenceScreen(R.xml.preferences)
@EFragment
public class SettingsFragment extends PreferenceFragment {
    private static final String TAG = "SettingsFragment";
    
    @PreferenceByKey(R.string.pref_key_push_notifications)
    CheckBoxPreference prefPushNotifications;

    @PreferenceByKey(R.string.pref_key_email_notifications)
    CheckBoxPreference prefEmailNotifications;

    User loggedInUser;
    private ApiActionDialogFragment mApiActionDialogFragment;

    @PreferenceChange(R.string.pref_key_push_notifications)
    void checkedChangedPushNotifications(boolean newValue, Preference preference) {
        loggedInUser.setEnablePushNotifications(newValue ? 1 : 0);
        loggedInUser.save();
        Log.d(TAG, "prfdbg Push clicked");
    }

    @PreferenceChange(R.string.pref_key_email_notifications)
    void checkedChangedEmailNotifications(boolean newValue, Preference preference) {
        loggedInUser.setEnableEmailNotifications(newValue ? 1 : 0);
        loggedInUser.save();
        Log.d(TAG, "prfdbg Email clicked");
    }

    void sendChangesToApi() {
        toggleProgressWheel(true);
        NewUser newUser = new NewUser();
        newUser.setEnableEmailNotifications(loggedInUser.getEnableEmailNotifications());
        newUser.setEnablePushNotifications(loggedInUser.getEnablePushNotifications());

        DlApplication.userApi.putUser(newUser, DlApplication.currentSession.getUserID(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(TAG, "userApi.sendRegistrationToServer post success: " + response + " user: " + user.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "userApi.postNewUser post error: " + error);

            }
        });
    }

    protected void toggleProgressWheel(boolean show) {
        if(show) {
            if(mApiActionDialogFragment == null) {
                mApiActionDialogFragment = ApiActionDialogFragment_.builder().build();
            }
            mApiActionDialogFragment.show(getSupportFragmentManager(), ApiActionDialogFragment.TAG);
        } else {
            mApiActionDialogFragment.dismiss();
        }
    }

    @AfterPreferences
    void initPrefs() {
        if(!isLoggedIn()) {
            prefPushNotifications.setEnabled(false);
            prefEmailNotifications.setEnabled(false);
        } else {
            loggedInUser = getLoggedInUser();
            prefPushNotifications.setChecked(loggedInUser.getEnableEmailNotifications() == 1 ? true : false);
            prefEmailNotifications.setChecked(loggedInUser.getEnablePushNotifications() == 1 ? true : false);
        }

    }

    protected boolean isLoggedIn() {
        return DlApplication.currentSession != null && DlApplication.currentSession.getSsid() != null;
    }

    @Nullable
    protected User getLoggedInUser() {
        User user = null;
        try {
            user = new Select().from(User.class).where("userID = ?", DlApplication.currentSession.getUserID()).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}