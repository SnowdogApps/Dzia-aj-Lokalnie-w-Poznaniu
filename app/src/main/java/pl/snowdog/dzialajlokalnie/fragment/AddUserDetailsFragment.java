package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.FragmentAddUserDetailsBinding;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.util.NotEmptyValidator;
import pl.snowdog.dzialajlokalnie.util.PasswordValidator;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_user_details)
public class AddUserDetailsFragment extends AddBaseFragment {
    private static final String TAG = "AddUserDetailsFragment";

    @ViewById(R.id.rootView)
    View rootView;

    @ViewById(R.id.etName)
    EditText etName;

    @ViewById(R.id.etSurname)
    EditText etSurname;

    @ViewById(R.id.etEmail)
    EditText etEmail;

    @ViewById(R.id.etPassword)
    EditText etPassword;

    @ViewById(R.id.etDescription)
    EditText etDescription;

    @ViewById(R.id.etNameWrapper)
    TextInputLayout etNameWrapper;

    @ViewById(R.id.etSurnameWrapper)
    TextInputLayout etSurnameWrapper;

    @ViewById(R.id.etEmailWrapper)
    TextInputLayout etEmailWrapper;

    @ViewById(R.id.etPasswordWrapper)
    TextInputLayout etPasswordWrapper;

    @ViewById(R.id.etDescriptionWrapper)
    TextInputLayout etDescriptionWrapper;

    @FragmentArg
    CreateNewObjectEvent mEditedObject;


    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if (validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .name(etName.getText().toString())
                    .surname(etSurname.getText().toString())
                    .email(etEmail.getText().toString())
                    .password(etPassword.getText().toString())
                    .description(etDescription.getText().toString())
                    .type(CreateNewObjectEvent.Type.details);

            EventBus.getDefault().post(builder.build());
        }
    }

    boolean validateInput() {
        boolean validPassword = true;
        boolean validName = validateName();
        boolean validSurname = validateSurname();
        boolean validEmail = validateEmail();

        //Normal password validation if not in edit mode
        if (mEditedObject == null) {
            validPassword = validatePassword();
        } else {
            //If we're editing the password, and user added new password, validate it
            if (etPassword.getText().toString().length() > 0) {
                validPassword = validatePassword();
            }
        }
        boolean validDescription = validateDescription();

        if (validName && validSurname && validEmail && validPassword && validDescription) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateName() {
        return NotEmptyValidator.validate(etName, etNameWrapper, R.string.warning_fill_name);
    }

    private boolean validateSurname() {
        return NotEmptyValidator.validate(etSurname, etSurnameWrapper, R.string.warning_fill_surname);
    }

    private boolean validateEmail() {
        boolean isInputValid = true;
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
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
        if (etPassword.getText().toString().trim().length() == 0
                || new PasswordValidator().validate(etPassword.getText().toString()) == false) {
            etPasswordWrapper.setErrorEnabled(true);
            etPasswordWrapper.setError(getString(R.string.warning_fill_password));
            isInputValid = false;
        } else {
            etPasswordWrapper.setErrorEnabled(false);
        }
        return isInputValid;
    }

    private boolean validateDescription() {
        return true;
        //TODO to validate description or not to validate description?
//        return NotEmptyValidator.validate(etDescription, etDescriptionWrapper, R.string.warning_fill_description);
    }

    @TextChange(R.id.etName)
    void onTextChangeName(TextView tv, CharSequence text) {
        if (text.length() > 0) {
            validateName();
        }
    }

    @TextChange(R.id.etSurname)
    void onTextChangeSurname(TextView tv, CharSequence text) {
        if (text.length() > 0) {
            validateSurname();
        }
    }

    @TextChange(R.id.etEmail)
    void onTextChangeEmail(TextView tv, CharSequence text) {
        if (text.length() > 0) {
            validateEmail();
        }
    }

    @TextChange(R.id.etPassword)
    void onTextChangePassword(TextView tv, CharSequence text) {
        if (text.length() > 0) {
            validatePassword();
        }
    }

    @TextChange(R.id.etDescription)
    void onTextChangeDescription(TextView tv, CharSequence text) {
        if (text.length() > 0) {
            validateDescription();
        }
    }

    @AfterViews
    void afterViewsCreated() {
        btnPrev.setVisibility(View.GONE);

        FragmentAddUserDetailsBinding binding = DataBindingUtil.bind(rootView);
        binding.setEditedObject(mEditedObject);

        if (mEditedObject != null) {
            //    etPassword.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean isImplementingEventBus() {
        return false;
    }
}
