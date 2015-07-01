package pl.snowdog.dzialajlokalnie.fragment;


import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends com.google.android.gms.maps.SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    private static final String TAG = "MapFragment";
    private GoogleMap map;
    private boolean centerOnUser;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        centerOnUser = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
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
