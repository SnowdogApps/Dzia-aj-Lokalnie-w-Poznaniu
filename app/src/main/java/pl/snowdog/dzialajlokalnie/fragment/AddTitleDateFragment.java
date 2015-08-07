package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.FragmentAddTitleDateBinding;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.events.DateSetEvent;
import pl.snowdog.dzialajlokalnie.model.DateWrapper;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_title_date)
public class AddTitleDateFragment extends AddBaseFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "AddIssueTitleDateFragment";

    public static final int MODE_EVENT = 0;
    public static final int MODE_ISSUE = 1;
    private static final boolean FIRST_DATE_SETTING = true;
    private static final boolean LATTER_DATE_SETTING = false;


    @ViewById(R.id.rootView)
    View rootView;

    @ViewById(R.id.tvDateStartStatus)
    TextView tvDateStartStatus;

    @ViewById(R.id.tvDateStartTimeStatus)
    TextView tvDateStartTimeStatus;

    @ViewById(R.id.tvDateEndStatus)
    TextView tvDateEndStatus;

    @ViewById(R.id.tvDateEndTimeStatus)
    TextView tvDateEndTimeStatus;

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

        FragmentAddTitleDateBinding binding = DataBindingUtil.bind(rootView);
            binding.setEditedObject(mEditedObject);

        if(mEditedObject != null) {
            startDate = mEditedObject.getStartDate();
            endDate = mEditedObject.getEndDate();
            updateDateTextView();
        }
        //updateNextButton();
    }

    @Click(R.id.btnDateStart)
    void onDateStartClicked() {
        final Calendar now = Calendar.getInstance();
        if(startDate != null && startDate.getDate() != null) {
            now.setTime(startDate.getDate());
        } else {
            startDate = new DateWrapper();
            endDate = new DateWrapper();
        }
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfYear) {
                //First touch or touch when range is selected - reset
                Log.d(TAG, "clnddbg year: " + year + " monthOfYear: " + monthOfYear + " dayOfYear: " + dayOfYear);
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, monthOfYear);
                now.set(Calendar.DAY_OF_MONTH, dayOfYear);
                Log.d(TAG, "clnddbg calendar: "+now.getTime().toString());

                if(endDate.getDate() == null) {
                    startDate.setDate(now.getTime());
                    endDate.setDate(now.getTime());
                    openStartDateTimePicker(FIRST_DATE_SETTING);
                } else {
                    if(now.getTime().after(endDate.getDate())) {
                        //Startdate has to be before enddate, notify user, don't set date
                        Snackbar.make(getView(), getString(R.string.warning_fill_date_after), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        //Startdate fine, set it and get the time from the user
                        startDate.setDate(now.getTime());
                        openStartDateTimePicker(LATTER_DATE_SETTING);
                    }
                }


                /*if(startDate.getDate() == null || startDate.getDate().compareTo(endDate.getDate()) != 0) {
                    startDate.setDate(now.getTime());
                    endDate.setDate(now.getTime());
                }

                //Second touch - add second date to range
                if(startDate.getDate().compareTo(endDate.getDate()) == 0) {
                    if(now.getTime().after(startDate.getDate())) {
                        endDate.setDate(now.getTime());
                    } else {
                        endDate.setDate(startDate.getDate());
                        startDate.setDate(now.getTime());
                    }
                } */

            }
        };

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                dateListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        //AddCaldroidDialogFragment_.builder().startDate(startDate).endDate(endDate).build().show(getChildFragmentManager(), AddCaldroidDialogFragment.TAG);
    }



    private void openStartDateTimePicker(final boolean firstDateSetting) {
        final Calendar now = Calendar.getInstance();
        now.setTime(startDate.getDate());

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minutes) {
                //Set selected hour to
                now.set(Calendar.HOUR_OF_DAY, hour);
                now.set(Calendar.MINUTE, minutes);
                if(now.getTime().after(endDate.getDate()) && !firstDateSetting) {
                    //endDate has to be after startdate, prompt user, don't set time
                    Snackbar.make(getView(), getString(R.string.warning_fill_date_after), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    startDate.setDate(now.getTime());


                    if (firstDateSetting) {
                        //If enddate is not set yet, set it to +1

                        now.set(Calendar.HOUR_OF_DAY, hour + 1);
                        endDate.setDate(now.getTime());

                    }
                    updateDateTextView();
                }
            }
        };

        TimePickerDialog dpd = TimePickerDialog.newInstance(
                listener,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
    }



    private void openEndDateTimePicker() {
        final Calendar now = Calendar.getInstance();
        now.setTime(endDate.getDate());

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minutes) {
                now.set(Calendar.HOUR_OF_DAY, hour);
                now.set(Calendar.MINUTE, minutes);
                if(now.getTime().before(startDate.getDate())) {
                    //endDate has to be after startdate, prompt user, don't set time
                    Snackbar.make(getView(), getString(R.string.warning_fill_date_after), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    endDate.setDate(now.getTime());
                    updateDateTextView();
                }
            }
        };

        TimePickerDialog dpd = TimePickerDialog.newInstance(
                listener,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
    }


    @Click(R.id.btnDateEnd)
    void onDateEndClicked() {
        final Calendar now = Calendar.getInstance();
        if(startDate != null && startDate.getDate() != null) {
            now.setTime(endDate.getDate());
        } else {
            startDate = new DateWrapper();
            endDate = new DateWrapper();
        }
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfYear) {
                //First touch or touch when range is selected - reset
                Log.d(TAG, "clnddbg year: " + year + " monthOfYear: " + monthOfYear + " dayOfYear: " + dayOfYear);
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, monthOfYear);
                now.set(Calendar.DAY_OF_MONTH, dayOfYear);
                Log.d(TAG, "clnddbg calendar: "+now.getTime().toString());


                if(now.getTime().before(startDate.getDate())) {
                    //endDate has to be after startdate, prompt user, don't set time
                    Snackbar.make(getView(), getString(R.string.warning_fill_date_after), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //enddate fine, set it, get the time
                    endDate.setDate(now.getTime());
                    openEndDateTimePicker();
                }

            }
        };

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                dateListener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
        //AddCaldroidDialogFragment_.builder().startDate(startDate).endDate(endDate).build().show(getChildFragmentManager(), AddCaldroidDialogFragment.TAG);
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

        tvDateStartStatus.setText(getString(R.string.date_start)+ " "+startDate.getDateString(getActivity()));
        tvDateStartTimeStatus.setText(getString(R.string.date_start_time)+ " "+startDate.getTimeString(getActivity()));
        tvDateEndStatus.setText(getString(R.string.date_end)+ " "+endDate.getDateString(getActivity()));
        tvDateEndTimeStatus.setText(getString(R.string.date_end_time)+ " "+endDate.getTimeString(getActivity()));
        /*
        if(startDate.getDate().compareTo(endDate.getDate()) != 0) {
            tvDateStartStatus.setText(getString(R.string.date_start)+ " "+startDate.getDateString(getActivity()));
            tvDateStartTimeStatus.setText(getString(R.string.date_start_time)+ " "+startDate.getDateString(getActivity()));
            //tvDateStatus.setText(getString(R.string.date)+ " "+startDate.getDateString(getActivity())+ " - "+endDate.getDateString(getActivity()));
        } else {

            //tvDateStatus.setText(getString(R.string.date)+ " "+startDate.getDateString(getActivity()));
        }*/
    }


    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        updateDateTextView();
    }
}
