package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.FragmentAddIssueTitleDateBinding;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.DateSetEvent;
import pl.snowdog.dzialajlokalnie.model.DateWrapper;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_title_date)
public class AddIssueTitleDateFragment extends AddIssueBaseFragment {
    private static final String TAG = "AddIssueTitleDateFragment";

    public static final int MODE_EVENT = 0;
    public static final int MODE_ISSUE = 1;


    @ViewById(R.id.rootView)
    View rootView;

    @ViewById(R.id.tvDateStatus)
    TextView tvDateStatus;

    @ViewById(R.id.etTitle)
    EditText etTitle;

    @ViewById(R.id.etDescription)
    EditText etDescription;

    @ViewById(R.id.etTitleWrapper)
    TextInputLayout etTitleWrapper;

    @ViewById(R.id.etDescriptionWrapper)
    TextInputLayout etDescriptionWrapper;

    @ViewById(R.id.event_container)
    View vEventContainer;

    @FragmentArg
    int mAddingMode;

    CaldroidFragment caldroidFragment;


    private DateWrapper startDate;
    private DateWrapper endDate;

    @FragmentArg
    CreateNewObjectEvent mEditedObject;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .title(etTitle.getText().toString())
                    .description(etDescription.getText().toString())
                    .type(CreateNewObjectEvent.Type.title);
            if(mAddingMode == MODE_EVENT) {
                builder.startDate(startDate);
                builder.endDate(endDate);
                builder.type(CreateNewObjectEvent.Type.date);
            }
            EventBus.getDefault().post(builder.build());
            //((AddBaseActivity)getActivity()).goToNextPage();
        }
    }

    @Override
    boolean validateInput() {
        boolean validTitle = validateTitle();
        boolean validDescription = validateDescription();
        boolean validDate = validateDate();

        if(validTitle && validDescription && validDate) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateDate() {
        boolean validDate = true;
        if(mAddingMode == MODE_EVENT) {
            if(startDate == null) {
                validDate = false;
                Snackbar.make(getView(), getString(R.string.warning_fill_date), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        }
        return validDate;
    }

    private boolean validateTitle() {
        boolean isInputValid = true;
        if(etTitle.getText().toString().length() == 0) {
            etTitleWrapper.setErrorEnabled(true);
            etTitleWrapper.setError(getString(R.string.warning_fill_title));
            isInputValid = false;
        } else {
            etTitleWrapper.setErrorEnabled(false);
        }
        return isInputValid;
    }

    private boolean validateDescription() {
        boolean isInputValid = true;
        if(etDescription.getText().toString().length() == 0) {
            etDescriptionWrapper.setErrorEnabled(true);
            etDescriptionWrapper.setError(getString(R.string.warning_fill_description));
            isInputValid = false;
        } else {
            etDescriptionWrapper.setErrorEnabled(false);
        }
        return isInputValid;
    }

    @TextChange(R.id.etTitle)
    void onTextChangeTitle(TextView tv, CharSequence text) {
        if(text.length() > 0) {
            validateTitle();
        }
    }

    @TextChange(R.id.etDescription)
    void onTextChangeDescription(TextView tv, CharSequence text) {
        if(text.length() > 0) {
            validateDescription();
        }
    }


    @AfterViews
    void afterViewsCreated() {

        if(mAddingMode == MODE_EVENT) {
            vEventContainer.setVisibility(View.VISIBLE);
        }
        btnPrev.setVisibility(View.GONE);

        FragmentAddIssueTitleDateBinding binding = DataBindingUtil.bind(rootView);
        binding.setNewObject(mEditedObject);

        if(mEditedObject != null) {
            updateDateTextView();
        }
        //updateNextButton();
    }

    @Click(R.id.btnCaldroid)
    void onCaldroidClicked() {
        AddCaldroidFragment_.builder().startDate(startDate).endDate(endDate).build().show(getChildFragmentManager(), AddCaldroidFragment.TAG);
    }


    public void onEvent(DateSetEvent event) {
        if(event.getEndDate() != null) {
            if(endDate == null) {
                endDate = new DateWrapper(new Date());
            }
            endDate.setDate(event.getEndDate());

        }
        if(event.getStartDate() != null) {
            if(startDate== null) {
                startDate = new DateWrapper(new Date());
            }
            startDate.setDate(event.getStartDate());
        }
        updateDateTextView();
        /*Toast.makeText(getActivity(),"onDateSet: "+event.toString(),
                Toast.LENGTH_SHORT).show();*/
    }

    private void updateDateTextView() {
        if(startDate == null) return;

        if(startDate.getDate().compareTo(endDate.getDate()) != 0) {
            tvDateStatus.setText(getString(R.string.date)+ " "+startDate.getDateString(getActivity())+ " - "+endDate.getDateString(getActivity()));
        } else {
            tvDateStatus.setText(getString(R.string.date)+ " "+startDate.getDateString(getActivity()));
        }
    }


    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }
}
