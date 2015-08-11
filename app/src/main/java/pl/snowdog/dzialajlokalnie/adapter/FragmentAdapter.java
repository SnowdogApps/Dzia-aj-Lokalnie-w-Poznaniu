package pl.snowdog.dzialajlokalnie.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.snowdog.dzialajlokalnie.R;

/**
 * Created by bartek on 23.07.15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        mFragmentTitles.add("");
    }

    public View getTabView(int position, Context ctx) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(ctx).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(mFragmentTitles.get(position));

        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final Locale locale = Locale.getDefault();
        return mFragmentTitles.get(position).toUpperCase(locale);
    }

    public void setPageTitle(int position, String pageTitle) {
        mFragmentTitles.set(position, pageTitle);
    }
}
