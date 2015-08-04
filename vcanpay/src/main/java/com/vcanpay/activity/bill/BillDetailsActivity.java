package com.vcanpay.activity.bill;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.Config;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.pay.BillsToBePaidActivity;
import com.vcanpay.activity.transfer.TransferRequest;
import com.vcanpay.activity.transfer.TransferResponse;

import org.vcanpay.eo.CustomTrade;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillDetailsActivity extends BaseActivity {

    TextView mTvTradeSn;
    TextView mTvCreatedTime;
    TextView mTvFee;
    TextView mTvFeeType;
    TextView mTvOrderId;
    TextView mTvAmount;
    TextView mTvStatus;
    TextView mTvSeller;
    TextView mTradeType;

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        final CustomTrade bill = (CustomTrade) getIntent().getExtras().getSerializable(BillsToBePaidActivity.BILL_KEY);
        mTradeType = (TextView) findViewById(R.id.tradeType);
        mTvTradeSn = (TextView) findViewById(R.id.tradeSn);
        mTvCreatedTime = (TextView) findViewById(R.id.createdTime);
        mTvFee = (TextView) findViewById(R.id.fee);
        mTvStatus = (TextView) findViewById(R.id.status);

//        mTvFeeType = (TextView) findViewById(R.id.tradeType);
//        mTvOrderId = (TextView) findViewById(R.id.order_id);
        mTvAmount = (TextView) findViewById(R.id.amount);
//        mTvState = (TextView) findViewById(R.id.state);
        mTvSeller = (TextView) findViewById(R.id.seller);

        mTradeType.setText(getTradeType(this, bill.getTradeType()));
        mTvStatus.setText(getTradeState(this, bill.getTradeState()));

        mTvTradeSn.setText(bill.getTradeSn());
        mTvCreatedTime.setText(new SimpleDateFormat(Config.DATE_FORMAT_1).format(bill.getCreateTime()));
//        mTvFeeType.setText(bill.getTradeType());
//        mTvOrderId.setText(bill.getOutId().toString());

        mTvSeller.setText("Vcanbuy");

        NumberFormat format = NumberFormat.getCurrencyInstance();

        mTvAmount.setText(format.format(bill.getTradeMoney()));
        mTvFee.setText(format.format(bill.getTradeFee()));
//        mTvSeller.setText("xxxxx");

        mButton = (Button) findViewById(R.id.btn_pay);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                makeRequest(bill);
//            }
//        });
    }

    private void makeRequest(CustomTrade bill) {
        showProgressDialog(this);
//        Gson gson = new Gson();
//        String json1 = gson.toJson(bill);


        String json1 = "";


        String json2 = "{\"waitingRechangeBill\": " + json1 + "}";

        TransferRequest request = new TransferRequest(
                this,
                json2,
                json1,
                new Response.Listener<TransferResponse>() {
                    @Override
                    public void onResponse(TransferResponse response) {
                        closeProgressDialog();
                        showAlertDialog(
                                BillDetailsActivity.this,
                                getString(R.string.notify),
                                response.getMessage()
                        );
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill_details, menu);
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

    public String getFeeType(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.cellphone_recharge);
            case 2:
                return context.getString(R.string.shopping);
            case 3:
                return context.getString(R.string.water_eletric);
        }

        return context.getString(R.string.unknow_fee);
    }

    public String getTradeType(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.add_fund);
            case 2:
                return context.getString(R.string.withdraw);
            case 3:
                return context.getString(R.string.send_money);
            case 4:
                return context.getString(R.string.chase_money);
            case 5:
                return context.getString(R.string.cellphone_recharge);
            case 6:
                return context.getString(R.string.water_and_electronice_fee);
            case 7:
                return context.getString(R.string.pay_for_others);
            case 8:
                return context.getString(R.string.loan);
            case 9:
                return context.getString(R.string.send_envelop);
        }

        return context.getString(R.string.unknown_bill_title);
    }

    public String getTradeState(Context context, int code) {
        switch (code) {
            case 1:
                return context.getString(R.string.initial_value);
            case 2:
                return context.getString(R.string.trade_not_verified);
            case 3:
                return context.getString(R.string.trade_verified_success);
            case 4:
                return context.getString(R.string.trade_verified_fail);
        }

        return context.getString(R.string.unknow_trade_state);
    }
}
