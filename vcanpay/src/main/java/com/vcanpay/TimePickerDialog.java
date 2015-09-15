package com.vcanpay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.example.vcanpay.R;
import com.vcanpay.view.TimePicker;

/**
 * Created by patrick wai on 2015/9/7.
 */
public class TimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener,
        TimePicker.OnTimeChangedListener {

    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String IS_24_HOUR = "is24hour";
    private static final String SECOND = "second";

    private final TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetCallback;

    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final int mInitialSecond;
    private final boolean mIs24HourView;


    /**
     * The callback interface used to indicate the user is done filling in
     * the time (they clicked on the 'Done' button).
     */
    public interface OnTimeSetListener {

        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute The minute that was set.
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute, int second);
    }

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public TimePickerDialog(Context context,
                            OnTimeSetListener callBack,
                            int hourOfDay, int minute, int second, boolean is24HourView) {
        this(context, 0, callBack, hourOfDay, minute, second, is24HourView);
    }

    static int resolveDialogTheme(Context context, int resid) {
        if (resid == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(0, outValue, true);
            return outValue.resourceId;
        } else {
            return resid;
        }
    }

    /**
     * @param context Parent.
     * @param theme the theme to apply to this dialog
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public TimePickerDialog(Context context, int theme, OnTimeSetListener callBack, int hourOfDay,
                            int minute, int second, boolean is24HourView) {
        super(context, resolveDialogTheme(context, theme));

        mTimeSetCallback = callBack;
        mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mInitialSecond = second;
        mIs24HourView = is24HourView;

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);
//        setButtonPanelLayoutHint(LAYOUT_HINT_SIDE);

        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(mIs24HourView);
        mTimePicker.setCurrentHour(mInitialHourOfDay);
        mTimePicker.setCurrentMinute(mInitialMinute);
        mTimePicker.setCurrentSecond(mInitialSecond);
        mTimePicker.setOnTimeChangedListener(this);
//        mTimePicker.setValidationCallback(mValidationCallback);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute, int second) {
        // do nothing
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mTimeSetCallback != null) {
                    mTimeSetCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute(), mTimePicker.getCurrentSecond());
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    /**
     * Sets the current time.
     *
     * @param hourOfDay The current hour within the day.
     * @param minuteOfHour The current minute within the hour.
     */
    public void updateTime(int hourOfDay, int minuteOfHour) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minuteOfHour);
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(HOUR, mTimePicker.getCurrentHour());
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putInt(SECOND, mTimePicker.getCurrentSecond());
        state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int hour = savedInstanceState.getInt(HOUR);
        final int minute = savedInstanceState.getInt(MINUTE);
        final int second = savedInstanceState.getInt(SECOND);
        mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setCurrentSecond(second);
    }

//    private final ValidationCallback mValidationCallback = new ValidationCallback() {
//        @Override
//        public void onValidationChanged(boolean valid) {
//            final Button positive = getButton(BUTTON_POSITIVE);
//            if (positive != null) {
//                positive.setEnabled(valid);
//            }
//        }
//    };
}
