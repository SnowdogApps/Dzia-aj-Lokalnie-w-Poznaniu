package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

    public static RestAdapter restAdapter;
    public static DlApi.Base baseApi;
    public static DlApi.IssueApi issueApi;
    public static DlApi.EventApi eventApi;
    public static DlApi.VoteApi voteApi;
    public static DlApi.UserApi userApi;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        final RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addQueryParam("apikey", "wjk8regtrvv158mu3ekb");
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(DlApi.API_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();

        baseApi = restAdapter.create(DlApi.Base.class);
        issueApi = restAdapter.create(DlApi.IssueApi.class);
        eventApi = restAdapter.create(DlApi.EventApi.class);
        voteApi = restAdapter.create(DlApi.VoteApi.class);
        userApi = restAdapter.create(DlApi.UserApi.class);
    }
}
