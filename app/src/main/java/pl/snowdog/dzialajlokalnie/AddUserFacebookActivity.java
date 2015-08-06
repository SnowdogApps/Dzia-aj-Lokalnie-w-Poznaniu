package pl.snowdog.dzialajlokalnie;

import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment_;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add)
public class AddUserFacebookActivity extends AddUserActivity {
    private static final String TAG = "AddUserFacebookActivity";

    @Extra
    int userID;

    @Override
    void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager facebook");
        Locale l = Locale.getDefault();
        mViewPager.setOffscreenPageLimit(1);
        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new AddLocationFragment_().builder()
                .mMode(MODE_SIGN_UP_FACEBOOK)
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    protected void putEditedFacebookUser() {
        toggleProgressWheel(true);
        NewUser newUser = createNewUserObject();

        DlApplication.userApi.putUser(newUser, userID, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(TAG, "userApi.putEditedFacebookUser put success: " + response + " user: " + user.toString());

                user.save();

                //Finished adding, close view
                finishAdding(ObjectAddedEvent.Type.user);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "userApi.postNewUser post error: " + error);
                toggleProgressWheel(false);
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(getString(R.string.warning_localization_dialogi_message))
                .setTitle(getString(R.string.warning_localization_dialog_title));
        // Add the buttons
        builder.setPositiveButton(getString(R.string.warning_localization_dialog_positive), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //LoginManager.getInstance().logOut();
                logout();
                AddUserFacebookActivity.this.finish();
            }
        });
        builder.setNegativeButton(getString(R.string.warning_localization_dialog_negative), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onEvent(CreateNewObjectEvent event) {
        switch (event.getType()) {

            case location:
                districtID = event.getDistrictID();
                putEditedFacebookUser();
                return;
        }
        super.onEvent(event);
    }
}
