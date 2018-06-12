package com.example.wisienka.mobileapplication.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.wisienka.mobileapplication.Fragments.MainPageFragment;
import com.example.wisienka.mobileapplication.Fragments.MapTabFragment;
import com.example.wisienka.mobileapplication.Fragments.RecyclerViewFragment;
import com.example.wisienka.mobileapplication.Fragments.SettingsPreferenceFragment;
import com.example.wisienka.mobileapplication.Models.Offer;
import com.example.wisienka.mobileapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Fragment recyclerFragment;
    Fragment mainPageFragment;
    Fragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            setContentView(R.layout.activity_main);
            //recyclerFragment = new RecyclerViewFragment();
            mainPageFragment = new MainPageFragment();
            settingsFragment = new SettingsPreferenceFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.add(R.id.activity_main_layout, mainPageFragment, "mainPageFragment");
            fragmentTransaction.commit();
            mainPageFragment.getFragmentManager().executePendingTransactions(); // force commit now
        } //else {
//            mainPageFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mainPageFragment");
//            settingsFragment = getSupportFragmentManager().getFragment(savedInstanceState, "settingsFragment");
//        }


//        Toolbar toolbar = (Toolbar) mainPageFragment.getView().findViewById(R.id.toolbar);
////        toolbar.setSubtitle("Test Subtitle");
////        toolbar.inflateMenu(R.menu.preference_menu);
//        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "mainPageFragment", mainPageFragment);
        getSupportFragmentManager().putFragment(outState, "settingsFragment", settingsFragment);
    }

    /**
     * Here the preference menu is created
     * These methods could be overriden in any fragment like MainPageFragment if we expected different menu behaviour on that level
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                SettingsPreferenceFragmentClick();
                return true;
            case R.id.action_reset:
                ((MainPageFragment)mainPageFragment).ClearOffersContainers();
                return true;
            default:
                //return super.onOptionsItemSelected(item);
                break;
        }
        return false;
    }

    public void UpdateOffersContainers(List<Offer> offersList){
        ((MainPageFragment)mainPageFragment).UpdateOffersContainers(offersList);
    }

    private void SettingsPreferenceFragmentClick(){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_layout, settingsFragment).addToBackStack(null).commit();
    }

    public MainPageFragment getMainPageFragment() {
        return (MainPageFragment)mainPageFragment;
    }

//    @Override
//    public void onBackPressed() {
//        // if there is a fragment and the back stack of this fragment is not empty,
//        // then emulate 'onBackPressed' behaviour, because in default, it is not working
//        FragmentManager fm = getSupportFragmentManager();
//        int count = fm.getBackStackEntryCount();
//        for (Fragment frag : fm.getFragments()) {
//            if (frag.isVisible()) {
//                FragmentManager childFm = frag.getChildFragmentManager();
//                if (childFm.getBackStackEntryCount() > 0) {
//                    childFm.popBackStack();
//                    return;
//                }
//            }
//        }
//        super.onBackPressed();
//    }
}
