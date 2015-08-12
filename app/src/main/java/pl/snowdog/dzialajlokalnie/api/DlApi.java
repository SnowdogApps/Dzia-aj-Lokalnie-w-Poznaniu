package pl.snowdog.dzialajlokalnie.api;

import java.util.List;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Login;
import pl.snowdog.dzialajlokalnie.model.NewEvent;
import pl.snowdog.dzialajlokalnie.model.NewIssue;

import pl.snowdog.dzialajlokalnie.model.NewUser;

import pl.snowdog.dzialajlokalnie.model.ParticipateEvent;

import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.User;
import pl.snowdog.dzialajlokalnie.model.Vote;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by bartek on 06.07.15.
 */
public class DlApi {

//        public static final String API_URL = "http://192.168.1.96/dzialaj-lokalnie-api/index.php/";
    public static final String API_URL = "http://dzialajlokalnie.snowdog.pro";
    //public static final String API_URL = "http://144.76.97.81";

    public static final String CITY_API_URL = "http://www.poznan.pl/";
    public static final String PHOTO_THUMB_URL = API_URL + "/photos/%s_thumb.jpeg";
    public static final String PHOTO_NORMAL_URL = API_URL + "/photos/%s_normal.jpeg";
    public static final String AVATAR_THUMB_URL = API_URL + "/avatars/%s_thumb.jpeg";
    public static final String AVATAR_NORMAL_URL = API_URL + "/avatars/%s_normal.jpeg";

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

        @POST("/issues/new")
        void postIssue(@Body NewIssue issue,
                       Callback<Issue> cb);

        @Multipart
        @POST("/issues/{id}/photo/new")
        void postIssueImage(@Part("photoIssue") TypedFile image,
                           @Path("id") int issueId,
                           Callback<Issue> cb);

        @PUT("/issues/{id}/edit")
        void putIssue(@Body NewIssue issue,
                      @Path("id") int issueId,
                      Callback<Issue> cb);
    }

    public interface EventApi {
        @GET("/events")
        void getEvents(@Query("districts") String districts,
                       @Query("categories") String categories,
                       @Query("orderBy") String sort,
                       Callback<List<Event>> cb);

        @GET("/events/{id}")
        void getEvent(@Path("id") int id,
                      Callback<Event> cb);

        @POST("/events/new")
        void postEvent(@Body NewEvent event,
                       Callback<Event> cb);

        @Multipart
        @POST("/events/{id}/photo/new")
        void postEventImage(@Part("photoEvent") TypedFile image,
                           @Path("id") int eventId,
                           Callback<Event> cb);

        @PUT("/events/{id}/edit")
        void putEvent(@Body NewEvent event,
                      @Path("id") int eventId,
                      Callback<Event> cb);

        @PUT("/events/{id}/users")
        void putParticipateEvent(@Body ParticipateEvent participateEvent,
                      @Path("id") int eventId,
                      Callback<ParticipateEvent> cb);

    }

    public interface CommentApi {
        @GET("/{parentType}/{id}/comments")
        void getComments(@Path("parentType") String parentType,
                       @Path("id") int id,
                       Callback<List<Comment>> cb);

        @POST("/comments/new")
        void comment(@Body Comment comment, Callback<Comment> cb);
    }

    public interface VoteApi {

        /**
         * @param what issues or comments
         * @param id id of issue or comment
         * @param vote with value 1, 0, -1
         * @param cb callback
         */
        @PUT("/{what}/{id}/vote")
        void vote(@Path("what") String what, @Path("id") int id, @Body Vote vote, Callback<Vote> cb);
    }

    public interface UserApi {
        @PUT("/login")
        void login(@Body Login login, Callback<Session> cb);

        @PUT("/login/fb")
        void loginFb(@Body Login.Facebook login, Callback<Login.Facebook> cb);

        @GET("/users/{id}")
        void getUserById(@Path("id") int userId, Callback<User> cb);

        @POST("/users/new")
        void postNewUser(@Body NewUser newUser, Callback<User> cb);

        @PUT("/users/{id}/edit")
        void putUser(@Body NewUser newUser,
                     @Path("id") int userId,
                     Callback<User> cb);

        @Multipart
        @POST("/users/{id}/avatar/new")
        void postUserAvatar(@Part("avatarPhoto") TypedFile image,
                            @Path("id") int issueId,
                            Callback<User> cb);
    }
}
