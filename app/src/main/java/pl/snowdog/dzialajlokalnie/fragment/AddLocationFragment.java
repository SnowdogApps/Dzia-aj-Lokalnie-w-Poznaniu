package pl.snowdog.dzialajlokalnie.fragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.PolygonOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.AddUserActivity;
import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.DistrictAdapter;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Point;
import pl.snowdog.dzialajlokalnie.model.Polygon;
import pl.snowdog.dzialajlokalnie.model.ReverseGeocoding;
import pl.snowdog.dzialajlokalnie.util.PolygonUtil;
import pl.snowdog.dzialajlokalnie.util.PrefsUtil_;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_location)
public class AddLocationFragment extends AddBaseFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {
    private static final String TAG = "AddIssueLocationFr";


    @ViewById
    MapView mapView;
    GoogleMap map;
    Marker mMarker;

    @ViewById(R.id.tvHint)
    TextView tvHint;

    @ViewById(R.id.spDistrict)
    Spinner spinner;

    private DistrictAdapter adapter;

    private Bundle mSavedInstanceState;

    String address;

    @FragmentArg
    CreateNewObjectEvent mEditedObject;

    @FragmentArg int mMode;

    List<District> districts;

    @Pref
    PrefsUtil_ pref;

    /**
     * When we do manual marker operations (drag, create) we mark it so the adapter.setSelected()
     * won't override it with it's own marker that is created when we pick District from the spinner
     */
    private boolean isMarkerChange = false;

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

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .lat(mMarker.getPosition().latitude)
                    .lon(mMarker.getPosition().longitude)
                    .address(address)
                    .districtID(adapter.getSelectedItem().getDistrictID())
                    .type(CreateNewObjectEvent.Type.location);

            EventBus.getDefault().post(builder.build());
            //((AddBaseActivity)getActivity()).goToNextPage();
        }
    }


    @AfterViews
    void afterViewsCreated() {
        mapView.onCreate(mSavedInstanceState);
        mapView.getMapAsync(this);

        districts = new Select().from(District.class).orderBy("name").execute();
        for(District d : districts) {
            d.createPolygonFromCoordinates();
        }
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

            if(position != 0 && !isMarkerChange) {
                Toast.makeText(getActivity(), "district latlng: "+adapter.getSelectedItem().getLat(), Toast.LENGTH_SHORT).show();
                map.clear();
                mMarker = map.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(adapter.getSelectedItem().getLat(), adapter.getSelectedItem().getLon()))
                                .draggable(true));
                getAddressForLocation(adapter.getSelectedItem().getLat(), adapter.getSelectedItem().getLon());

                PolygonUtil.createDistrictShapeOnMap(map, adapter.getSelectedItem(), getActivity());

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(adapter.getSelectedItem().getLat(), adapter.getSelectedItem().getLon()), 12));
            }
            isMarkerChange = false;
        }
    }

    private void getAddressForLocation(double lat, double lon) {
        Map<String, String> options = new HashMap<String, String>();
        options.put("x", Double.toString(lon));
        options.put("y", Double.toString(lat));
        options.put("n", "1");
        DlApplication.poznanApi.getAddressForLocation(options, new Callback<ReverseGeocoding>() {
            @Override
            public void success(ReverseGeocoding reverseGeocodings, Response response) {
                if (reverseGeocodings.getFeatures() != null && reverseGeocodings.getFeatures().size() > 0) {
                    if (reverseGeocodings.getFeatures().get(0).getProperty() != null) {
                        ReverseGeocoding.Property property = reverseGeocodings.getFeatures().get(0).getProperty();
                        Log.d(TAG, "cityApi get success: " + response + " getAddressForLocation: " + reverseGeocodings.getFeatures().get(0).getProperty().toString());
                        updateMarkerWithAddress(property);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "cityApi get error: " + error.toString());
            }
        });
    }

    @UiThread
    void updateMarkerWithAddress(ReverseGeocoding.Property property) {
        String streetName = property.getImie() != null ? property.getImie() : property.getNazwisko();
        streetName = String.valueOf(streetName.charAt(0)).toUpperCase() + streetName.substring(1, streetName.length());
        address = property.getTyp()+streetName +" "+ property.getNr();
        mMarker.setTitle(address);
        mMarker.showInfoWindow();
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
                Log.d(TAG, "mpdbg markerLat: " + point.latitude + " marker.lon: " + point.longitude);


                if(findDistrictAndSetSpinner(point)) {
                    getAddressForLocation(point.latitude, point.longitude);
                } else {
                    map.clear();
                    mMarker = null;
                    spinner.setSelection(0);
                    Snackbar.make(getView(), getString(R.string.outside_city_warning), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                map.clear();
                mMarker = map.addMarker(new MarkerOptions().position(marker.getPosition()).draggable(true));
                if(findDistrictAndSetSpinner(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))) {
                    getAddressForLocation(marker.getPosition().latitude, marker.getPosition().longitude);
                } else {
                    map.clear();
                    mMarker = null;
                    spinner.setSelection(0);
                    Snackbar.make(getView(), getString(R.string.outside_city_warning), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //EDIT Mode
        if(mEditedObject != null) {
            mMarker = map.addMarker(new MarkerOptions().position(new LatLng(mEditedObject.getLat(), mEditedObject.getLon())).draggable(true));
            getAddressForLocation(mEditedObject.getLat(), mEditedObject.getLon());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mEditedObject.getLat(), mEditedObject.getLon()), 11));
            findDistrictAndSetSpinner(new LatLng(mEditedObject.getLat(), mEditedObject.getLon()));
            Log.d(TAG, "edtdbg location: lat: "+mEditedObject.getLat()+ " lon: "+mEditedObject.getLon());
        }


        if(mMode == AddUserActivity.MODE_SIGN_UP) {
            tvHint.setText(R.string.info_select_live_distrcit);
            if(pref.lastLat().exists() && pref.lastLon().exists()) {
                mMarker = map.addMarker(new MarkerOptions().position(new LatLng(pref.lastLat().get(), pref.lastLon().get())).draggable(true));
                if (findDistrictAndSetSpinner(new LatLng(pref.lastLat().get(), pref.lastLon().get()))) {
                    getAddressForLocation(pref.lastLat().get(), pref.lastLon().get());
                }
            }
        }
    }

    /**
     *
     * @param point to check if it belongs to any district in the city
     * @return true if point is within given district
     */
    private boolean findDistrictAndSetSpinner(LatLng point) {
        boolean isWithinDistrict = false;
        int counter = 0;
        for(District d : districts) {
            if(d.getPolygon() != null) {
                if (PolygonUtil.isPointInPolygon(point, d.getPolygon())) {
                    isMarkerChange = true;
                    spinner.setSelection(counter);
                    PolygonUtil.createDistrictShapeOnMap(map, d, getActivity());
                    Log.d(TAG, "gacdbg look for location exists");
                    return true;
                }
            }
            counter++;
        }
        return isWithinDistrict;
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
