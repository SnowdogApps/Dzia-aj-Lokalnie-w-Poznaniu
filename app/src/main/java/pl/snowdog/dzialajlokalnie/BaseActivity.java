package pl.snowdog.dzialajlokalnie;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

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

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        getCategories();
    }

    protected void getCategories() {
        DlApplication.baseApi.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                Log.d(TAG, "categories success: " + categories);
                categoriesResult(categories);

                new Delete().from(Category.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (Category category : categories) {
                        category.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
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
