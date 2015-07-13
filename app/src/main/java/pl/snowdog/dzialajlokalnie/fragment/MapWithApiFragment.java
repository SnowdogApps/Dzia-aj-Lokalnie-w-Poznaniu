package pl.snowdog.dzialajlokalnie.fragment;

import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by bartek on 13.07.15.
 */

@EFragment(R.layout.fragment_map)
public class MapWithApiFragment extends BaseFragment {

    @ViewById(R.id.rl_container)
    RelativeLayout relativeLayout;

    @AfterViews
    public void afterViews() {

    }
}
