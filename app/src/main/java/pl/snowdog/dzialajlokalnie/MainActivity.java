package pl.snowdog.dzialajlokalnie;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.fragment.EventsFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.MapFragment;
import pl.snowdog.dzialajlokalnie.model.Session;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @ViewById(R.id.pager)
    ViewPager mViewPager;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.nav_view)
    NavigationView mNavigationView;

    @ViewById(R.id.tabs)
    TabLayout mTabLayout;

    @ViewById(R.id.fab)
    FloatingActionButton fab;


    @Override
    protected void afterView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        mTabLayout.setupWithViewPager(mViewPager);

        // TODO move login to login activity
        logout();
        if (!isLoggedIn()) {
            login("Bartek", "bartek");
        }
    }

    @Override
    protected void loginResult(Session session) {

        List<Session> dbSessions = new Select().from(Session.class).execute();

        for (Session s : dbSessions) {
            Log.d(TAG, "loginResult " + s);
        }
    }


    @Click(R.id.fab)
    void onFabClicked() {
        /*Snackbar.make(fab, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }


    private void setupViewPager(ViewPager viewPager) {
        Locale l = Locale.getDefault();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new IssuesFragment_(), getString(R.string.title_section1).toUpperCase(l));
        adapter.addFragment(new MapFragment(), getString(R.string.title_section2).toUpperCase(l));
        adapter.addFragment(new EventsFragment_(), getString(R.string.title_section3).toUpperCase(l));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @OptionsItem(android.R.id.home)
    void homeSelected() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


//package pl.snowdog.dzialajlokalnie;
//
//<<<<<<< HEAD
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.NavigationView;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBar;
//=======
//import android.os.Bundle;
//>>>>>>> feature-15536-api
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.view.ViewPager;
//<<<<<<< HEAD
//import android.support.v7.widget.Toolbar;
//import android.view.Gravity;
//=======
//import android.support.v7.app.ActionBar;
//import android.util.Log;
//>>>>>>> feature-15536-api
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//
//<<<<<<< HEAD
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//=======
//import com.activeandroid.Model;
//import com.activeandroid.query.Select;
//
//>>>>>>> feature-15536-api
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.OptionsItem;
//import org.androidannotations.annotations.ViewById;
//
//import java.util.List;
//import java.util.Locale;
//
//import pl.snowdog.dzialajlokalnie.fragment.EventsFragment;
//import pl.snowdog.dzialajlokalnie.fragment.EventsFragment_;
//import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;
//import pl.snowdog.dzialajlokalnie.fragment.MapFragment;
//import pl.snowdog.dzialajlokalnie.model.Session;
//
//@EActivity(R.layout.activity_main)
//<<<<<<< HEAD
//public class MainActivity extends AppCompatActivity {
//
//=======
//public class MainActivity extends BaseActivity implements ActionBar.TabListener {
//
//    private static final String TAG = "MainActivity";
//    /**
//     * The {@link android.support.v4.view.PagerAdapter} that will provide
//     * fragments for each of the sections. We use a
//     * {@link FragmentPagerAdapter} derivative, which will keep every
//     * loaded fragment in memory. If this becomes too memory intensive, it
//     * may be best to switch to a
//     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
//     */
//    SectionsPagerAdapter mSectionsPagerAdapter;
//
//    /**
//     * The {@link ViewPager} that will host the section contents.
//     */
//>>>>>>> feature-15536-api
//    @ViewById(R.id.pager)
//    ViewPager mViewPager;
//
//    @ViewById(R.id.drawer_layout)
//    DrawerLayout mDrawerLayout;
//
//    @ViewById(R.id.nav_view)
//    NavigationView mNavigationView;
//
//    @ViewById(R.id.tabs)
//    TabLayout mTabLayout;
//
//    @ViewById(R.id.fab)
//    FloatingActionButton fab;
//
//
//<<<<<<< HEAD
//    @AfterViews
//    void afterViews() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);
//=======
//    @Override
//    protected void afterView() {
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // Create the adapter that will return a fragment for each of the three
//        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        // When swiping between different sections, select the corresponding
//        // tab. We can also use ActionBar.Tab#select() to do this if we have
//        // a reference to the Tab.
//        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//        });
//
//        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
//
//        mViewPager.setOffscreenPageLimit(3);
//
//        logout();
//        if (!isLoggedIn()) {
//            login("Bartek", "bartek");
//        }
//    }
//
//    @Override
//    protected void loginResult(Session session) {
//
//        List<Session> dbSessions = new Select().from(Session.class).execute();
//
//        for (Session s : dbSessions) {
//            Log.d(TAG, "loginResult " + s);
//        }
//    }
//>>>>>>> feature-15536-api
//
//
//
//        if (mNavigationView != null) {
//            setupDrawerContent(mNavigationView);
//        }
//
//        if (mViewPager != null) {
//            setupViewPager(mViewPager);
//        }
//
//
//        mTabLayout.setupWithViewPager(mViewPager);
//
//    }
//
//    @Click(R.id.fab)
//    void onFabClicked() {
//        /*Snackbar.make(fab, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();*/
//
//    }
//
//
//    private void setupViewPager(ViewPager viewPager) {
//        Locale l = Locale.getDefault();
//        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new IssuesFragment_(), getString(R.string.title_section1).toUpperCase(l));
//        adapter.addFragment(new MapFragment(), getString(R.string.title_section2).toUpperCase(l));
//        adapter.addFragment(new IssuesFragment_(), getString(R.string.title_section3).toUpperCase(l));
//        viewPager.setAdapter(adapter);
//    }
//
//    private void setupDrawerContent(NavigationView navigationView) {
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }
//
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//        public Adapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//<<<<<<< HEAD
//            return mFragments.get(position);
//=======
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            switch (position) {
//                case 0:
//                    return new IssuesFragment_();
//                case 1:
//                    return new MapFragment();
//                case 2:
//                    return new EventsFragment_();
//            }
//            return PlaceholderFragment.newInstance(position + 1);
//>>>>>>> feature-15536-api
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @OptionsItem(android.R.id.home)
//    void homeSelected() {
//        mDrawerLayout.openDrawer(GravityCompat.START);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
