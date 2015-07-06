package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import retrofit.RestAdapter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

    private static final String API_URL = "http://192.168.1.95/php.dzialajLokalnie/index.php";
    public static RestAdapter restAdapter;
    public static DlApi.Categories categoriesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();

        categoriesApi = restAdapter.create(DlApi.Categories.class);
    }
}
