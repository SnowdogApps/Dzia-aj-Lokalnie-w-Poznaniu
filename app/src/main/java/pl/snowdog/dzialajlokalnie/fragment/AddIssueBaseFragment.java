package pl.snowdog.dzialajlokalnie.fragment;

import android.support.v4.app.Fragment;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import pl.snowdog.dzialajlokalnie.AddBaseActivity;
import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment
public abstract class AddIssueBaseFragment extends Fragment {

    @ViewById(R.id.btnNext)
    Button btnNext;


    @ViewById(R.id.btnPrev)
    Button btnPrev;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            ((AddBaseActivity)getActivity()).goToNextPage();
        }
    }

    @Click(R.id.btnPrev)
    void onPrevButtonClicked() {
        ((AddBaseActivity)getActivity()).goToPreviousPage();
    }

    abstract boolean validateInput();

    @AfterViews
    void afterViews() {

    }
}
