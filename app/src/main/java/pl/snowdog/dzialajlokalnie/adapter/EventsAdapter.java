package pl.snowdog.dzialajlokalnie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.ItemEventBinding;
import pl.snowdog.dzialajlokalnie.model.Event;

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

        Picasso.with(viewHolder.binding.getRoot().getContext()).load(event.getPhotoEventUri()).error(
                R.drawable.ic_editor_insert_emoticon).into(viewHolder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemEventBinding binding;

        public ViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
