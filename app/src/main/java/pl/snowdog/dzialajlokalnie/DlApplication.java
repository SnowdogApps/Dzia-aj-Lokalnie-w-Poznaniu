package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

    private static final String API_URL = "http://192.168.1.95/dzialaj-lokalnie-api/index.php/";
    public static RestAdapter restAdapter;
    public static DlApi.Categories categoriesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        categoriesApi = restAdapter.create(DlApi.Categories.class);
    }
}
