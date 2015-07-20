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
import pl.snowdog.dzialajlokalnie.events.NewCommentEvent;
import pl.snowdog.dzialajlokalnie.events.RefreshEvent;
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
        swipeRefreshLayout.setEnabled(false);
        emptyListText = R.string.empty_comment_list;

        getComments(objType, objId);
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

    public void onEvent(RefreshEvent event) {
        Log.d(TAG, "onEvent " + event);
        refreshItems();
    }

    public void onEvent(CommentVoteEvent event) {
        Log.d(TAG, "onEvent " + event);

        vote(DlApi.ParentType.comments, event.getId(), event.getVote() == VoteEvent.Vote.UP ? 1 : -1);
    }

    public void onEvent(NewCommentEvent event) {
        int position = -1;

        if (event.getComment().getParentType() == 3) { //comment to comment -> add under commented
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (adapter.getComments().get(i).getCommentID() == event.getComment().getParentId()) {
                    position = i+1;
                    break;
                }
            }
        } else { //comment to issue or event -> add to the end
            position = adapter.getItemCount();
        }

        if (position == -1) {
            position = 0;
        }

        adapter.getComments().add(position, event.getComment());
        adapter.notifyItemInserted(position);
        recyclerView.scrollToPosition(position);
    }

    @Override
    protected void voteResult(Vote vote) {
        for (int i = 0; i < adapter.getComments().size(); i++) {
            Comment comment = adapter.getComments().get(i);
            if (comment.getCommentID() == vote.getParentID()) {
                comment.setCommentRating(comment.getCommentRating() + vote.getValue());
                comment.setUserVotedValue(vote.getValue());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
