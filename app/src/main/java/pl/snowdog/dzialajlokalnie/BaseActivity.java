package pl.snowdog.dzialajlokalnie;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;
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
        getCategories();
        getDistricts();
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

}
