package com.example.wisienka.mobileapplication.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.wisienka.mobileapplication.Fragments.MainPageFragment;
import com.example.wisienka.mobileapplication.Fragments.SettingsPreferenceFragment;
import com.example.wisienka.mobileapplication.R;

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
            if (savedInstanceState == null){
                // created for the first time
                fragmentTransaction.add(R.id.activity_main_layout, mainPageFragment, "mainPageFragment");
                fragmentTransaction.commit();
                mainPageFragment.getFragmentManager().executePendingTransactions(); // force commit now
            }
        }
        int a = 0;

//        Toolbar toolbar = (Toolbar) mainPageFragment.getView().findViewById(R.id.toolbar);
////        toolbar.setSubtitle("Test Subtitle");
////        toolbar.inflateMenu(R.menu.preference_menu);
//        setSupportActionBar(toolbar);
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
            default:
                //return super.onOptionsItemSelected(item);
                break;
        }
        return false;
    }

    private void SettingsPreferenceFragmentClick(){
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_layout, settingsFragment).addToBackStack(null).commit();
    }
}
