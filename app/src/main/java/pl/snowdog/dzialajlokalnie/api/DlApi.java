package pl.snowdog.dzialajlokalnie.api;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApi {

    public interface Categories {

        @GET("/categories")
        void getCategories(Callback<List<Category>> cb);
    }
}
