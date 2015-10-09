package com.vcanpay.activity.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.NoticeDialogFragment;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.SignInActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

public class ResetPasswordActivity2 extends BaseActivity implements TextWatcher, View.OnClickListener, NoticeDialogFragment.NoticeDialogListener {
    EditText mNewPassword;
    EditText mConfirmPassword;
    EditText mRandomNumber;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_activity2);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mRandomNumber = (EditText) findViewById(R.id.random_number);

        mNewPassword.addTextChangedListener(this);
        mConfirmPassword.addTextChangedListener(this);
        mRandomNumber.addTextChangedListener(this);

        mButton = (Button) findViewById(R.id.reset_password);
        mButton.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!TextUtils.isEmpty(mNewPassword.getText().toString()) &&
                !TextUtils.isEmpty(mConfirmPassword.getText().toString()) &&
                !TextUtils.isEmpty(mRandomNumber.getText().toString())) {
            mButton.setEnabled(true);

        } else {
            mButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {

        if (!mNewPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            showAlertDialog(this, getString(R.string.notify), getString(R.string.password_not_same));
            return;
        }

        makeRequest();
    }

    private void makeRequest() {

        String json1 = String.format(
                "{" +
                "\"email\":\"%s\"," +
                "\"newPassWord\":\"%s\"" +
                "}",
                getIntent().getStringExtra(ResetPasswordActivity.EMAIL_KEY),
                mNewPassword.getText().toString()
        );

        String json2 = "{\"updatePwd\":" + json1 + "}";

        ResetPasswordRequest2 request = new ResetPasswordRequest2(
                mRandomNumber.getText().toString(),
                json2,
                json1,
                new Response.Listener<ResetPasswordResponse2>() {
                    @Override
                    public void onResponse(ResetPasswordResponse2 response) {
                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.reset_password_success, R.string.ok, 0);
                        dialog.setNoticeDialogListener(ResetPasswordActivity2.this);
                        dialog.setCancelable(false);
                        dialog.show(getSupportFragmentManager(), "reset_password");
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
