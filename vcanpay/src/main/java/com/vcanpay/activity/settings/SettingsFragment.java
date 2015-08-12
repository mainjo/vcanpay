package com.vcanpay.activity.settings;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vcanpay.R;
import com.vcanpay.activity.NoticeDialogFragment;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, NoticeDialogFragment.NoticeDialogListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.i("TEST", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TEST", "onCreate");
        addPreferencesFromResource(R.xml.pref_general);

    }

    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

        Log.i("TEST", "onCreateView");
        return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("TEST", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        Log.i("TEST", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("TEST", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("TEST", "onDetach");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("language_list")) {
            Log.i("SettingsFragment", key + ": " + sharedPreferences.getString(key, "my default"));
            showNoticeDialog();
        }
    }

    public void showNoticeDialog() {
        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.restart_to_change_the_language, 0, 0);
        dialog.setNoticeDialogListener(this);
        dialog.show(getChildFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.i("SettingFragment", "begin restarting....");
        Intent intent = getActivity().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Log.i("Vcanpay", "app is restarting...");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
