package pl.snowdog.dzialajlokalnie.adapter;

import android.content.Context;
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

    private List<District> districts;

    public static DistrictAdapter build(Context context, List<District> districts) {
        return new DistrictAdapter(context, 0, districts);
    }

    public DistrictAdapter(Context context, int resource, List<District> districts) {
        super(context, resource, districts);

        this.districts = districts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDistrictBinding binding = null;
        if (convertView == null) {
            binding = ItemDistrictBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemDistrictBinding) convertView.getTag();
        }
        binding.setDistrict(getItem(position));
        binding.setEven(position%2==0);

        return binding.getRoot();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
