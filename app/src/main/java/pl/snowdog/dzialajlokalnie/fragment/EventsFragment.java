package pl.snowdog.dzialajlokalnie.fragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.EventsAdapter;
import pl.snowdog.dzialajlokalnie.model.Event;

/**
 * Created by bartek on 07.07.15.
 */
@EFragment(R.layout.fragment_list)
public class EventsFragment extends ListFragment {

    EventsAdapter adapter;

    @Override
    protected void afterView() {
        getEvents();
    }

    @Override
    protected void refreshItems() {
        getEvents();
    }

    @Override
    protected void eventsResult(List<Event> events) {
        adapter = new EventsAdapter(events);
        recyclerView.setAdapter(adapter);
    }
}
