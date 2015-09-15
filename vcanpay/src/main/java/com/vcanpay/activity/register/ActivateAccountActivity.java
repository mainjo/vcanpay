package com.vcanpay.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.SignInActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

public class ActivateAccountActivity extends BaseActivity implements TextWatcher {

    EditText mEtActivateCode;
    Button mBtnActivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_account);

        mEtActivateCode = (EditText) findViewById(R.id.activate_code);
        mEtActivateCode.addTextChangedListener(this);
        mBtnActivate = (Button) findViewById(R.id.btn_activate);
        mBtnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("EMAIL");

                if (TextUtils.isEmpty(email)) {
                    email = "223153516@qq.com";
                }
                String activateCode = mEtActivateCode.getText().toString();
                makeRequest(email, activateCode);
            }
        });
    }

    private void makeRequest(String email, String activateCode) {

        showProgressDialog(this);
        ActivateAccountRequest request = new ActivateAccountRequest(this, email, activateCode,
                new Response.Listener<ActivateAccountResponse>() {
                    @Override
                    public void onResponse(ActivateAccountResponse response) {
                        closeProgressDialog();
                        Toast.makeText(ActivateAccountActivity.this, R.string.activated_success, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivateAccountActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);

                        if (error instanceof ActivateAccountRequest.VerificationCodeError) {
                            Toast.makeText(ActivateAccountActivity.this, R.string.verification_code_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mEtActivateCode.getText().toString())) {
            mBtnActivate.setEnabled(true);
        } else {
            mBtnActivate.setEnabled(false);
        }
    }
}
