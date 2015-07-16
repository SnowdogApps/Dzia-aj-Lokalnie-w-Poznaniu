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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
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
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.DistrictAdapter;
import pl.snowdog.dzialajlokalnie.model.District;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_second)
public class AddIssueSecondFragment extends AddIssueBaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    @ViewById
    MapView mapView;
    GoogleMap map;
    Marker mMarker;


    @ViewById(R.id.spDistrict)
    Spinner spinner;

    private DistrictAdapter adapter;

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
        if(mMarker == null && spinner.getSelectedItemPosition() == 0) {
            Snackbar.make(btnNext, getActivity().getString(R.string.warning_set_place), Snackbar.LENGTH_SHORT)
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

        List<District> districts = new Select().from(District.class).orderBy("name").execute();
        districts.add(0, new District(-1, getString(R.string.all_districts_filter), null, 16.934167, 52.408333, -1));
        adapter = DistrictAdapter.build(getActivity(), districts);
        if (DlApplication.filter.getDistrict() != null) {
            adapter.setSelectionId(DlApplication.filter.getDistrict().getDistrictID());
        }
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getSelection());
    }

    @ItemSelect(R.id.spDistrict)
    void districtSelected(boolean selected, int position) {
        if (selected) {
            adapter.setSelection(position);

            if(position != 0) {
                Toast.makeText(getActivity(), "district latlng: "+adapter.getSelectedItem().getLat(), Toast.LENGTH_SHORT).show();
                map.clear();
                mMarker = map.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(adapter.getSelectedItem().getLat(), adapter.getSelectedItem().getLon()))
                                .draggable(true));
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.408333, 16.934167), 11));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                //We allow only one marker
                map.clear();
                mMarker = map.addMarker(new MarkerOptions().position(point).draggable(true));
                spinner.setSelection(0);
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
