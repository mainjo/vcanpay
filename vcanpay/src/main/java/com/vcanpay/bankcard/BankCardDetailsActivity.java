package com.vcanpay.bankcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.utils.NavUtils;

import org.vcanpay.eo.CustBankCard;

public class BankCardDetailsActivity extends BaseActivity implements View.OnClickListener {

    TextView mBankName;
    TextView mCardNum;
    TextView mCardOwner;
    TextView mCardType;
    Button mStatusIndicator;

    CustBankCard mBankCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_details);

        mBankCard = (CustBankCard) getIntent().getExtras().getSerializable(NavUtils.BANK_CARD_KEY);

        init(mBankCard);
    }

    private void init(CustBankCard bankCard) {
        mBankName = (TextView) findViewById(R.id.bank_name);
        mCardNum = (TextView) findViewById(R.id.card_num);
        mCardOwner = (TextView) findViewById(R.id.card_owner);
        mCardType = (TextView) findViewById(R.id.card_type);
        mStatusIndicator = (Button) findViewById(R.id.status_indicator);

        mBankName.setText(getBankName(this, Integer.valueOf(bankCard.getBankName())));
        mCardNum.setText(bankCard.getBankCardNo());
        mCardOwner.setText(bankCard.getUserName());
        mCardType.setText(getAccountType(this, Integer.valueOf(bankCard.getAccountType())));

        if (bankCard.getHaveMobileCheck() != null && bankCard.getHaveMobileCheck().equals("1") && bankCard.getHaveMoneyCheck() != null && bankCard.getHaveMoneyCheck().equals("5")) {
            mStatusIndicator.setText(R.string.bank_card_verified);
            mStatusIndicator.setEnabled(false);
        } else {
            mStatusIndicator.setText(R.string.bank_card_unverified);
            mStatusIndicator.setEnabled(true);
            mStatusIndicator.setOnClickListener(this);
        }
    }


    public String getAccountType(Context context, int code) {
        switch (code) {
            case 0:
                return context.getString(R.string.credit_card);
            case 1:
                return context.getString(R.string.debit_card);
            case 2:
                return context.getString(R.string.bank_card_other);
        }
        return context.getString(R.string.bank_card_other);
    }


    public String getBankName(Context context, int code) {
        switch (code) {
            case 0:
                return context.getString(R.string.bank0);
            case 1:
                return context.getString(R.string.bank1);
            case 2:
                return context.getString(R.string.bank2);
            case 3:
                return context.getString(R.string.bank3);
        }

        return context.getString(R.string.unkown_bank);
    }

    public String getActivationStatus(Context context, int code) {
        switch (code) {
            case 0:
                return context.getString(R.string.status_unactivated);
            case 1:
                return context.getString(R.string.status_activated);
        }

        return context.getString(R.string.status_unactivated);
    }

    public String getMoneyCheckStatus(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.money_check_staus_1);
            case 2:
                return context.getString(R.string.money_check_staus_2);
            case 3:
                return context.getString(R.string.money_check_staus_3);
            case 4:
                return context.getString(R.string.money_check_staus_4);
            case 5:
                return context.getString(R.string.money_check_staus_5);
        }
        return context.getString(R.string.unknown_status);
    }

    @Override
    public void onClick(View v) {
        if (mBankCard.getHaveMobileCheck() != null && mBankCard.getHaveMobileCheck().equals("1")) {
            if (mBankCard.getHaveMoneyCheck() == null || (!mBankCard.getHaveMoneyCheck().equals("5"))) {
                Intent intent = new Intent(getIntent());
                intent.setClass(this, InputAmountToValidateActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(getIntent());
            intent.setClass(this, ActivateBankCardActivity.class);
            startActivity(intent);
        }
    }
}
