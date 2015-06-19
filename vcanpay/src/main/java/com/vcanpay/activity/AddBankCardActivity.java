package com.vcanpay.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.vcanpay.R;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class AddBankCardActivity extends ActionBarActivity {

    Spinner mCardType;
    Spinner mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);

        mCardType = (Spinner) findViewById(R.id.card_type);
        mCity = (Spinner) findViewById(R.id.city);

        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.account_type, android.R.layout.simple_spinner_dropdown_item);
        mCardType.setAdapter(adapter);

        SpinnerAdapter adapterCity = ArrayAdapter.createFromResource(this, R.array.cities_thailand, android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(adapterCity);
    }
}
