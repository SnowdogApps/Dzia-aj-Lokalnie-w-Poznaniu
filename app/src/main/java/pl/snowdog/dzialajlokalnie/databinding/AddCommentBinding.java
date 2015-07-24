package pl.snowdog.dzialajlokalnie.databinding;

import android.view.View;
import android.widget.CheckedTextView;

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
                        String quote = "> " + comment.getAuthorName() + ": " +
                                comment.getText().replace("\n", "\n> ") + "\n\n";

                        if (((CheckedTextView)v).isChecked()) {
                            etComment.setText(quote + etComment.getText().toString());
                            etComment.append("");
                        } else {
                            etComment.setText(etComment.getText().toString().replace(quote, ""));
                            etComment.append("");
                        }
                    }
                }
            }
        };

        ctvQuote.setOnClickListener(ctvOnClickListener);
        ctvReport.setOnClickListener(ctvOnClickListener);
        ctvSolution.setOnClickListener(ctvOnClickListener);
    }
}
