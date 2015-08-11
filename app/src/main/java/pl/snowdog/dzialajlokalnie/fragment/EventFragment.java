package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.AddEventActivity_;
import pl.snowdog.dzialajlokalnie.AddIssueActivity_;
import pl.snowdog.dzialajlokalnie.DlApplication;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.FragmentEventBinding;
import pl.snowdog.dzialajlokalnie.events.RefreshEvent;
import pl.snowdog.dzialajlokalnie.events.SetTitleAndPhotoEvent;
import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.ParticipateEvent;
import pl.snowdog.dzialajlokalnie.util.CircleTransform;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_event)
@OptionsMenu(R.menu.menu_details)
public class EventFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = "EventFragment";
    FragmentEventBinding binding;

    @FragmentArg
    int objId;

    @ViewById(R.id.eventDetails)
    View rootView;

    private SupportMapFragment mapFragment;
    private GoogleMap map;

    @OptionsMenuItem(R.id.action_edit)
    MenuItem menuEdit;

    @AfterViews
    void afterViews() {
        binding = DataBindingUtil.bind(rootView);
        getEvent(objId);


    }

    @Click(R.id.ibAttend)
    protected void attend() {
        if (binding.getEvent() != null) {
            final Calendar now = Calendar.getInstance();
            if(now.getTime().after(binding.getEvent().getEndDate())) {
                Snackbar.make(getView(), getString(R.string.warning_event_ended), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            ParticipateEvent.ParcitipateType participateType;
            if (binding.getEvent().getUserInEvent() != 1) {
                participateType = ParticipateEvent.ParcitipateType.attending;
            } else {
                participateType = ParticipateEvent.ParcitipateType.declined;
            }

            participate(new ParticipateEvent(binding.getEvent().getEventID(), participateType));
        }
    }

    @Override
    protected void eventResult(Event event) {
        Log.d(TAG, "eventResult " + event);
        binding.setEvent(event);

        EventBus.getDefault().post(new SetTitleAndPhotoEvent(event.getTitle(),
                String.format(DlApi.PHOTO_NORMAL_URL, event.getPhotoEventUri())));

        Picasso.with(binding.getRoot().getContext()).
                load(String.format(DlApi.AVATAR_THUMB_URL, event.getAuthorAvatar())).
                error(R.drawable.ic_editor_insert_emoticon).
                transform(new CircleTransform()).
                into(binding.attendCard.ivAuthorAvatar);

        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        options.camera(new CameraPosition(new LatLng(event.getLat(), event.getLon()), 15, 0, 0));
        mapFragment = SupportMapFragment.newInstance(options);
        getChildFragmentManager().beginTransaction().add(R.id.mapCard, mapFragment).commit();
        mapFragment.getMapAsync(this);
        menuEdit.setVisible(isLoggedInUserAuthor());
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
        Event event = binding.getEvent();
        if (event.getEventID() == participateEvent.getEventID()) {
            if (event.getUserInEvent() != 1 && participateEvent.getParticipateType() == 1) {
                event.setAttendingCount(event.getAttendingCount()+1);
            } else if (event.getUserInEvent() == 1 && participateEvent.getParticipateType() != 1) {
                event.setAttendingCount(event.getAttendingCount()-1);
            }
            event.setUserInEvent(participateEvent.getParticipateType());
            binding.setEvent(event);
        }
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

    @OptionsItem(R.id.action_edit)
    void edit() {
        AddEventActivity_.intent(this).mEditedEvent(binding.getEvent()).start();
    }


    private boolean isLoggedInUserAuthor() {
        if(DlApplication.currentSession != null) {
            return DlApplication.currentSession.getUserID() == binding.getEvent().getUserID();
        } else {
            return false;
        }
    }
}
