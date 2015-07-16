package pl.snowdog.dzialajlokalnie.api;

import java.util.List;
import java.util.Map;

import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.NewEvent;
import pl.snowdog.dzialajlokalnie.model.NewIssue;
import pl.snowdog.dzialajlokalnie.model.ReverseGeocoding;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.Vote;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by chomi3 on 16.07.15.
 */
public class CityApi {
    public static final String API_URL = "http://www.poznan.pl";

    public interface PoznanApi {

        // http://www.poznan.pl/ &x=16.939909&y=52.404694&n=1
        //@GET("/mim/plan/services.html?co=json&service=adresy&srs=EPSG:4326&x={lat}&y={lon}&n={results}")
        @GET("/mim/plan/services.html?co=json&service=adresy&srs=EPSG:4326")
        void getAddressForLocation(@QueryMap Map<String, String> options,
                       Callback<ReverseGeocoding> cb);

    }

}
