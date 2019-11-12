package com.karigor.tolet_seeker.ui.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.karigor.tolet_seeker.R;

/**
 * Created by touhid on 2019-11-09.
 * Email: udoy.touhid@gmail.com
 */
public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
    }
}