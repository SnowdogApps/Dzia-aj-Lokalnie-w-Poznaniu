package pl.snowdog.dzialajlokalnie;

import android.webkit.WebView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by chomi3 on 2015-07-27.
 */
@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity {
    private static final String TAG = "AboutActivity";

    @ViewById(R.id.web_view)
    WebView webView;
    
    @Override
    protected void afterView() {
        webView.loadUrl("file:///android_asset/about.html");
    }

}
