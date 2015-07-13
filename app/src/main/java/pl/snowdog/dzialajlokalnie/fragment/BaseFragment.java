package pl.snowdog.dzialajlokalnie.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Filter;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Vote;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    /**
     * Override this method to return true if you want to use EventBus and implemented onEvent method
     * @return
     */
    protected boolean isImplementingEventBus() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isImplementingEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (isImplementingEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        super.onStop();
    }

    protected void getIssues() {
        Filter filter = DlApplication.filter;

        DlApplication.issueApi.getIssues(filter.getDistrictFilter(),
                filter.getCategoriesFilter(),
                filter.getSortForIssues(),
                new Callback<List<Issue>>() {
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
        Filter filter = DlApplication.filter;

        DlApplication.eventApi.getEvents(filter.getDistrictFilter(),
                filter.getCategoriesFilter(),
                filter.getSortForEvents(),
                new Callback<List<Event>>() {
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

    protected void eventsResult(List<Event> events) { }

    protected void vote(Vote.ParentType parentType, int parentId, int value) {
        DlApplication.voteApi.vote(parentType.name(), parentId, value, new Callback<Vote>() {
            @Override
            public void success(Vote vote, Response response) {
                Log.d(TAG, "vote success: " + vote);
                voteResult(vote);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "vote failure: " + error);
            }
        });
    }

    protected void voteResult(Vote vote) { }


}
