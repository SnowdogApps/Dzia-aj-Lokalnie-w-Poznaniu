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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddCategoriesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddImageFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment_;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment_;
import pl.snowdog.dzialajlokalnie.model.DateWrapper;

/**
 * Created by chomi3 on 2015-07-23.
 */
@EActivity
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @ViewById(R.id.pager)
    protected ViewPager mViewPager;

    @ViewById(R.id.tabs)
    protected TabLayout mTabLayout;

    protected ApiActionDialogFragment mApiActionDialogFragment;


    @Override
    protected void afterView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);

        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(2);
            setupViewPager(mViewPager);
        }

        mTabLayout.setupWithViewPager(mViewPager);

    }

    void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Locale l = Locale.getDefault();
        AddBaseActivity.Adapter adapter = new AddBaseActivity.Adapter(getSupportFragmentManager());
/*

        adapter.addFragment(new AddTitleDateFragment_().builder()
                .mEditedObject(mEditedEvent != null ? new CreateNewObjectEvent.Builder()
                        .title(mEditedEvent.getTitle())
                        .description(mEditedEvent.getDescription())
                        .startDate(new DateWrapper(mEditedEvent.getStartDate()))
                        .endDate(new DateWrapper(mEditedEvent.getEndDate()))
                        .build() : null)
                .mAddingMode(AddTitleDateFragment.MODE_EVENT)
                .build());
*/


        viewPager.setAdapter(adapter);

        //Disable swipe events for viewpager
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
