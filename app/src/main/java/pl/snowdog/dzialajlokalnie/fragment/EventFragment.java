package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;
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
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.RefreshEvent;
import pl.snowdog.dzialajlokalnie.events.SetTitleAndPhotoEvent;
import pl.snowdog.dzialajlokalnie.events.VoteEvent;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Vote;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_event)
public class EventFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = "IssueFragment";
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
        getIssue(objId);
    }

    @Click(R.id.ibRateUp)
    protected void rateUp() {
        rate(IssueVoteEvent.Vote.UP);
    }

    @Click(R.id.ibRateDown)
    protected void rateDown() {
        rate(IssueVoteEvent.Vote.DOWN);
    }

    private void rate(IssueVoteEvent.Vote vote) {
        if (binding.getIssue() != null) {
            EventBus.getDefault().post(new IssueVoteEvent(
                    binding.getIssue().getIssueID(), vote));
        }
    }

    @Override
    protected void issueResult(Issue issue) {
        Log.d(TAG, "issueResult " + issue);
        binding.setIssue(issue);

        EventBus.getDefault().post(new SetTitleAndPhotoEvent(issue.getTitle(),
                String.format(DlApi.PHOTO_NORMAL_URL, issue.getPhotoIssueUri())));

        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        options.camera(new CameraPosition(new LatLng(issue.getLat(), issue.getLon()), 15, 0, 0));
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
        getIssue(objId);
    }

    public void onEvent(IssueVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(DlApi.ParentType.issues, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    @Override
    protected void voteResult(Vote vote) {
        Issue issue = binding.getIssue();
        if (issue.getIssueID() == vote.getParentID()) {
            issue.setIssueRating(issue.getIssueRating() + vote.getValue());
            issue.setUserVotedValue(vote.getValue());
            //TODO - this is dirty implementation. Observables shoud be used but it requires extending BaseObservable - conflict with Model
            binding.setIssue(issue);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);

        District district = new Select().from(District.class).
                where("districtID == ?", binding.getIssue().getDistrictID()).executeSingle();

        Marker marker = map.addMarker(new MarkerOptions().
                position(new LatLng(binding.getIssue().getLat(), binding.getIssue().getLon())).
                title(binding.getIssue().getAddress()).
                snippet(district != null ? district.getName() : null).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_issue_marker)));
        marker.showInfoWindow();
    }
}
