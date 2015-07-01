package pl.snowdog.dzialajlokalnie.fragment;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.adapter.IssuesAdapter;
import pl.snowdog.dzialajlokalnie.model.Issue;

/**
 * Created by bartek on 01.07.15.
 */

@EFragment(R.layout.fragment_list)
public class IssuesFragment extends ListFragment {

    IssuesAdapter adapter;

    @Override
    protected void afterView() {
        List<Issue> issues = new ArrayList<>();

        issues.add(new Issue(0, 0, 0, "Zepsuta ławka", "Bardzo zepsuta", 0, "Naramowicka 143", null, null, new Date(), 0, 0, null));

        adapter = new IssuesAdapter(issues);
        recyclerView.setAdapter(adapter);
    }
}
