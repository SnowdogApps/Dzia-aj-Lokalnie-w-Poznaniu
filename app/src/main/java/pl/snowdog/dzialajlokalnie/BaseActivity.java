package pl.snowdog.dzialajlokalnie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.EventClickedEvent;
import pl.snowdog.dzialajlokalnie.events.IssueClickedEvent;
import pl.snowdog.dzialajlokalnie.events.NetworkErrorEvent;
import pl.snowdog.dzialajlokalnie.events.ApiErrorEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.gcm.QuickstartPreferences;
import pl.snowdog.dzialajlokalnie.gcm.RegistrationIntentService;
import pl.snowdog.dzialajlokalnie.gcm.RegistrationIntentService_;
import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Login;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.User;
import pl.snowdog.dzialajlokalnie.util.PrefsUtil_;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
@EActivity//(R.layout.activity_main)
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @ViewById(R.id.main_content)
    protected View coordinatorLayout;

    @Pref
    PrefsUtil_ pref;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @AfterViews
    protected void afterBaseActivityViews() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d(TAG, "gcmdbg TOKEN sent");
                    InstanceID instanceID = InstanceID.getInstance(BaseActivity.this);
                    try {
                        String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                        pref.edit().pushRegId().put(token).apply();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "gcmdbg TOKEN NOT sent");
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService_.class);
            startService(intent);
        }

        //you can call there everything for all descendant activities that you normally call in onCreate method
        afterView();

    }

    protected abstract void afterView();



    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(NetworkErrorEvent event) {
        Log.d(TAG, "NetworkErrorEvent " + event);
        Snackbar.make(coordinatorLayout, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
    }

    public void onEvent(ApiErrorEvent event) {
        Log.d(TAG, "ApiErrorEvent " + event);

        switch (event.getStatus()) {
            case 401:
                showLoginSnackbar();
                break;
            case 403:
                Snackbar.make(coordinatorLayout, getString(R.string.forbidden_error), Snackbar.LENGTH_SHORT).show();
                break;
            case 404:
                Snackbar.make(coordinatorLayout, getString(R.string.not_found_error), Snackbar.LENGTH_SHORT).show();
                break;
            default:
                if (event.getStatus() >= 500) {
                    Snackbar.make(coordinatorLayout, getString(R.string.server_error), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(coordinatorLayout, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show();
                }
        }
    }

    protected void showLoginSnackbar() {
        Snackbar.make(coordinatorLayout, getString(R.string.unauthorized_error), Snackbar.LENGTH_LONG).
                setAction(R.string.login_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoginActivity_.intent(BaseActivity.this).start();
                        //AddUserActivity_.intent(BaseActivity.this).start();
                        Log.d(TAG, "login_action from snackbar ");
                    }
                }).show();
    }

    public void onEvent(IssueClickedEvent event) {
        Log.d(TAG, "onEvent " + event);
        startDetailsActivity(DlApi.ParentType.issues, event.getId());
    }

    public void onEvent(EventClickedEvent event) {
        Log.d(TAG, "onEvent " + event);
        startDetailsActivity(DlApi.ParentType.events, event.getId());
    }

    private void startDetailsActivity(DlApi.ParentType type, int id) {
        Intent intent = new Intent(this, DetailsActivity_.class);
        intent.putExtra("objType", type);
        intent.putExtra("objId", id);
        startActivity(intent);
    }

    protected void comment(DlApi.ParentType parentType, int parentID, int solution, String text) {
        int intParentType;

        switch (parentType){
            case issues:
                intParentType = 1;
                break;
            case events:
                intParentType = 2;
                break;
            default: //comments
                intParentType = 3;
                break;
        }

        DlApplication.commentApi.comment(new Comment(intParentType, parentID, solution, text), new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                Log.d(TAG, "comment success: " + comment);

                commentResult(comment);
                comment.save();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "comment failure: " + error);
            }
        });
    }

    protected void commentResult(Comment comment) { }

    protected void getCategories() {
        DlApplication.baseApi.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                Log.d(TAG, "getCategories success: " + categories);
                categoriesResult(categories);

                new Delete().from(Category.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (Category category : categories) {
                        category.save();
//                        Log.d(TAG, "getCategories success: " + category);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getCategories failure: " + error);
            }
        });
    }

    protected void categoriesResult(List<Category> categories) {
        // implement by override
    }

    protected void districtsResult(List<District> districts) {
        // implement by override
    }

    protected void getDistricts() {
        DlApplication.baseApi.getDistricts(new Callback<List<District>>() {
            @Override
            public void success(List<District> districts, Response response) {
                Log.d(TAG, "getDistricts success: " + districts);
                districtsResult(districts);
                new Delete().from(District.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (District district : districts) {
                        district.save();
//                        Log.d(TAG, "getDistricts success: " + district);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getDistricts failure: " + error);
            }
        });
    }

    protected void login(String username, String pass) {
        DlApplication.userApi.login(new Login(username, pass, 2), new Callback<Session>() {
            @Override
            public void success(Session session, Response response) {
                Log.d(TAG, "login success: " + session.toString());

                new Delete().from(Session.class).execute();
                try {
                    session.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                DlApplication.refreshCurrentSession();
                sendRegistrationToken();
                loginResult(session);
            }

            @Override
            public void failure(RetrofitError error) {
                loginResult(null);
                Log.d(TAG, "login failure: " + error);
            }
        });
    }

    @Background
    void sendRegistrationToken() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean sentToken = sharedPreferences
                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
        if (sentToken) {
            Log.d(TAG, "gcmdbg TOKEN sent");
            InstanceID instanceID = InstanceID.getInstance(BaseActivity.this);
            try {
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                pref.edit().pushRegId().put(token).apply();
                sendRegistrationToServer(token);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "gcmdbg TOKEN NOT sent");
        }
    }


    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        if(DlApplication.currentSession != null && DlApplication.currentSession.getSsid() != null) {
            NewUser newUser = new NewUser();
            newUser.setPushRegId(token);

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

    }

    @Nullable
    protected User getLoggedInUser() {
        User user = null;
        Log.d(TAG, "usrdbg user from session: "+DlApplication.currentSession.toString());
        try {
            user = new Select().from(User.class).where("userID = ?", DlApplication.currentSession.getUserID()).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    protected void getUserById(int userId) {
        DlApplication.userApi.getUserById(userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                user.save();
                userResult();
            }

            @Override
            public void failure(RetrofitError error) {


            }
        });
    }

    protected void userResult() {

    }

    protected void loginResult(Session session) {
        if(session != null) {
            Snackbar.make(coordinatorLayout, getString(R.string.login_success), Snackbar.LENGTH_LONG).show();
            DlApplication.createDlRestAdapter();
        }
    }

    protected boolean isLoggedIn() {
        return DlApplication.currentSession != null && DlApplication.currentSession.getSsid() != null;
    }

    protected void logout() {
        new Delete().from(Session.class).execute();
        DlApplication.currentSession = null;
        LoginManager.getInstance().logOut();
        Snackbar.make(coordinatorLayout, getString(R.string.logout_success), Snackbar.LENGTH_LONG).show();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
