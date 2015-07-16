package pl.snowdog.dzialajlokalnie.fragment;

import android.util.Log;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.CommentsAdapter;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.VoteEvent;
import pl.snowdog.dzialajlokalnie.model.Comment;
import pl.snowdog.dzialajlokalnie.model.Vote;

/**
 * Created by bartek on 01.07.15.
 */

@EFragment(R.layout.fragment_list)
public class CommentsFragment extends ListFragment {

    private static final String TAG = "CommentsFragment";
    CommentsAdapter adapter;

    @Override
    protected void afterView() {
        // TODO implement
//        getComments();
    }

    @Override
    protected void refreshItems() {
        super.refreshItems();
        // TODO implement
//        getComments();
    }

    // TODO implement
//    @Override
    protected void commentsResult(List<Comment> comments) {
        adapter = new CommentsAdapter(comments);
        recyclerView.setAdapter(adapter);

        onItemsLoadComplete();
    }

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(IssueVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(Vote.ParentType.comments, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    @Override
    protected void voteResult(Vote vote) {
        for (int i = 0; i < adapter.getComments().size(); i++) {
            Comment issue = adapter.getComments().get(i);
            if (issue.getCommentID() == vote.getParentID()) {
                issue.setCommentRating(issue.getCommentRating() + vote.getValue());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
