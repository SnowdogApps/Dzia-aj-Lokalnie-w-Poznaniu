package pl.snowdog.dzialajlokalnie;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.File;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.FilterChangedEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddCategoriesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddImageFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment_;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.NewIssue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add_issue)
public class AddIssueActivity extends AddBaseActivity {
    private static final String TAG = "AddIssueActivity";

    @Extra
    Issue mEditedIssue;

    @Override
    protected void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Locale l = Locale.getDefault();
        mViewPager.setOffscreenPageLimit(4);
        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new AddTitleDateFragment_().builder()
                .mEditedObject(mEditedIssue != null ? new CreateNewObjectEvent.Builder()
                        .title(mEditedIssue.getTitle())
                        .description(mEditedIssue.getDescription())
                        .build() : null)
                .mAddingMode(AddTitleDateFragment.MODE_ISSUE)
                .build());

        adapter.addFragment(new AddLocationFragment_().builder()
                .mEditedObject(mEditedIssue != null ? new CreateNewObjectEvent.Builder()
                        .lat(mEditedIssue.getLat())
                        .lon(mEditedIssue.getLon())
                        .districtID(mEditedIssue.getDistrictID())
                        .address(mEditedIssue.getAddress())
                        .build() : null)
                .build());

        adapter.addFragment(new AddImageFragment_().builder()
                .mEditedObject(mEditedIssue != null ? new CreateNewObjectEvent.Builder()
                        .image(mEditedIssue.getPhotoIssueUri())
                        .build() : null)
                .build());

        adapter.addFragment(new AddCategoriesFragment_().builder()
                .mEditedObject(mEditedIssue != null ? new CreateNewObjectEvent.Builder()
                        .categoryIDs(mEditedIssue.getCategoryIdsList())
                        .build() : null)
                .build());

        viewPager.setAdapter(adapter);

        //Disable swipe events for viewpager
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void afterView() {
        super.afterView();
        if(mEditedIssue != null) {
            getSupportActionBar().setTitle(getString(R.string.edit_issue));
        }
    }

    public void onEvent(CreateNewObjectEvent event) {
        switch (event.getType()) {
            case category:
                categoryIDs = event.getCategoryIDs();
                if(mEditedIssue != null) {
                    putIssue();
                } else {
                    //toggleProgressWheel(true);
                    postIssue();
                }
                return;
        }
        super.onEvent(event);
    }

    private void postIssue() {
        toggleProgressWheel(true);
        NewIssue newIssue = createNewIssueObject();

        DlApplication.issueApi.postIssue(newIssue, new Callback<Issue>() {
            @Override
            public void success(Issue issueWrapper, Response response) {
                Log.d(TAG, "issueApi post success: " + response.toString() + " newIssueFromApi: " + issueWrapper.toString()+ " photoUri: "+photoUri.toString());
                if (photoUri != null && photoUri.length() > 0) {
                    putIssueImage(issueWrapper.getIssueID());
                } else {
                    //Finished adding, close view
                    finishAdding(ObjectAddedEvent.Type.issue);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                toggleProgressWheel(false);
                Log.d(TAG, "issueApi post error: " + error);
            }
        });
    }



    private void putIssue() {
        toggleProgressWheel(true);
        NewIssue newIssue = createNewIssueObject();
        DlApplication.issueApi.putIssue(newIssue, mEditedIssue.getIssueID(), new Callback<Issue>() {
            @Override
            public void success(Issue issueWrapper, Response response) {
                Log.d(TAG, "issueApi put success: " + response + " newIssueFromApi: " + issueWrapper.toString());
                if (photoUri != null && photoUri.length() > 0) {
                    putIssueImage(issueWrapper.getIssueID());
                } else {
                    //Finished adding, close view
                    finishAdding(ObjectAddedEvent.Type.issue);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "issueApi put error: " + error);
               toggleProgressWheel(false);
            }
        });
    }

    private void putIssueImage(int issueId) {

        TypedFile file = new TypedFile("image/jpg", new File(photoUri));

        DlApplication.issueApi.postIssueImage(file, issueId, new Callback<Issue>() {
            @Override
            public void success(Issue issueWrapper, Response response) {
                Log.d(TAG, "issueApi put image success: " + response + " newIssueFromApi: " + issueWrapper.toString());
                //toggleProgressWheel(false);
                finishAdding(ObjectAddedEvent.Type.issue);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "issueApi put imaget error: " + error);
                toggleProgressWheel(false);
            }
        });
    }

    @NonNull
    private NewIssue createNewIssueObject() {
        NewIssue newIssue = new NewIssue();
        newIssue.setTitle(title);
        newIssue.setDescription(description);
        newIssue.setAddress(address);
        newIssue.setLocation(Double.toString(lat) + "," + Double.toString(lon));
        newIssue.setCategoryID(categoryIDs);
        newIssue.setDistrictID(districtID);


        return newIssue;
    }


}
