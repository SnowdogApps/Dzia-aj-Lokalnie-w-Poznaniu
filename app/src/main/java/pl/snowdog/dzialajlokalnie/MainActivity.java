package pl.snowdog.dzialajlokalnie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.adapter.FragmentAdapter;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.FilterChangedEvent;
import pl.snowdog.dzialajlokalnie.fragment.EventsFragment_;
import pl.snowdog.dzialajlokalnie.fragment.FilterFragment;
import pl.snowdog.dzialajlokalnie.fragment.FilterFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.MapFragment_;
import pl.snowdog.dzialajlokalnie.gcm.NotificationAction;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Filter;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.Notification;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
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
    FloatingActionsMenu fab;

    @ViewById(R.id.ivAvatar)
    ImageView ivNavAvatar;

    @ViewById(R.id.tvUserName)
    TextView tvNavUserName;

    @ViewById(R.id.tvUserDistrict)
    TextView tvNavUserDistrict;

/*
    @Click(R.id.ivAvatar)
    void onNavAvatarClicked() {

    }
*/

    @Click(R.id.navHeader)
    void onNavHeaderClicked() {
        if(isLoggedIn()) {
            AddUserActivity_.intent(this).mEditedUser(getLoggedInUser()).start();
        } else {
            AddUserActivity_.intent(this).start();
        }
        mDrawerLayout.closeDrawers();
    }

    @AfterViews
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
        //logout();
        if (!isLoggedIn()) {
            tvNavUserName.setText("Register");
            tvNavUserDistrict.setText("or Login");

            //login("bartek@bartek.pl", "bartek");
        } else {
            List<User> users = new Select().from(User.class).execute();
            for(User u : users) {
                Log.d(TAG, "usrdbg user: "+u.toString()+" sessionUserId: "+DlApplication.currentSession.getUserID());
            }
            User user = getLoggedInUser();
            if(user != null) {
                tvNavUserName.setText(user.getName() + " " + user.getSurname());
                District district = new Select().from(District.class).where("districtID = ?", user.getDistrictID()).executeSingle();
                tvNavUserDistrict.setText(district.getName());

                Picasso.with(this).
                        load(String.format(DlApi.AVATAR_THUMB_URL, user.getAvatarUri())).
                        error(R.drawable.ic_editor_insert_emoticon).
                        into(ivNavAvatar);
            }
        }

    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras != null) {
            int action = extras.getInt(NotificationAction.INTENT_ACTION);
            switch (action) {
                case NotificationAction.NEW_ISSUE_SURROUND:

                    break;
            }
        }
    }

    @Click(R.id.fab_new_event)
    void onFabNewEventClicked() {
        if(!isLoggedIn()) {
            showLoginSnackbar();
        } else {
            fab.collapse();
            AddEventActivity_.intent(this).start();
        }
    }

    @Click(R.id.fab_new_issue)
    void onFabNewIssueClicked() {
        if(!isLoggedIn()) {
            showLoginSnackbar();
        } else {
            fab.collapse();
            AddIssueActivity_.intent(this).start();

            //AddUserActivity_.intent(this).start();
        }
        //AddIssueActivity_.intent(this).start();

    }

    @Override
    protected void loginResult(Session session) {

        List<Session> dbSessions = new Select().from(Session.class).execute();

        for (Session s : dbSessions) {
            Log.d(TAG, "loginResult " + s);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Locale l = Locale.getDefault();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new IssuesFragment_(), getString(R.string.title_section1).toUpperCase(l));
        adapter.addFragment(new MapFragment_(), getString(R.string.title_section2).toUpperCase(l));
        adapter.addFragment(new EventsFragment_(), getString(R.string.title_section3).toUpperCase(l));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_settings:
                                mDrawerLayout.closeDrawers();
                                SettingsActivity_.intent(MainActivity.this).start();
                                break;
                            default:
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();
                                break;
                        }

                        return true;
                    }
                });
    }

    @OptionsItem(android.R.id.home)
    void homeSelected() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @OptionsItem(R.id.action_filter)
    void filterSelected() {
        FilterFragment fragment = FilterFragment_.builder().build();
        fragment.show(getSupportFragmentManager(), "filter");
    }

    @OptionsItem(R.id.action_logout)
    void actionLogout() {
        logout();
    }

    @OptionsItem(R.id.action_sort_popular)
    void sortPopular(MenuItem item) {
        applySort(item, Filter.Sort.popular);
    }

    @OptionsItem(R.id.action_sort_newest)
    void sortNewest(MenuItem item) {
        applySort(item, Filter.Sort.newest);
    }

    @OptionsItem(R.id.action_sort_top)
    void sortTop(MenuItem item) {
        applySort(item, Filter.Sort.top);
    }

    private boolean applySort(MenuItem item, Filter.Sort sotyType) {
        DlApplication.filter.setSort(sotyType);
        item.setChecked(true);
        EventBus.getDefault().post(new FilterChangedEvent());
        return true;
    }
}
