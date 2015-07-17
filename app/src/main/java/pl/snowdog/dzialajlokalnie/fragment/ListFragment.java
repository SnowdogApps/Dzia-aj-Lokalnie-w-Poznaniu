package pl.snowdog.dzialajlokalnie.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.events.ApiErrorEvent;
import pl.snowdog.dzialajlokalnie.events.FilterChangedEvent;
import pl.snowdog.dzialajlokalnie.events.NetworkErrorEvent;

@EFragment(R.layout.fragment_list)
public abstract class ListFragment extends BaseFragment {

    private static final String TAG = "ListFragment";
    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewById(R.id.emptyView)
    TextView emptyView;

    int emptyListText = R.string.empty_list;

    @AfterViews
    protected void afterTestBaseFragmentViews() {
        afterView();
        emptyView.setText(emptyListText);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }

    protected abstract void afterView();

    protected void refreshItems() {
        emptyView.setVisibility(View.GONE);
    }

    protected void onItemsLoadComplete() {
        if (recyclerView.getAdapter().getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

        stopRefreshing();
    }

    public void onEvent(FilterChangedEvent event) {
        Log.d(TAG, "onEvent " + event);
        refreshItems();
    }


    public void onEvent(NetworkErrorEvent event) {
        Log.d(TAG, "onEvent " + event);
        stopRefreshing();
    }

    public void onEvent(ApiErrorEvent event) {
        Log.d(TAG, "onEvent " + event);
        stopRefreshing();
    }

    private void stopRefreshing() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
