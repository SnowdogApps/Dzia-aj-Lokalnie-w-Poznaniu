package pl.snowdog.dzialajlokalnie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import pl.snowdog.dzialajlokalnie.databinding.ItemCategoryBinding;
import pl.snowdog.dzialajlokalnie.model.Category;

/**
 * Created by bartek on 01.07.15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;

    public CategoryAdapter(List<Category> issues) {
        this.categories = issues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemCategoryBinding binding = ItemCategoryBinding.
                inflate(LayoutInflater.from(viewGroup.getContext()));

        //TODO how to color the checkbox box?
//        binding.cbName.setHighlightColor(viewGroup.getContext().getResources().getColor(R.color.colorAccent));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Category category = categories.get(i);
        viewHolder.binding.setCategory(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.binding.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ViewHolder.this.binding.getCategory().setSelected(isChecked);
                }
            });
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setSelectedCategories(List<Category> selectedCategories) {
        for (Category c : categories) {
            c.setSelected(false);
        }

        for (Category c1 : categories) {
            for (Category c2 : selectedCategories) {
                if (c1.getCategoryID() == c2.getCategoryID()) {
                    c1.setSelected(true);
                    break;
                }
            }
        }
    }

    public List<Category> getSelectedCategories() {
        List<Category> selectedCategories = new ArrayList<>();
        for (Category c : categories) {
            if (c.isSelected()) {
                selectedCategories.add(c);
            }
        }
        return selectedCategories;
    }
}
