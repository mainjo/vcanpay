package com.vcanpay.activity;

import android.os.Bundle;

import com.example.vcanpay.R;


public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(R.string.title_activity_settings);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new com.vcanpay.activity.settings.SettingsFragment(), "settings").
                commit();
    }
}
