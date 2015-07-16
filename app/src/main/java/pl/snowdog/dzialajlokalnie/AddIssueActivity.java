package pl.snowdog.dzialajlokalnie;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.NetworkErrorEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFirstFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFirstFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFourthFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueSecondFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueThirdFragment_;
import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Filter;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.NewEvent;
import pl.snowdog.dzialajlokalnie.model.NewIssue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent.Type.title;

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
        adapter.addFragment(new AddIssueFirstFragment_().builder().mAddingMode(AddIssueFirstFragment.MODE_ISSUE).build(), getString(R.string.title_section1).toUpperCase(l));
        adapter.addFragment(new AddIssueSecondFragment_(), getString(R.string.title_section2).toUpperCase(l));
        adapter.addFragment(new AddIssueThirdFragment_(), getString(R.string.title_section3).toUpperCase(l));
        adapter.addFragment(new AddIssueFourthFragment_(), getString(R.string.title_section3).toUpperCase(l));
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
