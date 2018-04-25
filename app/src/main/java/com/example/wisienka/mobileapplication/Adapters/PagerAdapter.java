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

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MapTabFragment();
                //return new SupportMapFragment();
            case 1:
                return new RecyclerViewFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}