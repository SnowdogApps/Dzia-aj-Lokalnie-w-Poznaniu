package pl.snowdog.dzialajlokalnie.fragment;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.MapInfoWindowAdapter;
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
    private MapInfoWindowAdapter adapter;

    @AfterViews
    public void afterViews() {
        centerOnUser = false;
        adapter = new MapInfoWindowAdapter(getActivity());

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

        map.setInfoWindowAdapter(adapter);

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
            adapter.putIssue(marker, issue);
        }
    }

    @Override
    protected void eventsResult(List<Event> events) {
        for (Event event : events) {
            Marker marker = map.addMarker(new MarkerOptions().
                position(new LatLng(event.getLat(), event.getLon())).
                title(event.getTitle()).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_event_marker)));
            adapter.putEvent(marker, event);
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
