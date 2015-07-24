package pl.snowdog.dzialajlokalnie.databinding;

import android.view.View;
import android.widget.CheckedTextView;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.model.Comment;

/**
 * Created by bartek on 24.07.15.
 */
public class AddCommentBinding extends AddCommentWidgetBinding {
    public AddCommentBinding(View root) {
        super(root);

        View.OnClickListener ctvOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckedTextView) {
                    ((CheckedTextView)v).toggle();

                    if (v == ctvQuote && getComment() != null) {
                        Comment comment = getComment();
                        String quote = "> " +
                                AddCommentBinding.this.getRoot().getContext().
                                        getString(R.string.wrote_on_quote, comment.getAuthorName()) +
                                "> " + comment.getText().replace("\n", "\n> ") + "\n\n";

                        if (((CheckedTextView)v).isChecked()) {
                            etComment.setText("");
                            etComment.append(quote + etComment.getText().toString());
                        } else {
                            etComment.setText("");
                            etComment.append(etComment.getText().toString().replace(quote, ""));
                        }
                    }
                }
            }
        };

        ctvQuote.setOnClickListener(ctvOnClickListener);
        ctvReport.setOnClickListener(ctvOnClickListener);
        ctvSolution.setOnClickListener(ctvOnClickListener);
    }

    public void clear() {
        setComment(null);
        itemComment.getRoot().setVisibility(View.GONE);
        etComment.setHint(R.string.comment_hint);
        ctvQuote.setChecked(false);
        ctvReport.setChecked(false);
        ctvSolution.setChecked(false);
    }
}
