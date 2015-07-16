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
import pl.snowdog.dzialajlokalnie.databinding.ItemIssueBinding;
import pl.snowdog.dzialajlokalnie.events.IssueClickedEvent;
import pl.snowdog.dzialajlokalnie.events.IssueVoteEvent;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 01.07.15.
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {

    private List<Issue> issues;

    public IssuesAdapter(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemIssueBinding binding = ItemIssueBinding.
                inflate(LayoutInflater.from(viewGroup.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Issue issue = issues.get(i);
        viewHolder.binding.setIssue(issue);

        Picasso.with(viewHolder.binding.getRoot().getContext()).
                load(String.format(DlApi.PHOTO_THUMB_URL, issue.getPhotoIssueUri())).
                error(R.drawable.ic_editor_insert_emoticon).
                into(viewHolder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemIssueBinding binding;

        public ViewHolder(ItemIssueBinding binding) {
            super(binding.getRoot());

            binding.ratingWidget.ibRateUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new IssueVoteEvent(
                            ViewHolder.this.binding.getIssue().getIssueID(),
                            IssueVoteEvent.Vote.UP));
                }
            });

            binding.ratingWidget.ibRateDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new IssueVoteEvent(
                            ViewHolder.this.binding.getIssue().getIssueID(),
                            IssueVoteEvent.Vote.DOWN));
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new IssueClickedEvent(
                            ViewHolder.this.binding.getIssue().getIssueID()));
                }
            });

            this.binding = binding;
        }
    }

    public List<Issue> getIssues() {
        return issues;
    }
}
