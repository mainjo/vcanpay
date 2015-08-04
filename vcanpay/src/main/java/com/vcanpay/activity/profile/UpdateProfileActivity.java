package com.vcanpay.activity.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.bankcard.AddBankCardActivity;

import org.vcanpay.eo.CustomInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateProfileActivity extends BaseActivity {

    EditText mFirstName;
    EditText mLastName;
    RadioGroup mIdentityType;
    EditText mIdCard;
    Spinner mSex;
    EditText mBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mIdentityType = (RadioGroup) findViewById(R.id.identification_type);
        mIdCard = (EditText) findViewById(R.id.id_card);
        mSex = (Spinner) findViewById(R.id.spinner_sex);
        mBirthday = (EditText) findViewById(R.id.birthday);

        mSex.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, AddBankCardActivity.SexDummy.ITEMS));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_profile, menu);
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
        CustomInfo customInfo = new CustomInfo();
        customInfo.setCustomId(getCurrentCustomer().getCustomId());
        customInfo.setFirstName(mFirstName.getText().toString());
        customInfo.setLastName(mLastName.getText().toString());
        customInfo.setICardId(mIdCard.getText().toString());
        customInfo.setSex(((AddBankCardActivity.Sex) mSex.getSelectedItem()).id);
        customInfo.setBirthDate((Date)mBirthday.getTag());

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'+'HH:mm:ss").create();
        String json1 = gson.toJson(customInfo);
        String json2 = "{\"customInfo\":" + json1 + "}";

        String query = "?birthData=" + new SimpleDateFormat("yyyy-MM-dd'+'HH:mm:ss").format(customInfo.getBirthDate());

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

                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);

    }
}
