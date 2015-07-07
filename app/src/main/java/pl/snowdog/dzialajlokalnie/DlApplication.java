package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

//    private static final String API_URL = "http://192.168.1.95/dzialaj-lokalnie-api/index.php/";
    private static final String API_URL = "http://dzialajlokalnie.snowdog.pro";
    public static RestAdapter restAdapter;
    public static DlApi.Base baseApi;
    public static DlApi.IssueApi issueApi;
    public static DlApi.EventApi eventApi;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        baseApi = restAdapter.create(DlApi.Base.class);
        issueApi = restAdapter.create(DlApi.IssueApi.class);
        eventApi = restAdapter.create(DlApi.EventApi.class);
    }
}
