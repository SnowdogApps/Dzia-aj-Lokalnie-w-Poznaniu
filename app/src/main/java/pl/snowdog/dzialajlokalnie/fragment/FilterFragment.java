package pl.snowdog.dzialajlokalnie.fragment;


import android.app.DialogFragment;
import android.support.v4.app.Fragment;
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
//TODO DialogFragment DialogStyle się wywala w AndroidAnnotations dlaczego?
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
