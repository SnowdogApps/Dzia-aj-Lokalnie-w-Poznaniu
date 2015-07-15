package pl.snowdog.dzialajlokalnie;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.fragment.IssuesFragment_;

@EActivity(R.layout.activity_issue)
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

        getSupportFragmentManager().beginTransaction().replace(R.id.topContent, new SupportMapFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomContent, new IssuesFragment_()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
