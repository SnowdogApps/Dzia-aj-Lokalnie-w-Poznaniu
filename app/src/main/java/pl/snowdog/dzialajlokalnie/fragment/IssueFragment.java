package pl.snowdog.dzialajlokalnie.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.FragmentIssueBinding;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_issue)
public class IssueFragment extends BaseFragment {

    FragmentIssueBinding binding;

    @FragmentArg
    int objId;

    @AfterViews
    void afterViews() {
        binding = FragmentIssueBinding.inflate(getLayoutInflater(null));

        getIssue(objId);
    }

    @Override
    protected void issueResult(Issue issue) {
        binding.setIssue(issue);
    }
}
