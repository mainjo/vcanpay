package com.vcanpay;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vcanpay.R;
import com.vcanpay.view.TimePicker;

import java.util.Calendar;

/**
 * Project Name:Vcanpay
 * <p/>
 * Created by patrick wai on 2015/8/21.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "TimePickerFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, second, true);
        timePickerDialog.setTitle(R.string.time);
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int second) {
        Log.i("TAG", "hour of day " + hourOfDay);
        Log.i("TAG", "minute " + minute);
        Log.i("TAG", "second: " + second);
    }
}
