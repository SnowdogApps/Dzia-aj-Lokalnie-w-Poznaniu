package pl.snowdog.dzialajlokalnie.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;

@EFragment(R.layout.fragment_list)
public class ListFragment extends Fragment {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }


    @AfterViews
    void afterViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
