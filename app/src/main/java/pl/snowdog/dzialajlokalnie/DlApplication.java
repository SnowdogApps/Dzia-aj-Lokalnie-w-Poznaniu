package pl.snowdog.dzialajlokalnie;

import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Select;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Modifier;

import io.fabric.sdk.android.Fabric;
import pl.snowdog.dzialajlokalnie.api.CityApi;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.api.GlobalErrorHandler;
import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Filter;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Point;
import pl.snowdog.dzialajlokalnie.model.Polygon;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.User;
import pl.snowdog.dzialajlokalnie.util.PrefsUtil_;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bartek on 06.07.15.
 */
@EApplication
public class DlApplication extends MultiDexApplication implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "DlApplication";
    public static RestAdapter restAdapter;
    public static RestAdapter restCityAdapter;
    public static DlApi.Base baseApi;
    public static DlApi.IssueApi issueApi;
    public static DlApi.EventApi eventApi;
    public static DlApi.VoteApi voteApi;
    public static DlApi.UserApi userApi;
    public static DlApi.CommentApi commentApi;
    public static CityApi.PoznanApi poznanApi;

    public static Session currentSession;
    public static Filter filter;

    static Gson gson;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;


    @Pref
    PrefsUtil_ pref;

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        buildGoogleApiClient();
        try {
            Fabric.with(this, new Crashlytics());
        } catch (java.lang.VerifyError e) {
            Log.e(TAG, "" + e);
        }

//        ActiveAndroid.initialize(this);

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(Category.class);
        configurationBuilder.addModelClasses(Comment.class);
        configurationBuilder.addModelClasses(District.class);
        configurationBuilder.addModelClasses(Event.class);
        configurationBuilder.addModelClasses(Issue.class);
        configurationBuilder.addModelClasses(Session.class);
        configurationBuilder.addModelClasses(User.class);
        configurationBuilder.addModelClasses(Polygon.class);
        configurationBuilder.addModelClasses(Point.class);

        ActiveAndroid.initialize(configurationBuilder.create());

        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        createDlRestAdapter();

        //initGoogleAnalytics();

        filter = new Filter();
    }

    private void initGoogleAnalytics() {
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-213599-109");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    public static void createDlRestAdapter() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                if (currentSession != null && currentSession.getApiKey() != null) {
                    request.addQueryParam("apikey", currentSession.getApiKey());
                }

                if (currentSession == null) {
                    refreshCurrentSession();
                }

                if (currentSession != null && currentSession.getSsid() != null) {
                    request.addQueryParam("ssid", currentSession.getSsid());
                }
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(DlApi.API_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .setErrorHandler(new GlobalErrorHandler())
                .build();


        baseApi = restAdapter.create(DlApi.Base.class);
        issueApi = restAdapter.create(DlApi.IssueApi.class);
        eventApi = restAdapter.create(DlApi.EventApi.class);
        voteApi = restAdapter.create(DlApi.VoteApi.class);
        userApi = restAdapter.create(DlApi.UserApi.class);
        commentApi = restAdapter.create(DlApi.CommentApi.class);

        restCityAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(DlApi.CITY_API_URL)
                .setConverter(new GsonConverter(gson))
                .setErrorHandler(new GlobalErrorHandler())
                .build();

        poznanApi = restCityAdapter.create(CityApi.PoznanApi.class);

        refreshCurrentSession();
    }

    public static void refreshCurrentSession() {
        currentSession = new Select().from(Session.class).executeSingle();
    }


    protected synchronized void buildGoogleApiClient() {
        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
            Log.d(TAG, "gacdbg GoogleApiClient buildGoogleApiClient");
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d(TAG, "gacdbg GoogleApiClient onConnected Location Services");
        if (mLastLocation != null) {
            pref.edit().lastLat().put((float) mLastLocation.getLatitude()).lastLon().put((float) mLastLocation.getLongitude()).apply();
            Log.d(TAG, "gacdbg GoogleApiClient onConnected Location Services mLastLocation: " + mLastLocation.toString());
            Log.d(TAG, "gacdbg location exists " + pref.lastLat().get());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "gacdbg GoogleApiClient onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "gacdbg GoogleApiClient onConnectionFailed connectionResult: " + connectionResult.toString());
    }
}
