package pl.snowdog.dzialajlokalnie.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.ItemEventBinding;
import pl.snowdog.dzialajlokalnie.databinding.ItemIssueBinding;
import pl.snowdog.dzialajlokalnie.events.EventClickedOnMapEvent;
import pl.snowdog.dzialajlokalnie.events.IssueClickedOnMapEvent;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 14.07.15.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    public static class OnClickListener implements GoogleMap.OnInfoWindowClickListener {

        private HashMap<String, Event> markerEventMap;
        private HashMap<String, Issue> markerIssueMap;

        public OnClickListener(HashMap<String, Event> markerEventMap,
                               HashMap<String, Issue> markerIssueMap) {
            this.markerEventMap = markerEventMap;
            this.markerIssueMap = markerIssueMap;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            if (markerIssueMap.containsKey(marker.getId())) {
                EventBus.getDefault().post(new IssueClickedOnMapEvent(
                        markerIssueMap.get(marker.getId()).getIssueID()));
            } else if (markerEventMap.containsKey(marker.getId())) {
                EventBus.getDefault().post(new EventClickedOnMapEvent(
                        markerEventMap.get(marker.getId()).getEventID()));
            }
        }
    }

    private Context context;
    private Resources res;
    private HashMap<String, Event> markerEventMap;
    private HashMap<String, Issue> markerIssueMap;
    private OnClickListener onClickListener;

    public MapInfoWindowAdapter(Context context) {

        this.context = context;
        this.res = context.getResources();
        markerEventMap = new HashMap<>();
        markerIssueMap = new HashMap<>();
        onClickListener = new OnClickListener(markerEventMap, markerIssueMap);
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void putIssue(Marker marker, Issue issue) {
        markerIssueMap.put(marker.getId(), issue);
    }

    public void putEvent(Marker marker, Event event) {
        markerEventMap.put(marker.getId(), event);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        if (markerIssueMap.containsKey(marker.getId())) {
            Issue issue = markerIssueMap.get(marker.getId());

            final ItemIssueBinding binding = ItemIssueBinding.
                    inflate(LayoutInflater.from(context));
            binding.setIssue(issue);

            Picasso.with(binding.getRoot().getContext()).
                    load(String.format(DlApi.PHOTO_THUMB_URL, issue.getPhotoIssueUri())).
                    error(R.drawable.ic_editor_insert_emoticon).
                    into(binding.ivAvatar);

            binding.tvTitle.setText(issue.getTitle());
            binding.tvDesc.setText(issue.getDescription());
            binding.ratingWidget.ibRateUp.setVisibility(View.INVISIBLE);
            binding.ratingWidget.ibRateDown.setVisibility(View.INVISIBLE);
            binding.ratingWidget.tvRating.setText(String.valueOf(issue.getIssueRating()));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(res.getQuantityString(R.plurals.votes, issue.getVotesCount(), issue.getVotesCount()));
            stringBuilder.append(", ");
            stringBuilder.append(res.getQuantityString(R.plurals.comments, issue.getCommentsCount(), issue.getCommentsCount()));
            stringBuilder.append(", ");
            stringBuilder.append(res.getString(R.string.tags, issue.getCategoriesText()));
            binding.footerWidget.tvFooter.setText(stringBuilder.toString());

            //TODO problem with rendering because view is rendered as an image and does not update itself. Data binding won't work
            // https://developers.google.com/maps/documentation/android/infowindows#info_window_events
            return binding.getRoot();
        } else if (markerEventMap.containsKey(marker.getId())) {
            Event event = markerEventMap.get(marker.getId());

            final ItemEventBinding binding = ItemEventBinding.
                    inflate(LayoutInflater.from(context));
            binding.setEvent(event);

            Picasso.with(binding.getRoot().getContext()).
                    load(String.format(DlApi.PHOTO_THUMB_URL, event.getPhotoEventUri())).
                    error(R.drawable.ic_editor_insert_emoticon).
                    into(binding.ivAvatar);

            binding.tvTitle.setText(event.getTitle());
            binding.tvDesc.setText(event.getDescription());
            binding.attendingWidget.ibAttend.setVisibility(View.INVISIBLE);
            binding.attendingWidget.tvCount.setText(String.valueOf(event.getAttendingCount()));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(res.getQuantityString(R.plurals.invited, event.getInvitedCount(), event.getInvitedCount()));
            stringBuilder.append(", ");
            stringBuilder.append(res.getQuantityString(R.plurals.comments, event.getCommentsCount(), event.getCommentsCount()));
            stringBuilder.append(", ");
            stringBuilder.append(res.getString(R.string.tags, event.getCategoriesText()));
            binding.footerWidget.tvFooter.setText(stringBuilder.toString());

            return binding.getRoot();
        }

        return null;
    }
}
