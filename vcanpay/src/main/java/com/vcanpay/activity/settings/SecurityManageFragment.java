package com.vcanpay.activity.settings;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

import com.example.vcanpay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecurityManageFragment extends PreferenceFragment {


    public SecurityManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_security);
    }


}
