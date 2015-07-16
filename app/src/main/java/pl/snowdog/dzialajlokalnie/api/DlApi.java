package pl.snowdog.dzialajlokalnie.api;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.Vote;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApi {

//        public static final String API_URL = "http://192.168.1.96/dzialaj-lokalnie-api/index.php/";
    public static final String API_URL = "http://dzialajlokalnie.snowdog.pro";
    public static final String PHOTO_THUMB_URL = API_URL + "/photos/%s_thumb.jpeg";

    public enum ParentType {issues, events, comments}

    public interface Base {
        @GET("/categories")
        void getCategories(Callback<List<Category>> cb);

        @GET("/districts")
        void getDistricts(Callback<List<District>> cb);
    }

    public interface IssueApi {
        @GET("/issues")
        void getIssues(@Query("districts") String districts,
                       @Query("categories") String categories,
                       @Query("orderBy") String sort,
                       Callback<List<Issue>> cb);

        @GET("/issues/{id}")
        void getIssue(@Path("id") int id,
                       Callback<Issue> cb);
    }

    public interface EventApi {
        @GET("/events")
        void getEvents(@Query("districts") String districts,
                       @Query("categories") String categories,
                       @Query("orderBy") String sort,
                       Callback<List<Event>> cb);
    }

    public interface CommentApi {
        @GET("/{parentType}/{id}/comments")
        void getComments(@Path("parentType") String parentType,
                       @Path("id") int id,
                       Callback<List<Comment>> cb);
    }
    public interface VoteApi {

        /**
         *
         * @param what issues or comments
         * @param id id of issue or comment
         * @param value value 1, 0, -1
         * @param cb callback
         */
        @FormUrlEncoded
        @PUT("/{what}/{id}/vote")
        void vote(@Path("what") String what, @Path("id") int id, @Field("value") int value, Callback<Vote> cb);
    }

    public interface UserApi {
        @FormUrlEncoded
        @PUT("/login")
        void login(@Field("username") String username, @Field("pass") String pass,
                   @Field("isSessionAuthByAPIkey") int byApiKey, Callback<Session> cb);
    }
}
