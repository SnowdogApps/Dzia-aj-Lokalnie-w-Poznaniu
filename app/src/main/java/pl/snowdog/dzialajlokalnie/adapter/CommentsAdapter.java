package pl.snowdog.dzialajlokalnie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.ItemCommentBinding;
import pl.snowdog.dzialajlokalnie.events.CommentClickedEvent;
import pl.snowdog.dzialajlokalnie.events.CommentVoteEvent;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.model.Comment;

/**
 * Created by bartek on 01.07.15.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemCommentBinding binding = ItemCommentBinding.
                inflate(LayoutInflater.from(viewGroup.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Comment comment = comments.get(i);
        viewHolder.binding.setComment(comment);

        Picasso.with(viewHolder.binding.getRoot().getContext()).
                load(String.format(DlApi.PHOTO_THUMB_URL, comment.getAuthorAvatar())).
                error(R.drawable.ic_editor_insert_emoticon).
                into(viewHolder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentBinding binding;

        public ViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());

            binding.ratingWidget.ibRateUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CommentVoteEvent(
                            ViewHolder.this.binding.getComment().getCommentID(),
                            IssueVoteEvent.Vote.UP));
                }
            });

            binding.ratingWidget.ibRateDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CommentVoteEvent(
                            ViewHolder.this.binding.getComment().getCommentID(),
                            IssueVoteEvent.Vote.DOWN));
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new CommentClickedEvent(
                            ViewHolder.this.binding.getComment()));
                }
            });

            this.binding = binding;
        }
    }

    public List<Comment> getComments() {
        return comments;
    }
}
