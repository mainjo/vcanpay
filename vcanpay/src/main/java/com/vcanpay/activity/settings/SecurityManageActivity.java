package com.vcanpay.activity.settings;

import android.os.Bundle;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

public class SecurityManageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_manage);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new com.vcanpay.activity.settings.SecurityManageFragment(), "security").
                commit();
    }
}
