package com.vcanpay.activity.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.NoticeDialogFragment;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.TabHostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustBankCard;
import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.CustomerAccount;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class WithdrawConfirmActivity extends BaseActivity implements View.OnClickListener, NoticeDialogFragment.NoticeDialogListener {
    TextView mTvAmount;
    TextView mTvBank;

    Button mBtnOk;

    CustBankCard currentBankCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_confirm);

        Intent intent = getIntent();
        currentBankCard = (CustBankCard) intent.getExtras().get(WithdrawActivity.BANK_CARD_KEY);

        double amount = intent.getDoubleExtra(WithdrawActivity.AMOUNT, 0);
        mTvAmount = (TextView) findViewById(R.id.amount);
        mTvBank = (TextView) findViewById(R.id.bank);
        mBtnOk = (Button) findViewById(R.id.ok);

        mTvAmount.setText(NumberFormat.getCurrencyInstance(Locale.CHINA).format(amount));
        mTvAmount.setTag(amount);
        mTvBank.setText(getBankName(Integer.valueOf(currentBankCard.getBankName())));
        mBtnOk.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_withdraw_confirm, menu);
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

    @Override
    public void onClick(View v) {
        makeRequest();
    }

    private void makeRequest() {
        showProgressDialog(this);

        SubmitObject object = new SubmitObject();
        object.setAmount((double) mTvAmount.getTag());
        object.setCustomInfo(getCurrentCustomer());


//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'+'hh:mm:ss")
//                .create();
//
//        String json1 = gson.toJson(object);

        String json1 = String.format("{" +
                        "\"custWithdrawId\":0," +
                        "\"amount\":%s," +
                        "\"bankCardNo\":\"%s\"," +
                        "\"adminId\":0," +
                        "\"customInfo\":{" +
                        "\"customId\":%d," +
                        "\"customScore\":0," +
                        "\"loginErrTimes\":0," +
                        "\"registerFlag\":0}}",
                new DecimalFormat("0.##").format(object.amount),
                currentBankCard.getBankCardNo(),
                object.getCustomInfo().getCustomId()
        );
        String json2 = "{withdrawBill: " + json1 + "}";


        WithdrawRequest request = new WithdrawRequest(
                json2,
                json1,
                new Response.Listener<CustomerAccount>() {
                    @Override
                    public void onResponse(CustomerAccount response) {
                        closeProgressDialog();
                        getCurrentCustomer().setCustomAccounts(response);
                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.withdraw_submit_success, R.string.ok, 0);
                        dialog.setNoticeDialogListener(WithdrawConfirmActivity.this);
                        dialog.show(getSupportFragmentManager(), "withdraw_success");
                    }
                },
                new VolleyErrorListener(this){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        closeProgressDialog();
                        super.onErrorResponse(error);
                    }
                }
        );
        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, TabHostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finish();

    }

    public static class SubmitObject {
        double amount;
        CustomInfo customInfo;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public CustomInfo getCustomInfo() {
            return customInfo;
        }

        public void setCustomInfo(CustomInfo customInfo) {
            this.customInfo = customInfo;
        }
    }

    public String getBankName(int code) {
        switch (code) {
            case 0:
                return getString(R.string.bank0);
            case 1:
                return getString(R.string.bank1);
            case 2:
                return getString(R.string.bank2);
            case 3:
                return getString(R.string.bank3);
        }

        return getString(R.string.unkown_bank);
    }

    public String getActivationStatus(int code) {
        switch (code) {
            case 0:
                return getString(R.string.status_unactivated);
            case 1:
                return getString(R.string.status_activated);
        }

        return getString(R.string.status_unactivated);
    }

    public String getMoneyCheckStatus(int code) {
        switch (code) {
            case 1:
                return getString(R.string.money_check_staus_1);
            case 2:
                return getString(R.string.money_check_staus_2);
            case 3:
                return getString(R.string.money_check_staus_3);
            case 4:
                return getString(R.string.money_check_staus_4);
            case 5:
                return getString(R.string.money_check_staus_5);
        }

        return getString(R.string.unknown_status);
    }
}
