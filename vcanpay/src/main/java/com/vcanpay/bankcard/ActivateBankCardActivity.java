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
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.NoticeDialogFragment;
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustBankCard;
import org.vcanpay.eo.CustomInfo;

public class ActivateBankCardActivity extends BaseActivity implements TextWatcher, View.OnClickListener, NoticeDialogFragment.NoticeDialogListener {

    EditText mTvActivationCode;

    Button mButton;

    CustBankCard custBankCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_bank_card);

        init();
    }

    private void init() {
        custBankCard = (CustBankCard) getIntent().getExtras().get(AddBankCardActivity.BANK_CARD_KEY);
        mTvActivationCode = (EditText) findViewById(R.id.activation_code);
        mButton = (Button) findViewById(R.id.submit);
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
        if (!TextUtils.isEmpty(mTvActivationCode.getText().toString())) {
            mButton.setEnabled(true);
        } else {
            mButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        makeRequest();
    }

    private void makeRequest() {
        showProgressDialog(this);
        CustomInfo currentCustomer = getCurrentCustomer();
        ActivateBankCardRequest request = new ActivateBankCardRequest(
                currentCustomer.getCustomId(),
                custBankCard.getMobilePhone(),
                mTvActivationCode.getText().toString(),
                custBankCard.getUserName(),
                new Response.Listener<ActivateBankCardResponse>() {
                    @Override
                    public void onResponse(ActivateBankCardResponse response) {
                        closeProgressDialog();
//                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, response.getMessage(), R.string.input_the_amount_you_get, R.string.go_to_home);
//                        dialog.setNoticeDialogListener(ActivateBankCardActivity.this);
//                        dialog.show(getSupportFragmentManager(), "input amount");

                        Intent intent = new Intent(ActivateBankCardActivity.this, InputAmountToValidateActivity.class);
                        startActivity(intent);
                    }
                },
                new VolleyErrorListener(this)
        );
        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, InputAmountToValidateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Intent intent = new Intent(this, TabhostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
