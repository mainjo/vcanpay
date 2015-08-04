package com.vcanpay.activity.recharge;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class RechargeActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    ListView mRechargeTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        String[] rechargeTypes = getResources().getStringArray(R.array.recharge_type);


        mRechargeTypes = (ListView) findViewById(R.id.recharge_type_list);

        ListAdapter adapter = ArrayAdapter.createFromResource(this, R.array.recharge_type, android.R.layout.simple_list_item_1);
        mRechargeTypes.setAdapter(adapter);
        mRechargeTypes.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            startActivity(this, PrepaidCardRechargeActivity.class);
        }

        if (position == 1) {
            startActivity(this, AddFundActivity.class);
        }
    }
}
