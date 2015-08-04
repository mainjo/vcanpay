package com.vcanpay.activity.register;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class RegisterActivity extends BaseActivity implements RegisterFragment.OnFragmentInteractionListener,
        TextWatcher{

    public static final String FRAGMENT_TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, RegisterFragment.newInstance("", FRAGMENT_TAG))
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
