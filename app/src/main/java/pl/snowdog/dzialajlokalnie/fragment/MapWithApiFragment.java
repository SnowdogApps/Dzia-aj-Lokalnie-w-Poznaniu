package pl.snowdog.dzialajlokalnie.fragment;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by bartek on 13.07.15.
 */
@EFragment(R.layout.fragment_map)
public class MapWithApiFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    SupportMapFragment mapFragment;

    private GoogleMap map;
    private boolean centerOnUser;

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
