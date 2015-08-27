package com.vcanpay.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.vcanpay.R;


public class SettingsActivity extends BaseActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_settings);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        setTitle(R.string.title_activity_settings);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new com.vcanpay.activity.settings.SettingsFragment(), "settings").
                commit();

    }
}
