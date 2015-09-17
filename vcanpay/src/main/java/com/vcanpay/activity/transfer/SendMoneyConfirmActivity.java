package com.vcanpay.activity.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SendMoneyConfirmActivity extends BaseActivity {


    TextView mAmount;
    TextView mReceiver;
    TextView mNote;
    Button mConfirm;

    SendMoneyActivity.TransferRequestObject transferRequestObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_confirm);

        Intent intent = getIntent();
        transferRequestObject = (SendMoneyActivity.TransferRequestObject) intent.getExtras().getSerializable(SendMoneyActivity.TRANSFER_REQUEST);

        mAmount = (TextView) findViewById(R.id.amount);
        mReceiver = (TextView) findViewById(R.id.receiver);
        mNote = (TextView) findViewById(R.id.note);

        mAmount.setText(NumberFormat.getCurrencyInstance().format(transferRequestObject.amount));
        mReceiver.setText(transferRequestObject.receiverEmail);
        mNote.setText(transferRequestObject.note);

        mConfirm = (Button) findViewById(R.id.confirm);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest(transferRequestObject);
            }
        });
    }

    private void makeRequest(SendMoneyActivity.TransferRequestObject object) {
        showProgressDialog(this);
//        Gson gson = new Gson();
//        String json2 = gson.toJson(object, TransferActivity.TransferRequestObject.class);

        String json1 = String.format("{" +
                        "\"custWaitRecId\":0," +
                        "\"today\":0," +
                        "\"amount\":%s," +
                        "\"peopleNum\":0," +
                        "\"recipientEmail\":\"%s\"," +
                        "\"customInfo\":{" +
                        "\"customId\":%d," +
                        "\"customName\":\"%s\"," +
                        "\"customScore\":0," +
                        "\"loginErrTimes\":0," +
                        "\"registerFlag\":0}}",
                object.amount,
                object.receiverEmail,
                object.customer.getCustomId(),
                object.customer.getCustomName()
        );


        String json2 = String.format("{" +
                        "\"amount\":%s," +
                        "\"customInfo\":{" +
                        "\"customId\":%d," +
                        "\"customName\":\"%s\"" +
                        "}," +
                        "\"recipientEmail\":\"%s\"" +
                        "}",
                new DecimalFormat("0.##").format(object.amount),
                object.customer.getCustomId(),
                object.customer.getCustomName(),
                object.receiverEmail
        );
        json2 = "{\"waitingRechangeBill\":" + json2 + "}";

        TransferRequest request = new TransferRequest(
                this,
                json2,
                json1,
                new Response.Listener<TransferResponse>() {
                    @Override
                    public void onResponse(TransferResponse response) {
                        closeProgressDialog();
                        showAlertDialog(
                                SendMoneyConfirmActivity.this,
                                getString(R.string.notify),
                                response.getMessage()
                        );
                    }
                },
                new VolleyErrorListener(this) {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                        if (error instanceof TransferRequest.BalanceSufficentException) {
                            showAlertDialog(SendMoneyConfirmActivity.this, getString(R.string.notify), getString(R.string.balance_sufficient));
                        }
                        if (error instanceof TransferRequest.AccountNotExistException) {
                            showAlertDialog(SendMoneyConfirmActivity.this, getString(R.string.notify), getString(R.string.account_not_exist));
                        }
                    }
                }
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }
}
