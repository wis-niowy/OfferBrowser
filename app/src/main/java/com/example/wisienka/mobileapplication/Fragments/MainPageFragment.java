package com.example.wisienka.mobileapplication.Fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.wisienka.mobileapplication.Adapters.PagerAdapter;
import com.example.wisienka.mobileapplication.Adapters.RecyclerViewAdapter;
import com.example.wisienka.mobileapplication.Helpers.OfferBrowserAsyncTask;
import com.example.wisienka.mobileapplication.Models.Offer;
import com.example.wisienka.mobileapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class MainPageFragment extends Fragment {

    //Fragment settingsFragment;
    Fragment mapTabFragment;
    Fragment recyclerViewFragment;

    ViewPager viewPager;
    PagerAdapter adapter; // viewPager adapter

    public List<Offer> offersList;

    private boolean wasCreated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_page_layout, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back arrow

        if (/*savedInstanceState == null ||*/ !wasCreated){

            //settingsFragment = new SettingsPreferenceFragment();
            mapTabFragment = new MapTabFragment();
            recyclerViewFragment = new RecyclerViewFragment();
            offersList = new ArrayList<Offer>();

        } //else {
//            mapTabFragment = getActivity().getSupportFragmentManager().getFragment(savedInstanceState, "mapTabFragment");
//            recyclerViewFragment = getActivity().getSupportFragmentManager().getFragment(savedInstanceState, "recyclerViewFragment");
//        }

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        //necessarily getChildFragmentManager() has to be passed - not getFragmentManager() as it makes tab content disappear after page is restored
        adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), mapTabFragment, recyclerViewFragment);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        wasCreated = true;

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getActivity().getSupportFragmentManager().putFragment(outState, "mapTabFragment", mapTabFragment);
        getActivity().getSupportFragmentManager().putFragment(outState, "recyclerViewFragment", recyclerViewFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Here the preference menu is created
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.preference_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void UpdateOffersContainers(List<Offer> list){
        offersList.clear();
        offersList.addAll(list);
        ((MapTabFragment)mapTabFragment).updateMapState(offersList);
        ((RecyclerViewFragment)recyclerViewFragment).updateRecyclerState(offersList);
    }

    public void ClearOffersContainers(){
        offersList.clear();
        ((MapTabFragment)mapTabFragment).updateMapState(offersList);
        ((RecyclerViewFragment)recyclerViewFragment).updateRecyclerState(offersList);
    }

    public void runAsyncTask(){
        new OfferBrowserAsyncTask(this).execute();
    }

    public MapTabFragment getMapTabFragment() {
        return (MapTabFragment)mapTabFragment;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                SettingsPreferenceFragmentClick();
//                return true;
//            default:
//                //return super.onOptionsItemSelected(item);
//                break;
//        }
//        return false;
//    }
//
//    private void SettingsPreferenceFragmentClick(){
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_layout, settingsFragment).addToBackStack(null).commit();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
