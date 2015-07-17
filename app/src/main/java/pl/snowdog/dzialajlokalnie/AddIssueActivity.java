package pl.snowdog.dzialajlokalnie;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.EActivity;

import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueCategoriesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueImageFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueLocationFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueTitleDateFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueTitleDateFragment_;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.NewIssue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add_issue)
public class AddIssueActivity extends AddBaseActivity {
    private static final String TAG = "AddIssueActivity";

    @Override
    protected void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Locale l = Locale.getDefault();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new AddIssueTitleDateFragment_().builder().mAddingMode(AddIssueTitleDateFragment.MODE_ISSUE).build(), getString(R.string.title_section1).toUpperCase(l));
        adapter.addFragment(new AddIssueLocationFragment_(), getString(R.string.title_section2).toUpperCase(l));
        adapter.addFragment(new AddIssueImageFragment_(), getString(R.string.title_section3).toUpperCase(l));
        adapter.addFragment(new AddIssueCategoriesFragment_(), getString(R.string.title_section3).toUpperCase(l));
        viewPager.setAdapter(adapter);

        //Disable swipe events for viewpager
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void onEvent(CreateNewObjectEvent event) {
        switch (event.getType()) {
            case category:
                categoryIDs = event.getCategoryIDs();
                postIssue();
                return;
        }
        super.onEvent(event);
    }

    private void postIssue() {
        NewIssue newIssue = new NewIssue();
        newIssue.setTitle(title);
        newIssue.setDescription(description);
        newIssue.setAddress(address);
        newIssue.setLocation(Double.toString(lat) + "," + Double.toString(lon));
        newIssue.setCategoryID(categoryIDs);
        newIssue.setDistrictID(districtID);


        DlApplication.issueApi.postIssue(newIssue, new Callback<Issue.IssueWrapper>() {
            @Override
            public void success(Issue.IssueWrapper issueWrapper, Response response) {
                Log.d(TAG, "issueApi post success: " + response + " newIssueFromApi: " + issueWrapper.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "issueApi post error: " + error);
            }
        });


    }


}
