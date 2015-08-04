package com.vcanpay.activity.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.recharge.AreaContentProvider2;

import org.vcanpay.eo.CustomInfo;

public class UpdateAddressActivity extends BaseActivity {

    public static final String TAG = "UpdateAddressActivity";

    EditText mStreet;
    EditText mDistrict;
    EditText mCity;
    EditText mProvince;
    EditText mCountry;
    EditText mPostCode;

    static AreaContentProvider2 mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address_request);

        if (mDb == null) {
            mDb = new AreaContentProvider2(this);
        }

        mStreet = (EditText) findViewById(R.id.street);
        mDistrict = (EditText) findViewById(R.id.district);
        mCity = (EditText) findViewById(R.id.city);
        mProvince = (EditText) findViewById(R.id.province);
        mCountry = (EditText) findViewById(R.id.country);
        mPostCode = (EditText) findViewById(R.id.post_code);

        CustomInfo customer = getCurrentCustomer();

        mStreet.setText(customer.getAddress());
        mDistrict.setText(customer.getDistrict());
        mCity.setText(customer.getCity());
        mProvince.setText(customer.getProvince());
        mCountry.setText(customer.getCountry());
        mPostCode.setText(customer.getPostCode());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_address_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_save) {
            makeUpdateAddressRequest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeUpdateAddressRequest() {

        CustomInfo customInfo = getCurrentCustomer();
        customInfo.setAddress(mStreet.getText().toString());
        customInfo.setDistrict(mDistrict.getText().toString());
        customInfo.setProvince(mDistrict.getText().toString());
        customInfo.setCountry("1");
        customInfo.setPostCode(mPostCode.getText().toString());

//        Gson gson = new Gson();
//        String json1 = gson.toJson(customInfo, CustomInfo.class);

        String json1 = String.format(
                "{\"customId\":%d," +
                        "\"address\":\"%s\"," +
                        "\"city\":\"%s\"," +
                        "\"country\":\"%s\"," +
                        "\"customScore\":0," +
                        "\"district\":\"%s\"," +
                        "\"postCode\":\"%s\"," +
                        "\"province\":\"%s\"," +
                        "\"loginErrTimes\":0}",
                customInfo.getCustomId(),
                customInfo.getAddress(),
                customInfo.getCity(),
                customInfo.getCountry(),
                customInfo.getDistrict(),
                customInfo.getPostCode(),
                customInfo.getProvince()
        );

        String json2 = "{\"customInfo\": " + json1 + "}";

        UpdateAddressRequest request = new UpdateAddressRequest(
                this,
                json2,
                json1,
                new Response.Listener<UpdateAddressResponse>() {
                    @Override
                    public void onResponse(UpdateAddressResponse response) {
                        showAlertDialog(UpdateAddressActivity.this, getString(R.string.notify), response.getMessage());
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof AuthFailureError) {
                            Log.i(TAG, "author error");
                            showAlertDialog(UpdateAddressActivity.this, getString(R.string.notify), error.getMessage());
                            return;
                        }

                        if (error != null) {
                            Log.i(TAG, "volley error");
                            showAlertDialog(UpdateAddressActivity.this, getString(R.string.notify), error.getMessage());
                        }
                    }
                }
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }
}
