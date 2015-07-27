package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Modifier;

import pl.snowdog.dzialajlokalnie.api.CityApi;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.api.GlobalErrorHandler;
import pl.snowdog.dzialajlokalnie.model.Filter;
import pl.snowdog.dzialajlokalnie.model.Session;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

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

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        createDlRestAdapter();


        filter = new Filter();
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
}
