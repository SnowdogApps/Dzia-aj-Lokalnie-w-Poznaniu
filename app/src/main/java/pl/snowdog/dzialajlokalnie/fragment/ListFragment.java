package pl.snowdog.dzialajlokalnie.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;

@EFragment(R.layout.fragment_list)
public abstract class ListFragment extends BaseFragment {

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @AfterViews
    protected void afterTestBaseFragmentViews() {
        afterView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    protected abstract void afterView();
}
