package pl.snowdog.dzialajlokalnie.fragment;

import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.snowdog.dzialajlokalnie.AddBaseActivity;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.model.Category;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_fourth)
public class AddIssueFourthFragment extends AddIssueBaseFragment {

    @ViewById(R.id.lvCategories)
    ListView lvCategories;

    List<Category> lCategories = new ArrayList<>();
    ArrayList<String> lCategoriesLabels = new ArrayList<>();;
    ArrayAdapter<String> adapter;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            ((AddBaseActivity)getActivity()).onObjectCreated();
        }
    }

    @Override
    boolean validateInput() {
        if(lvCategories.getCheckedItemCount() > 0) {
            return true;
        } else {
            Snackbar.make(getView(), getString(R.string.warning_choose_category), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
    }

    @AfterViews
    void afterViewsCreated() {
        btnNext.setText(R.string.add);
        lCategories = new Select().from(Category.class).orderBy("name").execute();
        for(Category c : lCategories) {
            lCategoriesLabels.add(c.getName());
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, lCategoriesLabels);
        lvCategories.setAdapter(adapter);

    }
}
