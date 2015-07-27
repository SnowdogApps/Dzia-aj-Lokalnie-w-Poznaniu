package pl.snowdog.dzialajlokalnie;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddCategoriesFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddImageFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment;
import pl.snowdog.dzialajlokalnie.fragment.AddTitleDateFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddUserDetailsFragment_;
import pl.snowdog.dzialajlokalnie.model.DateWrapper;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.NewEvent;
import pl.snowdog.dzialajlokalnie.model.NewUser;
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.model.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EActivity(R.layout.activity_add_issue)
public class AddUserActivity extends AddBaseActivity {
    private static final String TAG = "AddUserActivity";
    public static final int MODE_SIGN_UP = 1234;
    //Event specific fields:
    String name;
    String surname;
    String email;
    String password;

    @Override
    void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Locale l = Locale.getDefault();
        mViewPager.setOffscreenPageLimit(3);
        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(new AddUserDetailsFragment_().builder()
                .build());

        adapter.addFragment(new AddLocationFragment_().builder()
                .mMode(MODE_SIGN_UP)
                .build());

        adapter.addFragment(new AddImageFragment_().builder()
                .mMode(MODE_SIGN_UP)
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
    }

    public void onEvent(CreateNewObjectEvent event) {
        switch (event.getType()) {

            case details:
                name = event.getName();
                surname = event.getSurname();
                email = event.getEmail();
                password = event.getPassword();
                description = event.getDescription();
                goToNextPage();
                return;
            case image:
                photoUri = event.getImage();
                postNewUser();
                return;
        }
        super.onEvent(event);
    }

    private void postNewUser() {
        toggleProgressWheel(true);
        NewUser newUser = createNewUserObject();

        DlApplication.userApi.postNewUser(newUser, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                //Login as new user:
                login(email, password);


                Log.d(TAG, "userApi.postNewUser post success: " + response + " user: " + user.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "userApi.postNewUser post error: " + error);
                toggleProgressWheel(false);
            }
        });
    }


    @Override
    protected void loginResult(Session session) {


        List<Session> dbSessions = new Select().from(Session.class).execute();

        for (Session s : dbSessions) {
            Log.d(TAG, "loginResult " + s);
        }
        //If we have successfull login, we can try to upload avatar
        if (photoUri != null && photoUri.length() > 0) {
            postUserAvatar(session.getUserID());
        } else {
            //Finished adding, close view
            finishAdding(ObjectAddedEvent.Type.user);
        }
    }

    private void postUserAvatar(int userId) {

        TypedFile file = new TypedFile("image/jpg", new File(photoUri));

        DlApplication.userApi.postUserAvatar(file, userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(TAG, "userApi.postUserAvatar success: " + response + " eventApi: " + user.toString());
                //toggleProgressWheel(false);

                finishAdding(ObjectAddedEvent.Type.user);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "userApi.postUserAvatar error: " + error);
                toggleProgressWheel(false);
            }
        });
    }

    @NonNull
    private NewUser createNewUserObject() {
        NewUser newUser = new NewUser();
        newUser.setDistrictID(districtID);
        newUser.setDescription(description);
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPushRegId("");

        return newUser;
    }


}
