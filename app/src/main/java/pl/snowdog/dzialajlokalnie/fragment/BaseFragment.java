package pl.snowdog.dzialajlokalnie.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.util.List;

import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected void getIssues() {
        DlApplication.issueApi.getIssues(new Callback<List<Issue>>() {
            @Override
            public void success(List<Issue> issues, Response response) {
                Log.d(TAG, "getIssues success: " + issues);
                issuesResult(issues);

                new Delete().from(Issue.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (Issue issue : issues) {
                        issue.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getIssues failure: " + error);
            }
        });
    }

    protected void issuesResult(List<Issue> issues) {
        // implement by override
    }

    protected void getEvents() {
        DlApplication.eventApi.getEvents(new Callback<List<Event>>() {
            @Override
            public void success(List<Event> events, Response response) {
                Log.d(TAG, "getEvents success: " + events);
                eventsResult(events);

                new Delete().from(Issue.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (Event event : events) {
                        event.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getEvents failure: " + error);
            }
        });
    }

    protected void eventsResult(List<Event> events) {
    }
}
