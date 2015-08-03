package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.FragmentEventBinding;
import pl.snowdog.dzialajlokalnie.events.RefreshEvent;
import pl.snowdog.dzialajlokalnie.events.SetTitleAndPhotoEvent;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.ParticipateEvent;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_event)
public class EventFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = "EventFragment";
    FragmentEventBinding binding;

    @FragmentArg
    int objId;

    @ViewById(R.id.eventDetails)
    View rootView;

    private SupportMapFragment mapFragment;
    private GoogleMap map;

    @AfterViews
    void afterViews() {
        binding = DataBindingUtil.bind(rootView);
        getEvent(objId);
    }

    @Click(R.id.ibAttend)
    protected void attend() {
        if (binding.getEvent() != null) {
            participate(new ParticipateEvent(binding.getEvent().getEventID(), ParticipateEvent.ParcitipateType.attending));
        }
    }

    @Override
    protected void eventResult(Event event) {
        Log.d(TAG, "eventResult " + event);
        binding.setEvent(event);

        EventBus.getDefault().post(new SetTitleAndPhotoEvent(event.getTitle(),
                String.format(DlApi.PHOTO_NORMAL_URL, event.getPhotoEventUri())));

        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        options.camera(new CameraPosition(new LatLng(event.getLat(), event.getLon()), 15, 0, 0));
        mapFragment = SupportMapFragment.newInstance(options);
        getChildFragmentManager().beginTransaction().add(R.id.mapCard, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(RefreshEvent event) {
        Log.d(TAG, "onEvent " + event);
        getEvent(objId);
    }

    @Override
    protected void participateResult(ParticipateEvent participateEvent) {
        Log.d(TAG, "participateResult " + participateEvent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        Marker marker = map.addMarker(new MarkerOptions().
                position(new LatLng(binding.getEvent().getLat(), binding.getEvent().getLon())).
                title(binding.getEvent().getAddress()).
                snippet(binding.getEvent().getDistrictName()).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_event_marker)));
        marker.showInfoWindow();
    }
}
