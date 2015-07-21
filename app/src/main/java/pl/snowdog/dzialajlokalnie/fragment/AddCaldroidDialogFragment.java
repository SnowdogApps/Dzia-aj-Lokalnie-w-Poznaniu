package pl.snowdog.dzialajlokalnie.fragment;

import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.events.DateSetEvent;
import pl.snowdog.dzialajlokalnie.model.DateWrapper;

/**
 * Created by chomi3 on 2015-07-15.
 */
@EFragment(R.layout.dialog_fragment_add_caldroid)
public class AddCaldroidDialogFragment extends DialogFragment {
    public static final String TAG = "AddCaldroidFragment";
    CaldroidFragment caldroidFragment;

    @FragmentArg
    DateWrapper startDate;

    @FragmentArg
    DateWrapper endDate;

    @AfterViews
    void afterViews() {
        setupCaldroidFragment();
        getDialog().setTitle(getString(R.string.set_date));
    }

    @Click(R.id.btnDone)
    void onDoneClicked() {
        if(validateDates()) {
            EventBus.getDefault().post(new DateSetEvent(startDate.getDate(), endDate.getDate()));
            dismiss();
        } else {
            Snackbar.make(getView(), getString(R.string.warning_fill_date), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private boolean validateDates() {
        //We only need to have one date, endDate is optional
        return startDate != null ? true : false;
    }

    @Click(R.id.btnCancel)
    void onCancelClicked() {
        dismiss();
    }

    private void setupCaldroidFragment() {
        final SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.simple_date_format));

        if (caldroidFragment == null) {
            caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, false);
            args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

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
                if(startDate.getDate() == null || startDate.getDate().compareTo(endDate.getDate()) != 0) {
                    startDate.setDate(date);
                    endDate.setDate(date);
                }

                //Second touch - add second date to range
                if(startDate.getDate().compareTo(endDate.getDate()) == 0) {
                    if(date.after(startDate.getDate())) {
                        endDate.setDate(date);
                    } else {
                        endDate.setDate(startDate.getDate());
                        startDate.setDate(date);
                    }
                }
                caldroidFragment.setSelectedDates(startDate.getDate(), endDate.getDate());
                caldroidFragment.refreshView();
                //caldroidFragment.setSelectedDates(startDate, endDate);
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

        caldroidFragment.setCaldroidListener(listener);
        try {
            Log.d(TAG, "dtdbg before clicks:  " + startDate.getDate().toString() + " endDate: " + endDate.getDate().toString());
        } catch (Exception e) {
            Log.d(TAG, "dtdbg before clicks dates not set");
        }

        //Temporarily store date so time won't be changed
        DateWrapper tempEndDate = null;
        if(endDate != null) {
            tempEndDate = new DateWrapper(endDate.getDate());
        }
        if(startDate != null) {
            caldroidFragment.getCaldroidListener().onSelectDate(startDate.getDate(), null);
            Log.d(TAG, "dtdbg performStartDateClick "+startDate.getDate().toString());
        } else {
            startDate = new DateWrapper();
        }
        if(tempEndDate != null) {
            caldroidFragment.getCaldroidListener().onSelectDate(tempEndDate.getDate(), null);
            Log.d(TAG, "dtdbg performEndDateClick " + tempEndDate.getDate().toString());
        } else {
            endDate = new DateWrapper();
        }
    }

    @UiThread(delay=5)
    void performStartDateClick() {

    }

    @UiThread(delay=100)
    void performEndDateClick() {

    }

}
