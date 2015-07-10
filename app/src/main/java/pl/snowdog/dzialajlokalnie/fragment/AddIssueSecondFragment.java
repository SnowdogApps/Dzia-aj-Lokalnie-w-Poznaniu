package pl.snowdog.dzialajlokalnie.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_second)
public class AddIssueSecondFragment extends AddIssueBaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    @ViewById
    MapView mapView;
    GoogleMap map;
    Marker mMarker;

    private Bundle mSavedInstanceState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Store savedInstaceState for latter mapView initialization
        mSavedInstanceState = savedInstanceState;

        return null;
    }

    @Override
    boolean validateInput() {
        if(mMarker == null) {
            Snackbar.make(btnNext, "Kliknij na mapie i ustaw miejsce zgłoszenia", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }


    @AfterViews
    void afterViewsCreated() {
        mapView.onCreate(mSavedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //TODO show users current position
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //We allow only one marker
                if(mMarker == null) {
                    mMarker = map.addMarker(new MarkerOptions().position(point).draggable(true));
                }
            }
        });

    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

}
