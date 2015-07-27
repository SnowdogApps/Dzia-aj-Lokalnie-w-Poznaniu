package pl.snowdog.dzialajlokalnie;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment_;
import pl.snowdog.dzialajlokalnie.view.CircularProgressWheel;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity
public abstract class AddBaseActivity extends BaseActivity {

    private static final String TAG = "AddBaseActivity";
    @ViewById(R.id.pager)
    protected ViewPager mViewPager;

    @ViewById(R.id.tabs)
    protected TabLayout mTabLayout;

    protected ApiActionDialogFragment mApiActionDialogFragment;

    //Common fields for creation of new objects (issue / event)
    String title;
    String description;
    int districtID;
    double lat;
    double lon;
    String address;
    List<Integer> categoryIDs;
    String photoUri;


    @Override
    protected void afterView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);

        if (mViewPager != null) {

            setupViewPager(mViewPager);
        }

        mTabLayout.setupWithViewPager(mViewPager);
        updateSubtitle();
    }

    abstract void setupViewPager(ViewPager viewPager);

    public void goToNextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        updateSubtitle();
        hideKeyboard();
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void goToPreviousPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        updateSubtitle();
        hideKeyboard();
    }

    @Override
    public void onBackPressed() {
        //Close activity if we're at the start of the viewpager
        if(mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            //Use back button to go back in the viewpager tabs.
            goToPreviousPage();
        }

    }

    protected void toggleProgressWheel(boolean show) {
        if(show) {
            if(mApiActionDialogFragment == null) {
                mApiActionDialogFragment = ApiActionDialogFragment_.builder().build();
            }
            mApiActionDialogFragment.show(getSupportFragmentManager(), ApiActionDialogFragment.TAG);
        } else {
            mApiActionDialogFragment.dismiss();
        }
    }

    private void updateSubtitle() {
        getSupportActionBar().setSubtitle(getString(R.string.add_issue_step) + " " + (mViewPager.getCurrentItem() + 1) + "/" + mViewPager.getAdapter().getCount());
    }

    /**
     * Handles gathering informations from fragments
     * NOTE: final step (category) is handled by child activities (AddIssue / AddEvent)
     * as we need to create different objects from the data
     * @param event
     */
    public void onEvent(CreateNewObjectEvent event) {
        switch (event.getType()) {
            case title:
                title = event.getTitle();
                description = event.getDescription();
                goToNextPage();
                break;
            case location:
                lat = event.getLat();
                lon = event.getLon();
                address = event.getAddress();
                districtID = event.getDistrictID();
                goToNextPage();
                break;
            case image:
                photoUri = event.getImage();
                goToNextPage();
                break;

        }
    }

    protected void finishAdding(ObjectAddedEvent.Type added) {
        toggleProgressWheel(false);
        finish();
        //EventBus.getDefault().post(new ObjectAddedEvent(added));
        MainActivity_.intent(this).start();
    }

    public void onObjectCreated() {
        Log.d(TAG, "title: "+title+" description: "+description+ " lat: "+lat+ " lon: "+lon+ " districtID: "+districtID
        +" categories: "+categoryIDs.toString());
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
            mFragmentTitles.add("");
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


    @OptionsItem(android.R.id.home)
    void homeSelected() {
        this.finish();
    }
}
