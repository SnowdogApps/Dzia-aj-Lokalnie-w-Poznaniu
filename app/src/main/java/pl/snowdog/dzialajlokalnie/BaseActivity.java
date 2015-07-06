package pl.snowdog.dzialajlokalnie;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected void getCategories() {
        DlApplication.categoriesApi.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                Log.d(TAG, "categories success: " + categories);
                categoriesResult(categories);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "categories failure: " + error);
            }
        });
    }

    protected void categoriesResult(List<Category> categories) {
        // implement by override
    }
}
