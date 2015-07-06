package pl.snowdog.dzialajlokalnie;

import android.app.Application;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import retrofit.RestAdapter;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApplication extends Application {

    public static RestAdapter restAdapter;
    public static DlApi.Categories categoriesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        
    }
}
