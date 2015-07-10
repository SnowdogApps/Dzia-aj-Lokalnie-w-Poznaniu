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
import pl.snowdog.dzialajlokalnie.fragment.FilterFragment;
import pl.snowdog.dzialajlokalnie.fragment.FilterFragment_;
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

        if (id == R.id.action_filter) {
            FilterFragment fragment = FilterFragment_.builder().build();
            fragment.show(getFragmentManager(), "filter");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
