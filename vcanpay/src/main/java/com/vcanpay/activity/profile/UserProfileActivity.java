package com.vcanpay.activity.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.vcanpay.R;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class UserProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_user_profile);
    }
}
