package pl.snowdog.dzialajlokalnie.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by bartek on 09.07.15.
 */
@EFragment(R.layout.fragment_filter)
public class FilterFragment extends DialogFragment {

    @ViewById(R.id.spDistrict)
    Spinner spinner;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.btSet)
    Button setButton;

    @ViewById(R.id.btCancel)
    Button cancelButton;


    @AfterViews
    void afterViews() {

    }
}
