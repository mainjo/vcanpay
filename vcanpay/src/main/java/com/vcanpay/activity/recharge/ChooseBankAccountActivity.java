package com.vcanpay.activity.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.Config;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.bill.DateDeserializer;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.request.GetVcpAccountsRequest;

import org.vcanpay.eo.VcanpayBankAccount;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class ChooseBankAccountActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "ChooseBankAccountActivity";
    public static final String VCP_ACCOUNT_KEY = "vcp_account";
    Spinner mSpinnerAccounts;
    Button mButtonOk;

    ArrayAdapter<VcanpayBankAccount> mAccountAdapter;

    public static final String FIELD_BANK_NAME = "inputBankName";
    public static final String FIELD_PROVINCE = "inputProvince";
    public static final String FIELD_REGION = "region";

    public static final int SUCCESS_CODE = 200;
    public static final int FAILURE_CODE = 300;

    int accountId;
    String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_account);

        Intent intent = getIntent();

        mSpinnerAccounts = (Spinner) findViewById(R.id.spinner_account_list);
        mSpinnerAccounts.setOnItemSelectedListener(this);

        mButtonOk = (Button) findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgressDialog(this);
        makeRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_bank_account, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        VcanpayBankAccount selectedAccount = (VcanpayBankAccount) mSpinnerAccounts.getSelectedItem();

        if (selectedAccount != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(VCP_ACCOUNT_KEY, selectedAccount);
            intent.putExtras(bundle);
            setResult(SUCCESS_CODE, intent);
            finish();
        } else {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.get_account_failure));
        }
    }

    private void makeRequest() {

        String params = FIELD_BANK_NAME + "=" + 1 + "&" +
                FIELD_PROVINCE + "=" + 7 + "&" +
                FIELD_REGION + "=" + 1;

        GetVcpAccountsRequest request = new GetVcpAccountsRequest(
                params,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        closeProgressDialog();

                        if (response.statusCode == 200) {
                            try {
                                String json = new String(response.data, "utf-8");
                                Gson gson = new GsonBuilder()
                                        .registerTypeHierarchyAdapter(Date.class, new DateDeserializer())
                                        .create();

                                VcanpayBankAccount[] accounts = gson.fromJson(json, VcanpayBankAccount[].class);

                                if (accounts.length != 0) {

                                    mAccountAdapter = new ArrayAdapter<>(
                                            ChooseBankAccountActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            accounts
                                    );
                                } else {
                                    //TODO 当json数组长度是0时候
                                }

                                mSpinnerAccounts.setAdapter(mAccountAdapter);

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showAlertDialog(ChooseBankAccountActivity.this,
                                    getString(R.string.notify), getString(R.string.can_not_get_records));
                        }
                    }
                },
                new VolleyErrorListener(this)
        );

        try {
            request.addHeader("app_sign", Utils.MD5("1" + "7" + "1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String app_time = String.valueOf(System.currentTimeMillis() / 1000);

        String signString = null;
        String app_sign = null;
        try {
            signString = Config.app_key + Utils.MD5(Config.app_secret) + app_time + "1" + "7" + "1";

            app_sign = Utils.MD5(signString);
        } catch (Exception e) {
            e.printStackTrace();
        }



        request.addHeader("app_key", Config.app_key);
        try {
            request.addHeader("app_secret", Utils.MD5(Config.app_secret));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.addHeader("sign_string", signString);
        request.addHeader("app_sign", app_sign);
        request.addHeader("app_time", app_time);

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
