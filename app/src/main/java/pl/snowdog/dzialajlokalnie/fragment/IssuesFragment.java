package pl.snowdog.dzialajlokalnie.fragment;

import android.support.design.widget.Snackbar;
import android.util.Log;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.AddIssueActivity_;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.IssuesAdapter;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.ObjectAddedEvent;
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
        AddIssueActivity_.intent(getActivity()).mEditedIssue(adapter.getIssues().get(0)).start();
        //vote(DlApi.ParentType.issues, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    public void onEvent(ObjectAddedEvent event) {
        Log.d(TAG, "onEvent " + event);
        switch (event.getAdded()) {
            case issue:
                Snackbar.make(getView(), getString(R.string.added_issue_success_info), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }

        refreshItems();
    }

    @Override
    protected void voteResult(Vote vote) {
        for (int i = 0; i < adapter.getIssues().size(); i++) {
            Issue issue = adapter.getIssues().get(i);
            if (issue.getIssueID() == vote.getParentID()) {
                issue.setIssueRating(issue.getIssueRating()+vote.getValue());
                issue.setUserVotedValue(vote.getValue());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
