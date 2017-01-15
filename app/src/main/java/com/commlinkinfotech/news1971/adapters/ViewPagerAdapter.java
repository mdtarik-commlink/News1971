package com.commlinkinfotech.news1971.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.commlinkinfotech.news1971.interfaces.FragmentUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarik on 4/20/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return original title here.
        // return "Title " + position;

        if (getItem(position) instanceof FragmentUtility) {
            return ((FragmentUtility) getItem(position)).getTitle();
        } else {
            return "";
        }
    }
}