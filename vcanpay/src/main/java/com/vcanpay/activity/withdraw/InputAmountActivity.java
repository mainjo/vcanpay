package com.vcanpay.activity.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

import org.vcanpay.eo.CustBankCard;

public class InputAmountActivity extends BaseActivity implements TextWatcher {

    EditText mAmount;
    Button mOk;

    CustBankCard currentBankCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_amount);

        Intent intent = getIntent();

        currentBankCard = (CustBankCard) intent.getExtras().get(WithdrawActivity.BANK_CARD_KEY);

        mAmount = (EditText) findViewById(R.id.amount);
        mAmount.addTextChangedListener(this);


        mOk = (Button) findViewById(R.id.button_ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double amount = Double.valueOf(mAmount.getText().toString());

                startConfirm(amount, currentBankCard);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_amount, menu);
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

    private void startConfirm(Double amount, CustBankCard card) {
        Intent intent = new Intent(this, WithdrawConfirmActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable(WithdrawActivity.BANK_CARD_KEY, card);
        bundle.putDouble(WithdrawActivity.AMOUNT, amount);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        //TODO 需要一个金额限制算法
        if (!TextUtils.isEmpty(mAmount.getText().toString())) {
            mOk.setEnabled(true);
            return;
        }

        mOk.setEnabled(false);
    }
}
