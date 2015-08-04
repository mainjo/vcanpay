package com.vcanpay.activity.bill;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vcanpay.R;

public class PayActivity extends ActionBarActivity {

    ListView mLvUnpaidBills;

    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mLvUnpaidBills = (ListView) findViewById(R.id.unpaid_bill_list);
        mLvUnpaidBills.setAdapter(mAdapter);
    }


    public void makeRequest() {
        
    }
}
