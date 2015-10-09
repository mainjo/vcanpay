package com.vcanpay.activity.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.CustomTrade;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillsToBePaidActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String BILL_KEY = "bill_key";

    ListView mLvBills;
    ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_to_be_paid);

        mLvBills = (ListView) findViewById(R.id.bills);
        mLvBills.setOnItemClickListener(this);

        makeRequest();

    }

    public void setEmptyText(String text) {
        TextView tv = (TextView) findViewById(R.id.emptyText);
        tv.setText(text);
    }

    private void makeRequest() {
        showProgressDialog(this);
        CustomInfo customInfo = getCurrentCustomer();
        int customerId = customInfo.getCustomId();

        BillRequest request = new BillRequest(
                customerId,
                new Response.Listener<CustomTrade[]>() {
                    @Override
                    public void onResponse(CustomTrade[] response) {
                        closeProgressDialog();
                        if (response != null) {
                            mAdapter = new MyAdapter(
                                    BillsToBePaidActivity.this,
                                    R.layout.list_item_bill2,
                                    response);
                            mLvBills.setAdapter(mAdapter);
                            return;
                        }
                        setEmptyText(getString(R.string.no_data));
                    }
                },
                new VolleyErrorListener(BillsToBePaidActivity.this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bills_to_be_paid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BillDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BILL_KEY, (CustomTrade) mAdapter.getItem(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static class MyAdapter extends ArrayAdapter<CustomTrade> {
        LayoutInflater mInflater;
        int mResource;

        public MyAdapter(Context context, int resource, CustomTrade[] objects) {
            super(context, resource, objects);
            this.mResource = resource;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = mInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }

            CustomTrade customTrade = getItem(position);
            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            TextView tvDate = (TextView) view.findViewById(R.id.date);
            TextView tvAmount = (TextView) view.findViewById(R.id.amount);
            TextView tvStatus = (TextView) view.findViewById(R.id.status);

            tvTitle.setText(getTradeType(getContext(), customTrade.getTradeType()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tvDate.setText(format.format(new Date(customTrade.getCreateTime())));
            tvAmount.setText(customTrade.getTradeMoney().toString());
            tvStatus.setText(getTradeState(getContext(), customTrade.getTradeState()));
            return view;
        }

        void setText(TextView textView, String value, String defaultValue) {
            if (value != null && !value.equals("")) {
                textView.setText(value);
                return;
            }
            textView.setText(defaultValue);
        }


        public String getFeeType(Context context, int code) {
            switch (code) {
                case 1:
                    return context.getString(R.string.cellphone_recharge);
                case 2:
                    return context.getString(R.string.shopping);
                case 3:
                    return context.getString(R.string.water_electricity);
            }

            return context.getString(R.string.unknown_fee);
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
                    return context.getString(R.string.water_and_electricity_fee);
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

            return context.getString(R.string.unknown_trade_state);
        }
    }
}
