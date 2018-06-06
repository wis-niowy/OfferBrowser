package com.example.wisienka.mobileapplication.Adapters;

/**
 * Created by Wisienka on 2018-04-22.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.google.android.gms.maps.SupportMapFragment;

import com.example.wisienka.mobileapplication.Fragments.MapTabFragment;
import com.example.wisienka.mobileapplication.Fragments.RecyclerViewFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment mapTabFragment;
    Fragment recyclerViewFragment;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Fragment... fragments) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mapTabFragment = fragments[0];
        recyclerViewFragment = fragments[1];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return mapTabFragment;
            case 1:
                return recyclerViewFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}