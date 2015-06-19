package com.vcanpay.activity.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vcanpay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityWithFragmentFragment extends Fragment {

    public ActivityWithFragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_with, container, false);
    }
}
