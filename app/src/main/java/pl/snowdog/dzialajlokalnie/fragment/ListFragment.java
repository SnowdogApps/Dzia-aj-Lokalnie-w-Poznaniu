package pl.snowdog.dzialajlokalnie.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.events.FilterChangedEvent;

@EFragment(R.layout.fragment_list)
public abstract class ListFragment extends BaseFragment {

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @AfterViews
    protected void afterTestBaseFragmentViews() {
        afterView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }

    protected abstract void afterView();

    protected abstract void refreshItems();

    protected void onItemsLoadComplete() {
        swipeRefreshLayout.setRefreshing(false);
    };

    public void onEvent(FilterChangedEvent event) {
        refreshItems();
    }
}
