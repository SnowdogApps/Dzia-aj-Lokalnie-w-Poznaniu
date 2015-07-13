package pl.snowdog.dzialajlokalnie.fragment;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.HashMap;
import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.ItemEventBinding;
import pl.snowdog.dzialajlokalnie.databinding.ItemIssueBinding;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 13.07.15.
 */
@EFragment(R.layout.fragment_map)
public class MapWithApiFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    SupportMapFragment mapFragment;

    private GoogleMap map;
    private boolean centerOnUser;

    HashMap<String, Event> markerEventMap = new HashMap<String, Event>();
    HashMap<String, Issue> markerIssueMap = new HashMap<String, Issue>();

    @AfterViews
    public void afterViews() {
        centerOnUser = false;

        mapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().add(R.id.rl_container, mapFragment).commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.408333, 16.934167), 11));

        map.setOnMyLocationChangeListener(this);
        map.setOnCameraChangeListener(this);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                if (markerIssueMap.containsKey(marker.getTitle())) {
                    Issue issue = markerIssueMap.get(marker.getId());

                    final ItemIssueBinding binding = ItemIssueBinding.
                            inflate(LayoutInflater.from(mapFragment.getActivity()));
                    binding.setIssue(issue);

                    Picasso.with(binding.getRoot().getContext()).
                            load(String.format(DlApi.PHOTO_THUMB_URL, issue.getPhotoIssueUri())).
                            error(R.drawable.ic_editor_insert_emoticon).
                            into(binding.ivAvatar);

                    binding.getRoot().post(new Runnable() {
                        @Override
                        public void run() {
                            marker.showInfoWindow();
                        }
                    });

                    //TODO problem with rendering because view is rendered as an image and does not update itself. Data binding won't work
                    // https://developers.google.com/maps/documentation/android/infowindows#info_window_events
                    return binding.getRoot();
                } else {
                    Event event = markerEventMap.get(marker.getId());

                    final ItemEventBinding binding = ItemEventBinding.
                            inflate(LayoutInflater.from(mapFragment.getActivity()));
                    binding.setEvent(event);

                    Picasso.with(binding.getRoot().getContext()).
                            load(String.format(DlApi.PHOTO_THUMB_URL, event.getPhotoEventUri())).
                            error(R.drawable.ic_editor_insert_emoticon).
                            into(binding.ivAvatar);

                    binding.getRoot().post(new Runnable() {
                        @Override
                        public void run() {
                            marker.showInfoWindow();
                        }
                    });
                    return binding.getRoot();
                }
            }
        });

        getIssues();
        getEvents();
    }

    @Override
    protected void issuesResult(List<Issue> issues) {
        for (Issue issue : issues) {
            Marker marker = map.addMarker(new MarkerOptions().
                position(new LatLng(issue.getLat(), issue.getLon())).
                title(issue.getTitle()).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_issue_marker)));
            markerIssueMap.put(marker.getId(), issue);
        }
    }

    @Override
    protected void eventsResult(List<Event> events) {
        for (Event event : events) {
            Marker marker = map.addMarker(new MarkerOptions().
                position(new LatLng(event.getLat(), event.getLon())).
                title(event.getTitle()).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_event_marker)));
            markerEventMap.put(marker.getId(), event);
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (centerOnUser) {
            LatLng currentLatLng = new LatLng(location.getLatitude(),
                    location.getLongitude());

            float zoom = map.getCameraPosition().zoom;
            if (zoom < 12) {
                zoom = 14;
            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom));
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        centerOnUser = false;
    }
}
