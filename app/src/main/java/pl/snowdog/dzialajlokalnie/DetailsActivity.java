package pl.snowdog.dzialajlokalnie;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.AddCommentWidgetBinding;
import pl.snowdog.dzialajlokalnie.events.SetTitleEvent;
import pl.snowdog.dzialajlokalnie.fragment.CommentsFragment;
import pl.snowdog.dzialajlokalnie.fragment.CommentsFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssueFragment;
import pl.snowdog.dzialajlokalnie.fragment.IssueFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;
import pl.snowdog.dzialajlokalnie.model.Issue;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

    private static final String TAG = "DetailsActivity";

    @ViewById(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.add_comment_widget)
    View addCommentWidget;

    AddCommentWidgetBinding binding;

    @Extra
    DlApi.ParentType objType;

    @Extra
    int objId;

    @Override
    protected void afterView() {
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "afterView " + objType + " " + objId);

        IssueFragment issueFragment = IssueFragment_.builder().arg("objId", objId).build();

        CommentsFragment commentsFragment = CommentsFragment_.builder().arg("objId", objId).
                arg("objType", objType).build();

        getSupportFragmentManager().beginTransaction().replace(R.id.topContent, issueFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomContent, commentsFragment).commit();

        binding = AddCommentWidgetBinding.bind(addCommentWidget);
        binding.itemComment.getRoot().setVisibility(View.GONE);
    }

    public void onEvent(SetTitleEvent event) {
        //TODO only collapsingToolbarLayout works but title is at the bottom (should be sticked to the top)
//        getSupportActionBar().setTitle(event.getTitle());
//        toolbar.setTitle(event.getTitle());
//        collapsingToolbarLayout.setTitle(event.getTitle());
    }
}
