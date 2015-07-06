package pl.snowdog.dzialajlokalnie;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by bartek on 06.07.15.
 */
public class BaseActivity extends AppCompatActivity {

    protected DlApplication getApp() {
        return (DlApplication) getApplication();
    }
}
