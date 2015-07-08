package pl.snowdog.dzialajlokalnie;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Session;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
@EActivity(R.layout.activity_main)
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @AfterViews
    protected void afterBaseActivityViews() {
        //TODO move init call for constant data to splash or other dedicated activity
        getCategories();
        getDistricts();

        //you can call there everything for all descendant activities that you normally call in onCreate method
        afterView();
    }

    protected abstract void afterView();

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

    protected void getDistricts() {
        DlApplication.baseApi.getDistricts(new Callback<List<District>>() {
            @Override
            public void success(List<District> districts, Response response) {
                Log.d(TAG, "getDistricts success: " + districts);


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
        DlApplication.userApi.login(username, pass, 2, new Callback<Session>() {
            @Override
            public void success(Session session, Response response) {
                Log.d(TAG, "login success: " + session);

                new Delete().from(Session.class).execute();

                session.save();

                loginResult(session);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "login failure: " + error);
            }
        });
    }

    protected void loginResult(Session session) { }
}
