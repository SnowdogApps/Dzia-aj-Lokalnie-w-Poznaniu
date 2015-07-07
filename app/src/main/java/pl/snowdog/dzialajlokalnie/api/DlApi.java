package pl.snowdog.dzialajlokalnie.api;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApi {

    public interface Base {
        @GET("/categories")
        void getCategories(Callback<List<Category>> cb);

        @GET("/districts")
        void getDistricts(Callback<List<District>> cb);
    }

    public interface IssueApi {
        @GET("/issues")
        void getIssues(Callback<List<Issue>> cb);
    }

    public interface EventApi {
        @GET("/events")
        void getEvents(Callback<List<Event>> cb);
    }
}
