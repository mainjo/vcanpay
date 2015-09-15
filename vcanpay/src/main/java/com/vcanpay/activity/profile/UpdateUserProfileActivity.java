package com.vcanpay.activity.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.Config;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustomInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class UpdateUserProfileActivity extends BaseActivity {
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

        mFirstName.setText(getCurrentCustomer().getFirstName());
        mLastName.setText(getCurrentCustomer().getLastName());
        mSpIdentificationType.setSelection(0);
        mEtIdCard.setText(getCurrentCustomer().getICardId());

        if (!TextUtils.isEmpty(getCurrentCustomer().getSex())) {
            mSpSex.setSelection(Integer.valueOf(getCurrentCustomer().getSex()) - 1);
        }

//        mEtBirthday.setText(getCurrentCustomer().getBirthDate() == null ? "" : new SimpleDateFormat(Config.DATE_FORMAT_1).format(getCurrentCustomer().getBirthDate()));
        mEtBirthday.setText(getCurrentCustomer().getBirthDate() == null ? "" : DateFormat.getDateInstance().format(getCurrentCustomer().getBirthDate()));

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
        customInfo.setSex(mSpSex.getSelectedItemPosition() + 1 + "");
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
                        "\"loginErrTimes\":0," +
                        "\"registerFlag\":0}",
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

                        showAlertDialog(UpdateUserProfileActivity.this, getString(R.string.notify), getString(R.string.update_profile_success));
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


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);

        DateFormat.getDateInstance();

//        mEtBirthday.setText(new SimpleDateFormat(Config.DATE_FORMAT_1).format(c.getTime()));
        mEtBirthday.setText(DateFormat.getDateInstance().format(c.getTime()));
        mEtBirthday.setTag(c.getTime());
    }
}
