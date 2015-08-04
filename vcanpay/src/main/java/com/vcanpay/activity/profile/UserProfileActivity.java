package com.vcanpay.activity.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.Application;
import com.vcanpay.Config;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustomInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class UserProfileActivity extends BaseActivity {
    public static final int DATE_DIALOG_ID = 1;

    EditText mFirstName;
    EditText mLastName;
    Spinner mSpIdentificationType;
    EditText mEtIdCard;
    Spinner mSpSex;
    EditText mEtBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mSpIdentificationType = (Spinner) findViewById(R.id.identification_type);
        mEtIdCard = (EditText) findViewById(R.id.id_card);
        mSpSex = (Spinner) findViewById(R.id.sex);
        mEtBirthday = (EditText) findViewById(R.id.birthday);

        mFirstName.setText(Application.customInfo.getFirstName());
        mLastName.setText(Application.customInfo.getLastName());
        mSpIdentificationType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, IdentificationTypeDump.ITEMS));
        mSpIdentificationType.setSelection(0);
        mEtIdCard.setText(getCurrentCustomer().getICardId());

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_dropdown_item);
        mSpSex.setAdapter(adapter);
        if (!TextUtils.isEmpty(Application.customInfo.getSex())) {
            mSpSex.setSelection(Integer.valueOf(Application.customInfo.getSex()));
        }
        mEtBirthday.setText(getCurrentCustomer().getBirthDate() == null ? "" : new SimpleDateFormat(Config.DATE_FORMAT_1).format(getCurrentCustomer().getBirthDate()));
        mEtBirthday.setTag(getCurrentCustomer().getBirthDate());

        mEtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_save) {
            makeSaveRequest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeSaveRequest() {
        showProgressDialog(this);

        final CustomInfo customInfo = new CustomInfo();
        customInfo.setCustomId(getCurrentCustomer().getCustomId());
        customInfo.setFirstName(mFirstName.getText().toString());
        customInfo.setLastName(mLastName.getText().toString());
        customInfo.setSex(mSpSex.getSelectedItemPosition() + "");
        customInfo.setBirthDate((Date) mEtBirthday.getTag());
        customInfo.setICardId(mEtIdCard.getText().toString());

//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'+'HH:mm:ss").create();
//        String json1 = gson.toJson(customInfo);
        String json1 = String.format("{" +
                        "\"customId\":%d," +
                        "\"customScore\":0," +
                        "\"firstName\":\"%s\"," +
                        "\"lastName\":\"%s\"," +
                        "\"sex\":\"%s\"," +
                        "\"loginErrTimes\":0}",
                customInfo.getCustomId(),
                customInfo.getFirstName(),
                customInfo.getLastName(),
                customInfo.getSex()
        );

        String json2 = "{\"customInfo\":" + json1 + "}";
        String query = null;
        if (customInfo.getBirthDate() != null) {
            query = new SimpleDateFormat(Config.DATE_FORMAT_3).format(customInfo.getBirthDate());
        } else {
            query = "";
        }

        UpdateProfileRequest request = new UpdateProfileRequest(
                this,
                Request.Method.PUT,
                query,
                json2,
                json1,
                new Response.Listener<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(UpdateProfileResponse response) {
                        closeProgressDialog();

                        // 更新本地customInfo
                        CustomInfo currentCustomer = getCurrentCustomer();
                        currentCustomer.setFirstName(customInfo.getFirstName());
                        currentCustomer.setLastName(customInfo.getLastName());
                        currentCustomer.setSex(customInfo.getSex());
                        currentCustomer.setBirthDate(customInfo.getBirthDate());
                        currentCustomer.setICardId(customInfo.getICardId());
                        showAlertDialog(UserProfileActivity.this, getString(R.string.notify), response.getMessage());
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }


    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_user_profile);
    }

    public static class IdentificationType {
        public int id;
        public String text;

        public IdentificationType(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class IdentificationTypeDump {
        public static IdentificationType[] ITEMS = new IdentificationType[2];

        static {
            ITEMS[0] = new IdentificationType(0, "Id Card");
            ITEMS[1] = new IdentificationType(1, "Passport");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                Calendar calendar = Calendar.getInstance();
                return new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, datePickerListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);

            mEtBirthday.setText(new SimpleDateFormat(Config.DATE_FORMAT_1).format(c.getTime()));
            mEtBirthday.setTag(c.getTime());
        }
    };
}
