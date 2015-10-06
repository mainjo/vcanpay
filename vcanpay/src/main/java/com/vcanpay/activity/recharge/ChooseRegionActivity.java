package com.vcanpay.activity.recharge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseRegionActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner mSpinnerCountry;
    Spinner mSpinnerRegion;
    Spinner mSpinnerProvince;
    Spinner mSpinnerBank;

    Button mBtnNextStep;

    static AreaContentProvider2 mDb;

    ArrayAdapter mCountryAdapter;
    ArrayAdapter<Area> mRegionAdapter;
    ArrayAdapter<Area> mProvinceAdapter;
    ArrayAdapter mBankAdapter;

    int mSelectedCountry;
    int mSelectedRegion;
    int mSelectedProvince;
    int mSelectedBank;

    public static final String COUNTRY_CODE = "country";
    public static final String REGION = "region";
    public static final String PROVINCE = "province";
    public static final String BANK_CODE = "bank";

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_region);

        if (mDb == null) {
            mDb = new AreaContentProvider2(this);
        }

        init();
    }

    private void init() {
        mSpinnerCountry = (Spinner) findViewById(R.id.spinner_country);
        mSpinnerRegion = (Spinner) findViewById(R.id.spinner_region);
        mSpinnerProvince = (Spinner) findViewById(R.id.spinner_province);
        mSpinnerBank = (Spinner) findViewById(R.id.spinner_bank);

        mCountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{getString(R.string.thailand)});
        mRegionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getRegionList(0));
        mProvinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<Area>());
        mBankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.bank_names));

        mSpinnerCountry.setAdapter(mCountryAdapter);
        mSpinnerProvince.setAdapter(mProvinceAdapter);
        mSpinnerRegion.setAdapter(mRegionAdapter);
        mSpinnerBank.setAdapter(mBankAdapter);

        mSpinnerCountry.setOnItemSelectedListener(this);
        mSpinnerRegion.setOnItemSelectedListener(this);
        mSpinnerProvince.setOnItemSelectedListener(this);
        mSpinnerBank.setOnItemSelectedListener(this);

        mBtnNextStep = (Button) findViewById(R.id.next_step);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, ChooseVcpAccountActivity.class);
        intent.putExtra(COUNTRY_CODE, mSpinnerCountry.getSelectedItemPosition());
        intent.putExtra(REGION, ((Area)mSpinnerRegion.getSelectedItem()).areaName);
        intent.putExtra(PROVINCE, ((Area)mSpinnerProvince.getSelectedItem()).areaName);

        intent.putExtra(BANK_CODE, mSpinnerBank.getSelectedItemPosition() + 1);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();

        switch (parentId) {
            case R.id.spinner_region:
                int newRegionId = mRegionAdapter.getItem(position).areaId;
                if (mSelectedRegion != newRegionId) {
                    mSelectedRegion = mRegionAdapter.getItem(position).areaId;
                    mProvinceAdapter.clear();
                    mProvinceAdapter.addAll(getProvinces(mSelectedRegion));
                }
                break;
            case R.id.spinner_province:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private static List<Area> getAreaList(int parentId) {
        Cursor c = mDb.queryByParentId(parentId);

        List<Area> result = new ArrayList<>();
        Area area;
        while (c.moveToNext()) {
            area = new Area(c.getInt(0), c.getString(1), c.getInt(2));
            result.add(area);
        }
        return result;
    }


    public static List<Area> getRegionList(int parentId) {
        return getAreaList(parentId);
    }

    public static List<Area> getProvinces(int parentId) {
        return getAreaList(parentId);
    }

    public static class Area {
        public int areaId;
        public String areaName;
        public int parentId;

        public Area(int areaId, String areaName, int parentId) {
            this.areaId = areaId;
            this.areaName = areaName;
            this.parentId = parentId;
        }

        @Override
        public String toString() {
            return areaName;
        }
    }

    public static class Bank {
        public int id;
        public String name;

        public Bank(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
