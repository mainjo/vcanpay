package com.vcanpay.activity.password;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

public class ResetPasswordActivity extends BaseActivity implements TextWatcher {
    EditText mEmail;
    EditText mIdCard;
    Button mButton;

    public static final String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        mEmail = (EditText) findViewById(R.id.email);
        mIdCard = (EditText) findViewById(R.id.id_card);
        mButton = (Button) findViewById(R.id.button);

        mEmail.addTextChangedListener(this);
        mIdCard.addTextChangedListener(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

    private void makeRequest() {
        showProgressDialog(this);

        final String email = mEmail.getText().toString();
        String idCard = mIdCard.getText().toString();

        String json1 = String.format("{" +
                        "\"customId\":0," +
                        "\"customScore\":0," +
                        "\"email\":\"%s\"," +
                        "\"ICardId\":\"%s\"," +
                        "\"loginErrTimes\":0," +
                        "\"registerFlag\":0}",
                email,
                idCard
        );

        String json2 = "{\"customInfo\":" + json1 + "}";

        ResetPasswordRequest request = new ResetPasswordRequest(
                this,
                json2,
                json1,
                new Response.Listener<ResetPasswordResponse>() {
                    @Override
                    public void onResponse(ResetPasswordResponse response) {
                        closeProgressDialog();
//                        showAlertDialog(ResetPasswordActivity.this, getString(R.string.notify), getString(R.string.reset_password_hint));

//                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, response.getMessage(), R)
                        Intent intent = new Intent(ResetPasswordActivity.this, ResetPasswordActivity2.class);
                        intent.putExtra(EMAIL_KEY, email);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this)
        );

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
        if (!TextUtils.isEmpty(mEmail.getText().toString()) && !TextUtils.isEmpty(mIdCard.getText().toString())) {
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }
    }
}
