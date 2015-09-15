package com.vcanpay.activity.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.NoticeDialogFragment;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

public class CellphoneRechargeActivity extends BaseActivity implements TextWatcher, NoticeDialogFragment.NoticeDialogListener {

    LinearLayout mContainer;
    EditText mTvPhoneNum;
    Spinner mCarriers;
    Spinner mSpAmountList;
    Button mButtonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellphone_recharge);

        initComponent();
    }

    private void initComponent() {
        mContainer = (LinearLayout) findViewById(R.id.container);

        mTvPhoneNum = (EditText) findViewById(R.id.phone_num);
        mTvPhoneNum.addTextChangedListener(this);
        mSpAmountList = (Spinner) findViewById(R.id.spinner_amount_list);
        mCarriers = (Spinner) findViewById(R.id.carrier);
        mButtonOk = (Button) findViewById(R.id.button_ok);

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        mSpAmountList.setAdapter(ArrayAdapter.createFromResource(this, R.array.recharge_amount_list, android.R.layout.simple_spinner_dropdown_item));
        mCarriers.setAdapter(ArrayAdapter.createFromResource(this, R.array.carriers, android.R.layout.simple_spinner_dropdown_item));
    }

    private void makeRequest() {
        showProgressDialog(this);
        int amount = Integer.valueOf((String) mSpAmountList.getSelectedItem());
        String phoneNum = mTvPhoneNum.getText().toString();
        String carrier = (String) mCarriers.getSelectedItem();

        CellphoneRechargeRequest request = new CellphoneRechargeRequest(
                carrier,
                phoneNum,
                amount,
                getCurrentCustomer().getCustomId(),
                new Response.Listener<CellphoneRechargeResponse>() {
                    @Override
                    public void onResponse(CellphoneRechargeResponse response) {
                        closeProgressDialog();
                        TextView tv;
                        tv = new TextView(CellphoneRechargeActivity.this);
                        tv.setText("卡号：" + response.getPhoneCard().cardNum);
                        mContainer.addView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tv = new TextView(CellphoneRechargeActivity.this);
                        tv.setText("密码：" + response.getPhoneCard().cardSecret);
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
        if (!TextUtils.isEmpty(mTvPhoneNum.getText().toString())) {
            mButtonOk.setEnabled(true);
            return;
        }
        mButtonOk.setEnabled(false);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, TabhostActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
