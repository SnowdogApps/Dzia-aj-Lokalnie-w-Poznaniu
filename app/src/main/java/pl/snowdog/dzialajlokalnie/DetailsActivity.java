package pl.snowdog.dzialajlokalnie;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.fragment.IssueFragment;
import pl.snowdog.dzialajlokalnie.fragment.IssueFragment_;
import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends BaseActivity {

    private static final String TAG = "DetailsActivity";
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Extra
    int objType;

    @Extra
    int objId;

    @Override
    protected void afterView() {
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "afterView " + objType + " " + objId);

        IssueFragment issueFragment = IssueFragment_.builder().arg("objId", objId).build();

        getSupportFragmentManager().beginTransaction().replace(R.id.topContent, issueFragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.bottomContent, new IssuesFragment_()).commit();
    }
}
