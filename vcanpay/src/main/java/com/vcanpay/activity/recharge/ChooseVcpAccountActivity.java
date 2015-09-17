package com.vcanpay.activity.recharge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class ChooseVcpAccountActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

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


    int mBankId;
    int mRegionId;
    int mProvinceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_account);

        Intent intent = getIntent();
        mBankId = intent.getIntExtra(ChooseRegionActivity.BANK_CODE, 1);
        mRegionId = intent.getIntExtra(ChooseRegionActivity.REGION_CODE, 1);
        mProvinceId = intent.getIntExtra(ChooseRegionActivity.PROVINCE_CODE, 7);

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
    public void onClick(View v) {

        VcanpayBankAccount selectedAccount = (VcanpayBankAccount) mSpinnerAccounts.getSelectedItem();

        if (selectedAccount != null) {
            Intent intent = new Intent(this, AddFundActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(VCP_ACCOUNT_KEY, selectedAccount);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.get_account_failure));
        }
    }

    private void makeRequest() {

        String params = FIELD_BANK_NAME + "=" + mBankId + "&" +
                FIELD_PROVINCE + "=" + mProvinceId + "&" +
                FIELD_REGION + "=" + mRegionId;

        GetVcpAccountsRequest request = new GetVcpAccountsRequest(
                params,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        closeProgressDialog();
                        try {
                            String json = new String(response.data, "utf-8");
                            Gson gson = new GsonBuilder()
                                    .registerTypeHierarchyAdapter(Date.class, new DateDeserializer())
                                    .create();
                            VcanpayBankAccount[] accounts = gson.fromJson(json, VcanpayBankAccount[].class);
                            if (accounts != null && accounts.length != 0) {
                                mAccountAdapter = new ArrayAdapter<>(
                                        ChooseVcpAccountActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        accounts
                                );
                                mSpinnerAccounts.setAdapter(mAccountAdapter);
                            } else {
                                showAlertDialog(ChooseVcpAccountActivity.this,
                                        getString(R.string.notify), getString(R.string.no_records));
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new VolleyErrorListener(this)
        );


        try {
            String app_time = String.valueOf(System.currentTimeMillis() / 1000);
            String signString = Config.app_key + Utils.MD5(Config.app_secret) + app_time + mBankId + mProvinceId + mRegionId;
            String app_sign = Utils.MD5(signString);

            request.addHeader("app_key", Config.app_key);
            request.addHeader("app_secret", Utils.MD5(Config.app_secret));
            request.addHeader("app_time", app_time);
            request.addHeader("app_sign", app_sign);
            request.addHeader("sign_string", signString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        finish();
    }
}
