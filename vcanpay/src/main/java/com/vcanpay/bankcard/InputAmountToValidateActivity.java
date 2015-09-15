package com.vcanpay.bankcard;

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
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import java.math.BigDecimal;

public class InputAmountToValidateActivity extends BaseActivity implements TextWatcher, View.OnClickListener, NoticeDialogFragment.NoticeDialogListener {

    EditText mTvAmount;
    Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_amount_to_validate);
        init();
    }

    private void init() {
        mTvAmount = (EditText) findViewById(R.id.amount);
        mTvAmount.addTextChangedListener(this);
        mBtnSubmit = (Button) findViewById(R.id.submit);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(mTvAmount.getText().toString())) {
            mBtnSubmit.setEnabled(true);
        } else {
            mBtnSubmit.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        makeRequest();
    }

    private void makeRequest() {
        showProgressDialog(this);

        int customerId = getCurrentCustomer().getCustomId();
//        double amount = Double.valueOf(mTvAmount.getText().toString());
//        int customerId = 189;
//        double amount =
        InputAccountToValidateRequest request = new InputAccountToValidateRequest(
                customerId,
                new BigDecimal(mTvAmount.getText().toString()),
                new Response.Listener<InputAccountToValidateResponse>() {
                    @Override
                    public void onResponse(InputAccountToValidateResponse response) {
                        closeProgressDialog();
                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.add_bank_card_success, R.string.go_to_home, R.string.cancel);
                        dialog.setNoticeDialogListener(InputAmountToValidateActivity.this);
                        dialog.show(getSupportFragmentManager(), "validate bank card");
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, TabhostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
