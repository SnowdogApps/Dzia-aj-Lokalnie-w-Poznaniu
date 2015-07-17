package pl.snowdog.dzialajlokalnie;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.databinding.AddCommentWidgetBinding;
import pl.snowdog.dzialajlokalnie.events.CommentClickedEvent;
import pl.snowdog.dzialajlokalnie.events.NewCommentEvent;
import pl.snowdog.dzialajlokalnie.events.SetTitleEvent;
import pl.snowdog.dzialajlokalnie.fragment.CommentsFragment;
import pl.snowdog.dzialajlokalnie.fragment.CommentsFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssueFragment;
import pl.snowdog.dzialajlokalnie.fragment.IssueFragment_;
import pl.snowdog.dzialajlokalnie.model.Comment;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

    private static final String TAG = "DetailsActivity";

    @ViewById(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.focus_background)
    View focusBackground;

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
        binding.itemComment.ratingWidget.ibRateUp.setVisibility(View.INVISIBLE);
        binding.itemComment.ratingWidget.ibRateDown.setVisibility(View.INVISIBLE);

        binding.etComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    focusBackground.setVisibility(View.VISIBLE);
                } else {
                    focusBackground.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.etComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "onEditorAction " + actionId);
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    send();
                    unfocus();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "back button pressed");

        //TODO onBackPressed does not catch closing the soft keyboard. Possible solution: http://tech.leolink.net/2014/02/a-hack-to-catch-soft-keyboard-showhide.html
        if (binding.etComment.hasFocus()) {
            unfocus();
        } else {
            super.onBackPressed();
        }
    }
    @Click(R.id.focus_background)
    protected void unfocus() {
        binding.etComment.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.etComment.getWindowToken(), 0);

        binding.setComment(null);
        binding.itemComment.getRoot().setVisibility(View.GONE);
        binding.etComment.setHint(R.string.comment_hint);
    }

    @Click(R.id.bt_send)
    protected void send() {
        if (binding.getComment() != null) {
            comment(DlApi.ParentType.comments, binding.getComment().getCommentID(), 0,
                    binding.etComment.getText().toString());
        } else {
            comment(objType, objId, 0, binding.etComment.getText().toString());
        }
        unfocus();
    }

    public void onEvent(SetTitleEvent event) {
        //TODO only collapsingToolbarLayout works but title is at the bottom (should be sticked to the top)
        collapsingToolbarLayout.setTitle(event.getTitle());
    }

    public void onEvent(CommentClickedEvent event) {
        binding.setComment(event.getComment());
        binding.itemComment.getRoot().setVisibility(View.VISIBLE);
        binding.etComment.requestFocus();
        binding.etComment.setHint(R.string.response_hint);

        Picasso.with(binding.getRoot().getContext()).
                load(String.format(DlApi.PHOTO_THUMB_URL, event.getComment().getAuthorAvatar())).
                error(R.drawable.ic_editor_insert_emoticon).
                into(binding.itemComment.ivAvatar);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    protected void commentResult(Comment comment) {
        binding.etComment.setText("");
        EventBus.getDefault().post(new NewCommentEvent(comment));
    }
}
