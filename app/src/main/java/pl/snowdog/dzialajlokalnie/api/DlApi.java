package pl.snowdog.dzialajlokalnie.api;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Vote;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApi {

    //    private static final String API_URL = "http://192.168.1.95/dzialaj-lokalnie-api/index.php/";
    public static final String API_URL = "http://dzialajlokalnie.snowdog.pro";
    public static final String PHOTO_THUMB_URL = API_URL + "/photos/%s_thumb.jpeg";

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

    public interface VoteApi {

        /**
         *
         * @param what issues or comments
         * @param id id of issue or comment
         * @param vote vote object with value 1, 0, -1
         * @param cb callback
         */
        @PUT("/{what}/{id}/vote")
        void vote(@Path("what") String what, @Path("id") int id, @Body Vote vote, Callback<Vote> cb);
    }

    public interface UserApi {
        @PUT("/login")
        void login(@Field("username") String username, @Field("pass") String pass, @Field("isSessionAuthByAPIkey") int byApiKey);
    }
}
