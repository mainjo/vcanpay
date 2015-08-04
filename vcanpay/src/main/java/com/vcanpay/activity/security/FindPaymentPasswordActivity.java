package com.vcanpay.activity.security;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.vcanpay.R;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class FindPaymentPasswordActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_payment_password);
    }

    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_find_payment_password);
    }
}
