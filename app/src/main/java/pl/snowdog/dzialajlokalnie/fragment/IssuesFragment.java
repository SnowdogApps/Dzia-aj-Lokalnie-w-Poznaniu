package pl.snowdog.dzialajlokalnie.fragment;

import android.util.Log;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.IssuesAdapter;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.VoteEvent;
import pl.snowdog.dzialajlokalnie.model.Issue;
import pl.snowdog.dzialajlokalnie.model.Vote;

/**
 * Created by bartek on 01.07.15.
 */

@EFragment(R.layout.fragment_list)
public class IssuesFragment extends ListFragment {

    private static final String TAG = "IssuesFragment";
    IssuesAdapter adapter;

    @Override
    protected void afterView() {
        getIssues();
    }

    @Override
    protected void refreshItems() {
        super.refreshItems();
        getIssues();
    }

    @Override
    protected void issuesResult(List<Issue> issues) {
        adapter = new IssuesAdapter(issues);
        recyclerView.setAdapter(adapter);

        onItemsLoadComplete();
    }

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(IssueVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(DlApi.ParentType.issues, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    @Override
    protected void voteResult(Vote vote) {
        for (int i = 0; i < adapter.getIssues().size(); i++) {
            Issue issue = adapter.getIssues().get(i);
            if (issue.getIssueID() == vote.getParentID()) {
                issue.setIssueRating(issue.getIssueRating()+vote.getValue());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
