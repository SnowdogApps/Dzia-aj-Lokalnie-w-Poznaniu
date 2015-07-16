package pl.snowdog.dzialajlokalnie;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;

import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFirstFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFirstFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueFourthFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueSecondFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddIssueThirdFragment_;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add_issue)
public class AddEventActivity extends AddBaseActivity {
    private static final String TAG = "AddEventActivity";

    //Event specific fields:
    String startDate;
    String endDate;
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
                endDate = event.getEndDate().getDateString(this);
                startDate = event.getStartDate().getDateString(this);
                goToNextPage();
                onObjectCreated();
                return;

        }
        super.onEvent(event);
    }

    @Override
    public void onObjectCreated() {

        Toast.makeText(this, "We have working object yeah! "+title, Toast.LENGTH_SHORT).show();
    }

}
