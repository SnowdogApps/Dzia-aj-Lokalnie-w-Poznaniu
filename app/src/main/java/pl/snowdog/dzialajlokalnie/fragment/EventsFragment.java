package pl.snowdog.dzialajlokalnie.fragment;

import android.util.Log;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.EventsAdapter;
import pl.snowdog.dzialajlokalnie.events.EventAttendEvent;
import pl.snowdog.dzialajlokalnie.model.Event;

/**
 * Created by bartek on 07.07.15.
 */
@EFragment(R.layout.fragment_list)
public class EventsFragment extends ListFragment {

    private static final String TAG = "EventsFragment";
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

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(EventAttendEvent event) {
        Log.d(TAG, "onEvent " + event);
    }
}
