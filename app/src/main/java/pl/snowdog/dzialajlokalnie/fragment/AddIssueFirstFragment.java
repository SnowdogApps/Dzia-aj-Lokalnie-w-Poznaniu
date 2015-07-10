package pl.snowdog.dzialajlokalnie.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_first)
public class AddIssueFirstFragment extends AddIssueBaseFragment {
    public static final int MODE_EVENT = 0;
    public static final int MODE_ISSUE = 1;

    @ViewById(R.id.etTitle)
    EditText etTitle;

    @ViewById(R.id.etDescription)
    EditText etDescription;

    @ViewById(R.id.etTitleWrapper)
    TextInputLayout etTitleWrapper;

    @ViewById(R.id.etDescriptionWrapper)
    TextInputLayout etDescriptionWrapper;

    @FragmentArg
    int mAddingMode;

    CaldroidFragment caldroidFragment;

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
            if(oldDate == null) {
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
            setupCaldroid();
        }
        btnPrev.setVisibility(View.GONE);
        //updateNextButton();
    }

    private Date oldDate;
    private Date newDate;

    private void setupCaldroid() {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        if (caldroidFragment == null) {
            caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.caldroid, caldroidFragment);
        t.commit();


        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getActivity(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();

                //First touch or touch when range is selected - reset
                if(oldDate == null || oldDate.compareTo(newDate) != 0) {
                    oldDate = date;
                    newDate = date;
                }

                //Second touch - add second date to range
                if(oldDate.compareTo(newDate) == 0) {
                    if(date.after(oldDate)) {
                        newDate = date;
                    } else {
                        newDate = oldDate;
                        oldDate = date;
                    }
                }
                caldroidFragment.setSelectedDates(oldDate, newDate);
                caldroidFragment.refreshView();
                //caldroidFragment.setSelectedDates(oldDate, newDate);
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
    }
}
