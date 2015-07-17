package pl.snowdog.dzialajlokalnie.fragment;

import android.util.Log;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.CommentsAdapter;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.CommentClickedEvent;
import pl.snowdog.dzialajlokalnie.events.CommentVoteEvent;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.events.NewCommentEvent;
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

    @FragmentArg
    DlApi.ParentType objType;

    @FragmentArg
    int objId;

    @Override
    protected void afterView() {
        getComments(objType, objId);
        swipeRefreshLayout.setEnabled(false);
        emptyListText = R.string.empty_comment_list;
    }

    @Override
    protected void refreshItems() {
        super.refreshItems();
        getComments(objType, objId);
    }

    @Override
    protected void commentsResult(List<Comment> comments) {
        adapter = new CommentsAdapter(comments);
        recyclerView.setAdapter(adapter);

        onItemsLoadComplete();
    }

    @Override
    protected boolean isImplementingEventBus() {
        return true;
    }

    public void onEvent(CommentClickedEvent event) {
        Log.d(TAG, "onEvent " + event);
    }

    public void onEvent(CommentVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(DlApi.ParentType.comments, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    public void onEvent(NewCommentEvent event) {
        adapter.getComments().add(event.getComment());
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
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
