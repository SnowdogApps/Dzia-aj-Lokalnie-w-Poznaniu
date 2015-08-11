package pl.snowdog.dzialajlokalnie.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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
@EFragment(R.layout.dialog_fragment_api_action)
public class ApiActionDialogFragment extends DialogFragment {
    public static final String TAG = "ApiActionDialogFragment";

    @ViewById(R.id.status_info)
    TextView statusInfo;

    @AfterViews
    void afterViews() {
        getDialog().setTitle(R.string.please_wait_info);
        setCancelable(false);
    }

}
