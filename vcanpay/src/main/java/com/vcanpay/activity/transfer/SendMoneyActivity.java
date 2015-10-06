package com.vcanpay.activity.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

import org.vcanpay.eo.CustomInfo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SendMoneyActivity extends BaseActivity implements TextWatcher{

    EditText mEtAmount;
    EditText mEtEmail;
    EditText mEtNote;

    Button mOk;

    public static final String TRANSFER_REQUEST = "transfer_request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        mEtEmail = (EditText) findViewById(R.id.email);
        mEtAmount = (EditText) findViewById(R.id.amount);
        mEtNote = (EditText) findViewById(R.id.note);

        mEtEmail.addTextChangedListener(this);
        mEtAmount.addTextChangedListener(this);
        mEtNote.addTextChangedListener(this);

        mOk = (Button) findViewById(R.id.action_ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                BigDecimal amount = new BigDecimal(mEtAmount.getText().toString());
                String note = mEtNote.getText().toString();

                TransferRequestObject transferRequestObject = new TransferRequestObject();
                transferRequestObject.amount = amount;
                transferRequestObject.receiverEmail = email;
                transferRequestObject.note = note;

                transferRequestObject.customer = getCurrentCustomer();

                Intent intent = new Intent(SendMoneyActivity.this, SendMoneyConfirmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRANSFER_REQUEST, transferRequestObject);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isEmpty(mEtEmail.getText().toString()) &&
                !isEmpty(mEtAmount.getText().toString())) {
            mOk.setEnabled(true);
        } else {
            mOk.setEnabled(false);
        }
    }

    public boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    public static class TransferRequestObject implements Serializable{
        public BigDecimal amount;
        public String receiverEmail;
        public String note;
        public CustomInfo customer;

        public TransferRequestObject() {

        }
    }
}
