package pl.snowdog.dzialajlokalnie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pl.snowdog.dzialajlokalnie.databinding.ItemDistrictBinding;
import pl.snowdog.dzialajlokalnie.model.District;

/**
 * Created by bartek on 10.07.15.
 */
public class DistrictAdapter extends ArrayAdapter<District> {

    private static final String TAG = "DistrictAdapter";
    private int selection;

    public static DistrictAdapter build(Context context, List<District> districts) {
        return new DistrictAdapter(context, 0, districts);
    }

    public DistrictAdapter(Context context, int resource, List<District> districts) {
        super(context, resource, districts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemDistrictBinding(position, convertView, parent, false);
    }

    @NonNull
    private View getItemDistrictBinding(int position, View convertView, ViewGroup parent, boolean isDropDown) {
        ItemDistrictBinding binding;
        if (convertView == null) {
            binding = ItemDistrictBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemDistrictBinding) convertView.getTag();
        }

        binding.setDistrict(getItem(position));
        binding.setLongText(getItem(position).getName().length() > 20);

        if (isDropDown) {
            binding.setEven(position % 2 == 0);
            binding.setSelectedItem(position == selection);
        } else {
            binding.setEven(true);
            binding.setSelectedItem(false);
        }

        return binding.getRoot();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getItemDistrictBinding(position, convertView, parent, true);
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelectionId(int selectionId) {
        for (int i = 0; i < getCount(); i++) {
            if (selectionId == getItem(i).getDistrictID()) {
                selection = i;
                break;
            }
        }
    }

    public int getSelectionId() {
        return getItem(selection).getDistrictID();
    }


}
