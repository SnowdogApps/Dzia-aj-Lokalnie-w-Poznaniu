package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.FragmentIssueBinding;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.RefreshEvent;
import pl.snowdog.dzialajlokalnie.events.SetTitleAndPhotoEvent;
import pl.snowdog.dzialajlokalnie.events.VoteEvent;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Vote;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_issue)
public class IssueFragment extends BaseFragment {

    private static final String TAG = "IssueFragment";
    FragmentIssueBinding binding;

    @FragmentArg
    int objId;

    @ViewById(R.id.issueDetails)
    View rootView;

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
}
