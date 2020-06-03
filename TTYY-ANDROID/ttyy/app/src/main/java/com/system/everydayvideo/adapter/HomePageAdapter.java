package com.system.everydayvideo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by miya95 on 2017/1/5.
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"测试1", "测试2", "测试3","测试4"};

    private ArrayList<Fragment> fragments;

    public HomePageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
