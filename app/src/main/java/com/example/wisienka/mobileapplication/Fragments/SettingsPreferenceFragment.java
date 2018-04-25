package com.example.wisienka.mobileapplication.Fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.example.wisienka.mobileapplication.R;

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
}