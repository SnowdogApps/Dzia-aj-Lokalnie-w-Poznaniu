package pl.snowdog.dzialajlokalnie;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
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
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.TextChange;
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
import pl.snowdog.dzialajlokalnie.model.Session;
import pl.snowdog.dzialajlokalnie.util.PasswordValidator;

/**
 * Created by chomi3 on 2015-07-23.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    protected ApiActionDialogFragment mApiActionDialogFragment;

    @ViewById(R.id.etEmail)
    EditText etEmail;

    @ViewById(R.id.etPassword)
    EditText etPassword;

    @ViewById(R.id.etEmailWrapper)
    TextInputLayout etEmailWrapper;

    @ViewById(R.id.etPasswordWrapper)
    TextInputLayout etPasswordWrapper;

    @Click(R.id.btnLogin)
    void onLoginClicked() {
        if(validateInput()) {
            toggleProgressWheel(true);
            login(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    @Click(R.id.btnRegister)
    void onRegisterClicked() {
        AddUserActivity_.intent(this).start();
        this.finish();
    }

    @Click(R.id.btnLoginFacebook)
    void onFacebookLoginClicked() {

    }

    @Override
    protected void loginResult(Session session) {
        toggleProgressWheel(false);
        super.loginResult(session);
        if(session != null) {
            this.finish();
        }
    }

    @Override
    protected void afterView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @TextChange(R.id.etEmail)
    void onTextChangeEmail(TextView tv, CharSequence text) {
        if(text.length() > 0) {
            validateEmail();
        }
    }

    @TextChange(R.id.etPassword)
    void onTextChangePassword(TextView tv, CharSequence text) {
        if(text.length() > 0) {
            validatePassword();
        }
    }

    boolean validateInput() {
        boolean validEmail = validateEmail();
        boolean validPassword = validatePassword();

        if(validEmail && validPassword) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateEmail() {
        boolean isInputValid = true;
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmailWrapper.setErrorEnabled(true);
            etEmailWrapper.setError(getString(R.string.warning_fill_email));
            isInputValid = false;
        } else {
            etEmailWrapper.setErrorEnabled(false);
        }
        return isInputValid;
    }

    private boolean validatePassword() {
        boolean isInputValid = true;
        if(etPassword.getText().toString().length() == 0
                || new PasswordValidator().validate(etPassword.getText().toString()) == false) {
            etPasswordWrapper.setErrorEnabled(true);
            etPasswordWrapper.setError(getString(R.string.warning_fill_password));
            isInputValid = false;
        } else {
            etPasswordWrapper.setErrorEnabled(false);
        }
        return isInputValid;
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



    @OptionsItem(android.R.id.home)
    void homeSelected() {
        this.finish();
    }
}
