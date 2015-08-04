package com.vcanpay.activity;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by patrick wai on 2015/7/8.
 */
public class DatePickerListener implements DatePickerDialog.OnDateSetListener {
    TextView mTextView;

    public DatePickerListener(TextView textView) {
        mTextView = textView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);

        mTextView.setText(c.getTime().toString());
        mTextView.setTag(c.getTime());
        mTextView.setEnabled(false);
    }
}
