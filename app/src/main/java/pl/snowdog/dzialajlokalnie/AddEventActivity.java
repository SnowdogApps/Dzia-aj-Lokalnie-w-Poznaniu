package pl.snowdog.dzialajlokalnie;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.androidannotations.annotations.EActivity;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add_issue)
public class AddEventActivity extends AddBaseActivity {
    private static final String TAG = "AddEventActivity";

    //Event specific fields:
    Date startDate;
    Date endDate;
    String facebookURL;

    @Override
    void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Locale l = Locale.getDefault();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new AddIssueFirstFragment_().builder().mAddingMode(AddIssueFirstFragment.MODE_EVENT).build(), getString(R.string.title_section1).toUpperCase(l));
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
            case date:
                //Consume Date setting
                title = event.getTitle();
                description = event.getDescription();
                endDate = event.getEndDate().getDate();
                startDate = event.getStartDate().getDate();
                goToNextPage();
                return;
            case category:
                categoryIDs = event.getCategoryIDs();
                postEvent();
                return;
        }
        super.onEvent(event);
    }

    private void postEvent() {
        NewEvent newEvent = new NewEvent();
        newEvent.setTitle(title);
        newEvent.setDescription(description);
        newEvent.setAddress(address);
        newEvent.setLocation(Double.toString(lat)+","+Double.toString(lon));
        newEvent.setCategoryID(categoryIDs);
        newEvent.setDistrictID(districtID);
        newEvent.setStartDate(startDate);
        newEvent.setEndDate(endDate);

        DlApplication.eventApi.postEvent(newEvent, new Callback<Event.EventWrapper>() {
            @Override
            public void success(Event.EventWrapper eventWrapper, Response response) {
                Log.d(TAG, "eventApi post success: " + response + " newEventFromApi: " + eventWrapper.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "eventApi post error: " + error);
            }
        });
    }

}
