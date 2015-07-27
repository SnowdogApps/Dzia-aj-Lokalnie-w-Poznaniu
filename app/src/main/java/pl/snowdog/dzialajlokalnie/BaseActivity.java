package pl.snowdog.dzialajlokalnie;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.EventClickedEvent;
import pl.snowdog.dzialajlokalnie.events.IssueClickedEvent;
import pl.snowdog.dzialajlokalnie.events.NetworkErrorEvent;
import pl.snowdog.dzialajlokalnie.events.ApiErrorEvent;
import pl.snowdog.dzialajlokalnie.model.Category;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Login;
import pl.snowdog.dzialajlokalnie.model.Session;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 06.07.15.
 */
@EActivity//(R.layout.activity_main)
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @ViewById(R.id.main_content)
    protected View coordinatorLayout;

    @AfterViews
    protected void afterBaseActivityViews() {


        //you can call there everything for all descendant activities that you normally call in onCreate method
        afterView();
    }

    protected abstract void afterView();


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(NetworkErrorEvent event) {
        Log.d(TAG, "NetworkErrorEvent " + event);
        Snackbar.make(coordinatorLayout, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
    }

    public void onEvent(ApiErrorEvent event) {
        Log.d(TAG, "ApiErrorEvent " + event);

        switch (event.getStatus()) {
            case 401:
                showLoginSnackbar();
                break;
            case 403:
                Snackbar.make(coordinatorLayout, getString(R.string.forbidden_error), Snackbar.LENGTH_SHORT).show();
                break;
            case 404:
                Snackbar.make(coordinatorLayout, getString(R.string.not_found_error), Snackbar.LENGTH_SHORT).show();
                break;
            default:
                if (event.getStatus() >= 500) {
                    Snackbar.make(coordinatorLayout, getString(R.string.server_error), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(coordinatorLayout, getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show();
                }
        }
    }

    protected void showLoginSnackbar() {
        Snackbar.make(coordinatorLayout, getString(R.string.unauthorized_error), Snackbar.LENGTH_LONG).
                setAction(R.string.login_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO go to login activity
                        LoginActivity_.intent(BaseActivity.this).start();
                        //AddUserActivity_.intent(BaseActivity.this).start();
                        Log.d(TAG, "login_action from snackbar ");
                    }
                }).show();
    }

    public void onEvent(IssueClickedEvent event) {
        Log.d(TAG, "onEvent " + event);
        startDetailsActivity(DlApi.ParentType.issues, event.getId());
    }

    public void onEvent(EventClickedEvent event) {
        Log.d(TAG, "onEvent " + event);
        startDetailsActivity(DlApi.ParentType.events, event.getId());
    }

    private void startDetailsActivity(DlApi.ParentType type, int id) {
        Intent intent = new Intent(this, DetailsActivity_.class);
        intent.putExtra("objType", type);
        intent.putExtra("objId", id);
        startActivity(intent);
    }

    protected void comment(DlApi.ParentType parentType, int parentID, int solution, String text) {
        int intParentType;

        switch (parentType){
            case issues:
                intParentType = 1;
                break;
            case events:
                intParentType = 2;
                break;
            default: //comments
                intParentType = 3;
                break;
        }

        DlApplication.commentApi.comment(new Comment(intParentType, parentID, solution, text), new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                Log.d(TAG, "comment success: " + comment);

                commentResult(comment);
                comment.save();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "comment failure: " + error);
            }
        });
    }

    protected void commentResult(Comment comment) { }

    protected void getCategories() {
        DlApplication.baseApi.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {
                Log.d(TAG, "getCategories success: " + categories);
                categoriesResult(categories);

                new Delete().from(Category.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (Category category : categories) {
                        category.save();
//                        Log.d(TAG, "getCategories success: " + category);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getCategories failure: " + error);
            }
        });
    }

    protected void categoriesResult(List<Category> categories) {
        // implement by override
    }

    protected void districtsResult(List<District> districts) {
        // implement by override
    }

    protected void getDistricts() {
        DlApplication.baseApi.getDistricts(new Callback<List<District>>() {
            @Override
            public void success(List<District> districts, Response response) {
                Log.d(TAG, "getDistricts success: " + districts);
                districtsResult(districts);
                new Delete().from(District.class).execute();

                ActiveAndroid.beginTransaction();
                try {
                    for (District district : districts) {
                        district.save();
//                        Log.d(TAG, "getDistricts success: " + district);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getDistricts failure: " + error);
            }
        });
    }

    protected void login(String username, String pass) {
        DlApplication.userApi.login(new Login(username, pass, 2), new Callback<Session>() {
            @Override
            public void success(Session session, Response response) {
                Log.d(TAG, "login success: " + session);

                new Delete().from(Session.class).execute();

                session.save();

                DlApplication.refreshCurrentSession();

                loginResult(session);
            }

            @Override
            public void failure(RetrofitError error) {
                loginResult(null);
                Log.d(TAG, "login failure: " + error);
            }
        });
    }

    protected void loginResult(Session session) {
        if(session != null) {
            Snackbar.make(coordinatorLayout, getString(R.string.login_success), Snackbar.LENGTH_LONG).show();
            DlApplication.createDlRestAdapter();
        }
    }

    protected boolean isLoggedIn() {
        return DlApplication.currentSession != null && DlApplication.currentSession.getSsid() != null;
    }

    protected void logout() {
        new Delete().from(Session.class).execute();
        DlApplication.currentSession = null;
        Snackbar.make(coordinatorLayout, getString(R.string.logout_success), Snackbar.LENGTH_LONG).show();
    }
}
