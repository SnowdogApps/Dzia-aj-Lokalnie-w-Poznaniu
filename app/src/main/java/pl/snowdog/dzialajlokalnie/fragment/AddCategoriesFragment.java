package pl.snowdog.dzialajlokalnie.fragment;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.model.Category;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_categories)
public class AddCategoriesFragment extends AddBaseFragment {

    @ViewById(R.id.lvCategories)
    ListView lvCategories;

    List<Category> lCategories = new ArrayList<>();
    ArrayList<String> lCategoriesLabels = new ArrayList<>();;
    ArrayAdapter<String> adapter;

    @FragmentArg
    CreateNewObjectEvent mEditedObject;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .categoryIDs(getSelectedCategoriesList())
                    .type(CreateNewObjectEvent.Type.category);

            EventBus.getDefault().post(builder.build());
            //((AddBaseActivity)getActivity()).goToNextPage();
        }
    }

    private List<Integer> getSelectedCategoriesList() {
        SparseBooleanArray checked = lvCategories.getCheckedItemPositions();
        List<Integer> lSelectedCategoriesIds = new ArrayList<>(lvCategories.getCheckedItemCount());
        for (int i = 0; i < lvCategories.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                lSelectedCategoriesIds.add(lCategories.get(i).getCategoryID());
            }
        }
        return lSelectedCategoriesIds;
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

        //EDIT MODE
        if(mEditedObject != null) {
            for(Integer i : mEditedObject.getCategoryIDs()) {

                int counter = 0;
                for(Category c : lCategories) {
                    if(c.getCategoryID() == i) {
                        lvCategories.setItemChecked(counter, true);
                        Log.d("issue", "create categoryId category: " + c.getCategoryID());
                    } else {
                       // lvCategories.setItemChecked(counter, false);
                    }
                    counter++;
                }
            }
            //TODO set selected categories in spinner
        }
    }
}
