package pl.snowdog.dzialajlokalnie.fragment;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.FragmentIssueBinding;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
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

        binding.ratingWidget.ibRateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getIssue() != null) {
                    EventBus.getDefault().post(new IssueVoteEvent(
                            binding.getIssue().getIssueID(),
                            IssueVoteEvent.Vote.UP));
                }
            }
        });

        binding.ratingWidget.ibRateDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getIssue() != null) {
                    EventBus.getDefault().post(new IssueVoteEvent(
                            binding.getIssue().getIssueID(),
                            IssueVoteEvent.Vote.DOWN));
                }
            }
        });

        getIssue(objId);
    }

    @Override
    protected void issueResult(Issue issue) {
        
        Log.d(TAG, "issueResult " + issue);
        binding.setIssue(issue);

        Picasso.with(binding.getRoot().getContext()).
                load(String.format(DlApi.PHOTO_THUMB_URL, issue.getPhotoIssueUri())).
                error(R.drawable.ic_editor_insert_emoticon).
                into(binding.ivAvatar);
    }

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(IssueVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(Vote.ParentType.issues, event.getIssueId(), event.getVote() == IssueVoteEvent.Vote.UP ? 1 : -1);
    }

    @Override
    protected void voteResult(Vote vote) {
        Issue issue = binding.getIssue();
        if (issue.getIssueID() == vote.getParentID()) {
            issue.setIssueRating(issue.getIssueRating()+vote.getValue());
        }
    }
}
