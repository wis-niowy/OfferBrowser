package com.example.wisienka.mobileapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.example.wisienka.mobileapplication.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Wisienka on 2018-04-24.
 */

public class SettingsPreferenceFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preference_fragment, rootKey);
    }

//    @Override
//    public void onDestroyView() {
////        SharedPreferences settings = getContext().getSharedPreferences("FILTER_PREFS", MODE_PRIVATE);
////
////        // Writing data to SharedPreferences
////        SharedPreferences.Editor editor = settings.edit();
////        editor.putFloat("price_to", 40);
////        editor.commit();
//        super.onDestroyView();
//    }
}