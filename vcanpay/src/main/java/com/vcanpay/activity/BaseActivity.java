package com.vcanpay.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.Application;
import com.vcanpay.activity.recharge.AreaContentProvider2;
import com.vcanpay.activity.recharge.ChooseRegionActivity;
import com.vcanpay.validator.Validator;
import com.vcanpay.view.CustomProgressBarDialog;

import org.vcanpay.eo.CustomInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BaseActivity extends ActionBarActivity {

    public static final String CUSTOMER_INFO = "custom_info";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_EMAIL = "customer_email";
    public static final String CUSTOMER_PASSWORD = "customer_password";

    public static final String DEFAULT = "default_lang";
    public static final String ZH = "zh";
    public static final String EN = "en";
    public static final String TH = "th";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        initConfig();
    }

    public void initConfig() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sp.getString("language_list", DEFAULT);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals(DEFAULT)) {

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

    private static CustomInfo currentCustomer = null;

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
//        if (currentCustomer == null) {
//            SharedPreferences sp = getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE);
//
//            int customerId = sp.getInt(CUSTOMER_ID, -1);
//            String customerName = sp.getString(CUSTOMER_NAME, null);
//            if (customerId != -1 && !TextUtils.isEmpty(customerName)) {
//                currentCustomer = new CustomInfo();
//                currentCustomer.setCustomId(customerId);
//                currentCustomer.setCustomName(customerName);
//            }
//        }
//
//        return currentCustomer;
        return Application.customInfo;
    }


    protected boolean validate(TextView textView, Validator validator) {
        boolean result = validator.validate(textView.getText().toString());

        Log.i("BaseActivity", textView.getText() + ": " + result);
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
}