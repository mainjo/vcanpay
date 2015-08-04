package com.vcanpay.activity.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustomInfo;

public class UpdateMobileActivity extends BaseActivity {

    EditText mCellPhone;
    EditText mOfficeTel;
    EditText mHomeTel;
    Spinner mCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile_request);

        mCellPhone = (EditText) findViewById(R.id.cell_phone);
        mOfficeTel = (EditText) findViewById(R.id.work_tel);
        mHomeTel = (EditText) findViewById(R.id.home_tel);
        mCountry = (Spinner) findViewById(R.id.country);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(adapter);

        CustomInfo customer = getCurrentCustomer();

        if (!TextUtils.isEmpty(customer.getMobileOfCountry())) {
            mCountry.setSelection(Integer.valueOf(customer.getMobileOfCountry()));
        }
        mCellPhone.setText(customer.getMobiePhone());
        mOfficeTel.setText(customer.getTeleOfOffice());
        mHomeTel.setText(customer.getTeleOfHome());
    }

    public static class Country{
        public static Country[] countries = new Country[2];
        static {
            countries[0] = new Country("0", "Thailand");
            countries[1] = new Country("1", "China");
        }

        public String id;
        public String name;

        public Country(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_mobile_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        customInfo.setMobiePhone(mCellPhone.getText().toString());
        customInfo.setMobileOfCountry(mCountry.getSelectedItemPosition()+"");
        customInfo.setTeleOfHome(mHomeTel.getText().toString());
        customInfo.setTeleOfOffice(mOfficeTel.getText().toString());

//        String json1 = new Gson().toJson(customInfo, CustomInfo.class);


        String json1 = String.format(
                "{\"customId\":%d," +
                        "\"customScore\":0," +
                        "\"mobiePhone\":\"%s\"," +
                        "\"mobileOfCountry\":\"%s\"," +
                        "\"loginErrTimes\":0," +
                        "\"teleOfHome\":\"%s\"," +
                        "\"teleOfOffice\":\"%s\"}",
                customInfo.getCustomId(),
                customInfo.getMobiePhone(),
                customInfo.getMobileOfCountry(),
                customInfo.getTeleOfHome(),
                customInfo.getTeleOfOffice()
                );


        String json2 = "{\"customInfo\": " + json1 + "}";

        UpdateMobileRequest request = new UpdateMobileRequest(
                json2,
                json1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        getCurrentCustomer().setMobiePhone(mCellPhone.getText().toString());
                        getCurrentCustomer().setMobileOfCountry(mCountry.getSelectedItemPosition()+"");
                        getCurrentCustomer().setTeleOfHome(mHomeTel.getText().toString());
                        getCurrentCustomer().setTeleOfOffice(mOfficeTel.getText().toString());
                        showAlertDialog(UpdateMobileActivity.this, getString(R.string.notify), "success");
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);

        queue.addToRequestQueue(request);
    }
}
