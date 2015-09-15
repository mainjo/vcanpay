package com.vcanpay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.MyApplication;
import com.vcanpay.TimePickerDialog;
import com.vcanpay.activity.recharge.AreaContentProvider2;
import com.vcanpay.activity.recharge.ChooseRegionActivity;
import com.vcanpay.validator.Validator;
import com.vcanpay.view.CustomProgressBarDialog;

import org.vcanpay.eo.CustomInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BaseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public static final String CUSTOMER_INFO = "custom_info";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_EMAIL = "customer_email";
    public static final String CUSTOMER_PASSWORD = "customer_password";

    public static final String DEFAULT_LANG = "default_lang";
    public static final String ZH = "zh";
    public static final String EN = "en";
    public static final String TH = "th";



    protected static final int DATE_DIALOG_ID = 1;
    protected static final int TIME_DIALOG_ID = 2;
    protected static final String CALENDAR_KEY = "date_key";
    private static final String TAG = "BaseActivity";

    private Calendar tempCalendar;  // hold the calendar which is set by the previous DatePickerDialog



    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        initConfig();
//        setupToolbar();
    }

    private void setupToolbar() {


        Toolbar toolbar = new Toolbar(this);
        toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setSupportActionBar(toolbar);


    }

    public void initConfig() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sp.getString("language_list", DEFAULT_LANG);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals(DEFAULT_LANG)) {

            config.locale = Locale.getDefault();
        }

        if (language.equals(ZH)) {

            config.locale = Locale.SIMPLIFIED_CHINESE;
        }

        if (language.equals(EN)) {

            config.locale = Locale.ENGLISH;
        }

        if (language.equals(TH)) {

            config.locale = Locale.SIMPLIFIED_CHINESE;
        }

        resources.updateConfiguration(config, dm);
    }




    private CustomProgressBarDialog mDialog;

    protected void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.action_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    protected void startActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    public void showProgressDialog(Context context) {
        mDialog = CustomProgressBarDialog.createDialog(context);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public CustomInfo getCurrentCustomer() {

        return ((MyApplication)getApplication()).getCustomInfo();
    }


    protected boolean validate(TextView textView, Validator validator) {
        boolean result = validator.validate(textView.getText().toString());

        return result;
    }

    public static List<ChooseRegionActivity.Area> getAreaList(int parentId, AreaContentProvider2 db) {
        Cursor c = db.queryByParentId(parentId);

        List<ChooseRegionActivity.Area> result = new ArrayList<>();
        ChooseRegionActivity.Area area;
        while (c.moveToNext()) {
            area = new ChooseRegionActivity.Area(c.getInt(0), c.getString(1), c.getInt(2));
            result.add(area);
        }

        return result;
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        super.onCreateDialog(id, args);
        Calendar c = null;
        if (args != null) {
            c = (Calendar) args.getSerializable(CALENDAR_KEY);
        }
        if (c == null) {
            c = Calendar.getInstance();
        }

        setTempCalendar(c);
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
//                return new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialog, this, year, month, day);
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMaxDate(System.currentTimeMillis());
                return datePickerDialog;
            case TIME_DIALOG_ID:
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, second, true);
//                TimePicker timePicker = timePickerDialog.
                return timePickerDialog;
        }
        return null;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }


    @Override
    public void onTimeSet(com.vcanpay.view.TimePicker view, int hourOfDay, int minute, int second) {

    }

    public Calendar getTempCalendar() {
        return tempCalendar;
    }

    public void setTempCalendar(Calendar tempCalendar) {
        this.tempCalendar = tempCalendar;
    }

}
