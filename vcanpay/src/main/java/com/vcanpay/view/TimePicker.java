package com.vcanpay.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.example.vcanpay.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by patrick wai on 2015/9/5.
 */
public class TimePicker extends FrameLayout {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;

    // state
    private boolean mIs24HourView;
    private boolean mIsAm;

    // ui
    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;
    private NumberPicker mSecondPicker;
    private NumberPicker mAmPmPicker;

    private boolean mIsEnabled = DEFAULT_ENABLED_STATE;
    private boolean mHourWithTwoDigit;
    private char mHourFormat;


    private OnTimeChangedListener mOnTimeChangedListener;

    private Locale mCurrentLocale;


    public TimePicker(Context context) {
        this(context, null, 0);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        layoutInflater.inflate(R.layout.time_picker, this, true);

        mHourPicker = (NumberPicker) findViewById(R.id.hour);
        mHourPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });

        mHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
//                updateInputState();
                if (!is24HourView()) {
                    if ((oldVal == HOURS_IN_HALF_DAY - 1 && newVal == HOURS_IN_HALF_DAY) ||
                            (oldVal == HOURS_IN_HALF_DAY && newVal == HOURS_IN_HALF_DAY - 1)) {
                        mIsAm = !mIsAm;
                        updateAmPmControl();
                    }
                }
                onTimeChanged();
            }
        });
        mMinutePicker = (NumberPicker) findViewById(R.id.minute);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        mMinutePicker.setOnLongPressUpdateInterval(100);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
//                updateInputState();
                int minValue = mMinutePicker.getMinValue();
                int maxValue = mMinutePicker.getMaxValue();
                if (oldVal == maxValue && newVal == minValue) {
                    int newHour = mHourPicker.getValue() + 1;
//                    if (!is24HourView() && newHour == HOURS_IN_HALF_DAY) {
//                        mIsAm = !mIsAm;
//                        updateAmPmControl();
//                    }
                    if (newHour == 60) {
                        newHour = 0;
                    }
                    mHourPicker.setValue(newHour);
                } else if (oldVal == minValue && newVal == maxValue) {
                    int newHour = mHourPicker.getValue() - 1;
//                    if (!is24HourView() && newHour == HOURS_IN_HALF_DAY - 1) {
//                        mIsAm = !mIsAm;
//                        updateAmPmControl();
//                    }
                    if (newHour == -1) {
                        newHour = 59;
                    }
                    mHourPicker.setValue(newHour);
                }
                onTimeChanged();
            }
        });

        mSecondPicker = (NumberPicker) findViewById(R.id.second);
        mSecondPicker.setMinValue(0);
        mSecondPicker.setMaxValue(59);
        mSecondPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        mSecondPicker.setOnLongPressUpdateInterval(100);
        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int minValue = mSecondPicker.getMinValue();
                int maxValue = mSecondPicker.getMaxValue();
                if (oldVal == maxValue && newVal == minValue) {
                    int newMinute = mMinutePicker.getValue() + 1;
                    if (newMinute == 60) {
                        newMinute = 0;
                    }
                    mMinutePicker.setValue(newMinute);
                } else if (oldVal == minValue && newVal == maxValue) {
                    int newMinute = mMinutePicker.getValue() - 1;
                    if (newMinute == -1) {
                        newMinute = 59;
                    }
                    mMinutePicker.setValue(newMinute);
                }
                onTimeChanged();
            }
        });

        // am/pm
        String[] amPmStrings = getAmPmStrings(context);
        mAmPmPicker = (NumberPicker) findViewById(R.id.amPm);
        mAmPmPicker.setMinValue(0);
        mAmPmPicker.setMaxValue(1);
        mAmPmPicker.setDisplayedValues(amPmStrings);
        mAmPmPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                    updateInputState();
                picker.requestFocus();
                mIsAm = !mIsAm;
                updateAmPmControl();
                onTimeChanged();
            }
        });

        if (isAmPmAtStart()) {
            // Move the am/pm view to the beginning
            ViewGroup amPmParent = (ViewGroup) findViewById(R.id.timePickerLayout);
            amPmParent.removeView(mAmPmPicker);
            amPmParent.addView(mAmPmPicker, 0);
            // Swap layout margins if needed. They may be not symmetrical (Old Standard Theme
            // for example and not for Holo Theme)
            MarginLayoutParams lp =
                    (MarginLayoutParams) mAmPmPicker.getLayoutParams();
            final int startMargin = lp.getMarginStart();
            final int endMargin = lp.getMarginEnd();
            if (startMargin != endMargin) {
                lp.setMarginStart(endMargin);
                lp.setMarginEnd(startMargin);
            }
        }

        getHourFormatData();

        // update controls to initial state
        updateHourControl();
        updateMinuteControl();
        updateAmPmControl();

        // set to current time
        Calendar tempCalendar = Calendar.getInstance();
        setCurrentHour(tempCalendar.get(Calendar.HOUR_OF_DAY));
        setCurrentMinute(tempCalendar.get(Calendar.MINUTE));

        if (!isEnabled()) {
            setEnabled(false);
        }

        // set the content descriptions
        // setContentDescriptions();

        // If not explicitly specified this view is important for accessibility.
        if (getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        }
    }

    private void updateAmPmControl() {
        if (is24HourView()) {
            mAmPmPicker.setVisibility(View.GONE);
        } else {
            int index = mIsAm ? Calendar.AM : Calendar.PM;
            mAmPmPicker.setValue(index);
            mAmPmPicker.setVisibility(View.VISIBLE);

        }
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
    }

    private void onTimeChanged() {
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        if (mOnTimeChangedListener != null) {
            mOnTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute(), getCurrentSecond());
        }
    }

    public void setCurrentHour(Integer currentHour) {
        setCurrentHour(currentHour, true);
    }

    private void setCurrentHour(int currentHour, boolean notifyTimeChanged) {
        if (currentHour == getCurrentHour()) {
            return;
        }
        if (!is24HourView()) {
            // convert [0,23] ordinal to wall clock display
            if (currentHour >= HOURS_IN_HALF_DAY) {
                mIsAm = false;
                if (currentHour > HOURS_IN_HALF_DAY) {
                    currentHour = currentHour - HOURS_IN_HALF_DAY;
                }
            } else {
                mIsAm = true;
                if (currentHour == 0) {
                    currentHour = HOURS_IN_HALF_DAY;
                }
            }
            updateAmPmControl();
        }
        mHourPicker.setValue(currentHour);
        if (notifyTimeChanged) {
            onTimeChanged();
        }
    }

    public int getCurrentHour() {
        int currentHour = mHourPicker.getValue();
        if (is24HourView()) {
            return currentHour;
        } else if (mIsAm) {
            return currentHour % HOURS_IN_HALF_DAY;
        } else {
            return (currentHour % HOURS_IN_HALF_DAY) + HOURS_IN_HALF_DAY;
        }
    }

    public void setCurrentMinute(int currentMinute) {
        if (currentMinute == getCurrentMinute()) {
            return;
        }
        mMinutePicker.setValue(currentMinute);
        onTimeChanged();
    }

    public int getCurrentMinute() {
        return mMinutePicker.getValue();
    }

    public void setCurrentSecond(int currentSecond) {
        if (currentSecond == getCurrentSecond()) {
            return;
        }
        mSecondPicker.setValue(currentSecond);
        onTimeChanged();
    }

    public int getCurrentSecond() {
        return mSecondPicker.getValue();
    }

    public void setIs24HourView(Boolean is24HourView) {
        if (mIs24HourView == is24HourView) {
            return;
        }
        // cache the current hour since spinner range changes and BEFORE changing mIs24HourView!!
        int currentHour = getCurrentHour();
        // Order is important here.
        mIs24HourView = is24HourView;
        getHourFormatData();
        updateHourControl();
        // set value after spinner range is updated
        setCurrentHour(currentHour, false);
        updateMinuteControl();
        updateAmPmControl();
    }

    public boolean is24HourView() {
        return mIs24HourView;
    }

    private void getHourFormatData() {
        final String bestDateTimePattern = DateFormat.getBestDateTimePattern(Locale.ENGLISH,
                (mIs24HourView) ? "Hm" : "hm");
        final int lengthPattern = bestDateTimePattern.length();
        mHourWithTwoDigit = false;
        char hourFormat = '\0';
        // Check if the returned pattern is single or double 'H', 'h', 'K', 'k'. We also save
        // the hour format that we found.
        for (int i = 0; i < lengthPattern; i++) {
            final char c = bestDateTimePattern.charAt(i);
            if (c == 'H' || c == 'h' || c == 'K' || c == 'k') {
                mHourFormat = c;
                if (i + 1 < lengthPattern && c == bestDateTimePattern.charAt(i + 1)) {
                    mHourWithTwoDigit = true;
                }
                break;
            }
        }
    }


    private void updateHourControl() {
        if (is24HourView()) {
            // 'k' means 1-24 hour
            if (mHourFormat == 'k') {
                mHourPicker.setMinValue(1);
                mHourPicker.setMaxValue(24);
            } else {
                mHourPicker.setMinValue(0);
                mHourPicker.setMaxValue(23);
            }
        } else {
            // 'K' means 0-11 hour
            if (mHourFormat == 'K') {
                mHourPicker.setMinValue(0);
                mHourPicker.setMaxValue(11);
            } else {
                mHourPicker.setMinValue(1);
                mHourPicker.setMaxValue(12);
            }
        }
//        mHourPicker.setFormatter(mHourWithTwoDigit ? NumberPicker.getTwoDigitFormatter() : null);
    }

    private void updateMinuteControl() {
//        if (is24HourView()) {
//            mMinuteSpinnerInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        } else {
//            mMinuteSpinnerInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//        }
    }



    public void setCurrentLocale(Locale locale) {
        if (locale.equals(mCurrentLocale)) {
            return;
        }
        mCurrentLocale = locale;
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    public interface OnTimeChangedListener {

        /**
         * @param view      The view associated with this listener.
         * @param hourOfDay The current hour.
         * @param minute    The current minute.
         * @param second    The current second.
         */
        void onTimeChanged(TimePicker view, int hourOfDay, int minute, int second);
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getCurrentHour(), getCurrentMinute(), getCurrentSecond());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentHour(ss.getHour(), true);
        setCurrentMinute(ss.getMinute());
        setCurrentSecond(ss.getSecond());
    }

    private static class SavedState extends BaseSavedState {
        private final int mHour;
        private final int mMinute;
        private final int mSecond;

        private SavedState(Parcelable superState, int hour, int minute, int second) {
            super(superState);
            mHour = hour;
            mMinute = minute;
            mSecond = second;
        }

        private SavedState(Parcel in) {
            super(in);
            mHour = in.readInt();
            mMinute = in.readInt();
            mSecond = in.readInt();
        }

        public int getHour() {
            return mHour;
        }

        public int getMinute() {
            return mMinute;
        }

        public int getSecond() {
            return mSecond;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mHour);
            dest.writeInt(mMinute);
            dest.writeInt(mSecond);
        }

        @SuppressWarnings({"unused", "hiding"})
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public static String[] getAmPmStrings(Context context) {
        return context.getResources().getStringArray(R.array.ampam);
    }


    private boolean isAmPmAtStart() {
        //TODO
        final String bestDateTimePattern = DateFormat.getBestDateTimePattern(Locale.ENGLISH,
                "hm" /* skeleton */);

        return bestDateTimePattern.startsWith("a");
    }
}
