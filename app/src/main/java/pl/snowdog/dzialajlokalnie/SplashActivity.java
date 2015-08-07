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

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment;
import pl.snowdog.dzialajlokalnie.fragment.ApiActionDialogFragment_;
import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.District;

/**
 * Created by chomi3 on 2015-07-27.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    boolean districtsReady = false;
    boolean categoriesReady = false;
    boolean startActivity = false;
    private boolean userReady = false;

    @Override
    protected void afterView() {
        startApiSync();
    }


    @Background(delay=10)
    void startApiSync() {
        getCategories();
        getDistricts();
        if(DlApplication.currentSession != null) {
            getUserById(DlApplication.currentSession.getUserID());
        } else {
            userReady = true;
        }
    }

    @Override
    protected void userResult() {
        super.userResult();
        userReady = true;
        startMainOrLoginActivity();
    }

    @Override
    protected void districtsResult(List<District> districts) {
        super.districtsResult(districts);
        districtsReady = true;
        startMainOrLoginActivity();
    }

    @Override
    protected void categoriesResult(List<Category> categories) {
        super.categoriesResult(categories);
        categoriesReady = true;
        startMainOrLoginActivity();
    }

    //TODO change delay to 2000 (Debug reasons)
    @Background(delay=0)
    void startMainOrLoginActivity() {
        if((!districtsReady && !categoriesReady && !userReady) || startActivity) return;
        startActivity = true;
        MainActivity_.intent(this).start();

        /*if(isLoggedIn()) {
            MainActivity_.intent(this).start();
        } else {
            AddUserActivity_.intent(this).start();
        }*/

        this.finish();
    }

}
