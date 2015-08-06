package pl.snowdog.dzialajlokalnie;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.io.File;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.adapter.FragmentAdapter;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
import pl.snowdog.dzialajlokalnie.fragment.AddImageFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddLocationFragment_;
import pl.snowdog.dzialajlokalnie.fragment.AddUserDetailsFragment_;
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
@EActivity(R.layout.activity_add)
public class AddUserActivity extends AddBaseActivity {
    private static final String TAG = "AddUserActivity";
    public static final int MODE_SIGN_UP = 1234;
    public static final int MODE_SIGN_UP_FACEBOOK = 12373;

    //Event specific fields:
    String name;
    String surname;
    String email;
    String password;

    @Extra
    User mEditedUser;

    @Override
    void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        mViewPager.setOffscreenPageLimit(3);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.addFragment(new AddUserDetailsFragment_().builder()
                .mEditedObject(mEditedUser != null ? new CreateNewObjectEvent.Builder()
                        .name(mEditedUser.getName())
                        .surname(mEditedUser.getSurname())
                        .description(mEditedUser.getDescription())
                        .build() : null)
                .build());

        adapter.addFragment(new AddLocationFragment_().builder()
                .mEditedObject(mEditedUser != null ? new CreateNewObjectEvent.Builder()
                        .districtID(mEditedUser.getDistrictID())
                        .build() : null)
                .mMode(MODE_SIGN_UP)
                .build());

        adapter.addFragment(new AddImageFragment_().builder()
                .mEditedObject(mEditedUser != null ? new CreateNewObjectEvent.Builder()
                        .image(mEditedUser.getAvatarUri())
                        .build() : null)
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
        if (mEditedUser != null) {
            getSupportActionBar().setTitle(getString(R.string.edit_user));
        }
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
                if (mEditedUser == null) {
                    postNewUser();
                } else {
                    putEditedUser();
                }
                return;
        }
        super.onEvent(event);
    }

    protected void putEditedUser() {
        toggleProgressWheel(true);
        NewUser newUser = createNewUserObject();

        DlApplication.userApi.putUser(newUser, DlApplication.currentSession.getUserID(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(TAG, "userApi.sendRegistrationToServer post success: " + response + " user: " + user.toString());
                ActiveAndroid.beginTransaction();
                try {
                    user.save();
                    ActiveAndroid.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                //If we have successfull login, we can try to upload avatar
                if (photoUri != null && photoUri.length() > 0) {
                    postUserAvatar(user.getUserID());
                } else {
                    //Finished adding, close view
                    finishAdding(ObjectAddedEvent.Type.user);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "userApi.postNewUser post error: " + error);
                toggleProgressWheel(false);
            }
        });
    }

    protected void postNewUser() {
        toggleProgressWheel(true);
        NewUser newUser = createNewUserObject();

        DlApplication.userApi.postNewUser(newUser, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                ActiveAndroid.beginTransaction();
                try {
                    user.setEmail(email);
                    user.save();
                    ActiveAndroid.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ActiveAndroid.endTransaction();
                }
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
        super.loginResult(session);

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

    protected void postUserAvatar(int userId) {

        TypedFile file = new TypedFile("image/jpg", new File(photoUri));

        DlApplication.userApi.postUserAvatar(file, userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d(TAG, "userApi.postUserAvatar success: " + response + " eventApi: " + user.toString());
                //toggleProgressWheel(false);

                ActiveAndroid.beginTransaction();
                try {
                    user.save();
                    ActiveAndroid.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ActiveAndroid.endTransaction();
                }

                List<User> users = new Select().from(User.class).execute();
                for (User u : users) {
                    Log.d(TAG, "usrdbg user: " + u.toString());
                }

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
    protected NewUser createNewUserObject() {
        NewUser newUser = new NewUser();
        newUser.setDistrictID(districtID > 0 ? districtID : null);
        newUser.setDescription(description != null && description.length() > 0 ? description : null);
        newUser.setName(name != null && name.length() > 0 ? name : null);
        newUser.setSurname(surname != null && surname.length() > 0 ? surname : null);
        newUser.setEmail(email != null && email.length() > 0 ? email : null);
        newUser.setPass(password != null && password.length() > 0 ? password : null);
        newUser.setPushRegId(pref.pushRegId().get());

        return newUser;
    }
}
