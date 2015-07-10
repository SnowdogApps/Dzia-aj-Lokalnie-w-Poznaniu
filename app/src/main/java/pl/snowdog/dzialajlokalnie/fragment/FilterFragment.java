package pl.snowdog.dzialajlokalnie.fragment;


import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.DistrictAdapter;
import pl.snowdog.dzialajlokalnie.model.District;

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

    private DistrictAdapter adapter;


    @AfterViews
    void afterViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<District> districts = new Select().from(District.class).orderBy("name").execute();

        adapter = DistrictAdapter.build(getActivity(), districts);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
