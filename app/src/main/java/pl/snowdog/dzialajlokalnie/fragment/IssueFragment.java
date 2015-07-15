package pl.snowdog.dzialajlokalnie.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.databinding.FragmentIssueBinding;

/**
 * Created by bartek on 15.07.15.
 */

@EFragment(R.layout.fragment_issue)
public class IssueFragment extends BaseFragment {

    FragmentIssueBinding binding;

    @AfterViews
    void afterViews() {
        binding = FragmentIssueBinding.inflate(getLayoutInflater(null));

    }
}
