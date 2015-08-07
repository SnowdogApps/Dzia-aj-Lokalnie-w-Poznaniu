package pl.snowdog.dzialajlokalnie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.ItemEventBinding;
import pl.snowdog.dzialajlokalnie.events.EventAttendEvent;
import pl.snowdog.dzialajlokalnie.events.EventClickedEvent;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.ParticipateEvent;
import pl.snowdog.dzialajlokalnie.util.CircleTransform;

/**
 * Created by bartek on 01.07.15.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<Event> events;

    public EventsAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemEventBinding binding = ItemEventBinding.
                inflate(LayoutInflater.from(viewGroup.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Event event = events.get(i);
        viewHolder.binding.setEvent(event);

        Picasso.with(viewHolder.binding.getRoot().getContext()).
                load(String.format(DlApi.PHOTO_NORMAL_URL, event.getPhotoEventUri())).
                error(R.drawable.ic_editor_insert_emoticon).
                into(viewHolder.binding.ivAvatar);

        Picasso.with(viewHolder.binding.getRoot().getContext()).
                load(String.format(DlApi.AVATAR_THUMB_URL, event.getAuthorAvatar())).
                error(R.drawable.ic_editor_insert_emoticon).
                transform(new CircleTransform()).
                into(viewHolder.binding.ivAuthorAvatar);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemEventBinding binding;

        public ViewHolder(final ItemEventBinding binding) {
            super(binding.getRoot());

            binding.attendingWidget.ibAttend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(binding.getRoot().getContext(), binding.attendingWidget.ibAttend);
                    popupMenu.inflate(R.menu.menu_participate);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.attend:
                                    EventBus.getDefault().post(new EventAttendEvent(ViewHolder.this.binding.getEvent(), ParticipateEvent.ParcitipateType.attending));
                                    break;
                                case R.id.maybe:
                                    EventBus.getDefault().post(new EventAttendEvent(ViewHolder.this.binding.getEvent(), ParticipateEvent.ParcitipateType.maybe));
                                    break;
                                case R.id.decline:
                                    EventBus.getDefault().post(new EventAttendEvent(ViewHolder.this.binding.getEvent(), ParticipateEvent.ParcitipateType.declined));
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventClickedEvent(
                            ViewHolder.this.binding.getEvent().getEventID()));
                }
            });

            this.binding = binding;
        }
    }

    public List<Event> getEvents() {
        return events;
    }
}
