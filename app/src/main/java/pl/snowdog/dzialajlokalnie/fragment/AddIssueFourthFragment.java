package pl.snowdog.dzialajlokalnie.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.model.Categories;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_fourth)
public class AddIssueFourthFragment extends AddIssueBaseFragment {

    @ViewById(R.id.lvCategories)
    ListView lvCategories;

    List<Categories> lCategories = new ArrayList<>();
    ArrayList<String> lCategoriesLabels = new ArrayList<>();;

    @Override
    boolean validateInput() {
        return true;
    }

    @AfterViews
    void afterViewsCreated() {
        btnNext.setText(R.string.add);
        lCategories.add(new Categories(0, "Zgloszenia", 0, new Date(), new Date()));
        lCategories.add(new Categories(1, "Pomoc sasiedzka", 0, new Date(), new Date()));
        lCategories.add(new Categories(2, "Wspolne dzialanie", 0, new Date(), new Date()));
        lCategories.add(new Categories(3, "Wandalizm", 0, new Date(), new Date()));
        lCategories.add(new Categories(4, "Zmiany", 0, new Date(), new Date()));

        for(Categories c : lCategories) {
            lCategoriesLabels.add(c.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, lCategoriesLabels);
        lvCategories.setAdapter(adapter);

    }
}
