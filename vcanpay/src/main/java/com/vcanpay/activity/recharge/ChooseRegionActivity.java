package com.vcanpay.activity.recharge;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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
    static List<Area> regions;
    static List<Area> provinces;


    SpinnerAdapter countryAdapter;
    SpinnerAdapter regionAdapter;
    SpinnerAdapter provinceAdapter;
    SpinnerAdapter bankAdapter;

    int selectCountry;
    int selectRegion;
    int selectProvince;
    int selectBank;

    public static final String COUNTRY_CODE = "country";
    public static final String REGION_CODE = "region";
    public static final String PROVINCE_CODE = "province";
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

        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{getString(R.string.thailand)});
        regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getRegionList(0, mDb));
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getAreaList(1, mDb));
        bankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.bank_names));

        mSpinnerCountry.setAdapter(countryAdapter);
        mSpinnerProvince.setAdapter(provinceAdapter);
        mSpinnerRegion.setAdapter(regionAdapter);
        mSpinnerBank.setAdapter(bankAdapter);

        mSpinnerCountry.setOnItemSelectedListener(this);
        mSpinnerRegion.setOnItemSelectedListener(this);
        mSpinnerProvince.setOnItemSelectedListener(this);

        mBtnNextStep = (Button) findViewById(R.id.next_step);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_region, menu);
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

        Intent intent = new Intent(this, ChooseBankAccountActivity.class);

        intent.putExtra(COUNTRY_CODE, selectCountry);
        intent.putExtra(REGION_CODE, selectRegion);
        intent.putExtra(PROVINCE_CODE, selectProvince);
        intent.putExtra(BANK_CODE, selectBank);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            setResult(resultCode, data);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = view.getId();
//        if (viewId == R.id.spinner_country) {
//            selectCountry = 0;
//        }
//
//        if (viewId == R.id.spinner_region) {
//            Area area = (Area) (regionAdapter.getItem(position));
//            selectRegion = area.areaId;
//        }
//
//        if (viewId == R.id.spinner_province) {
//            Area area = (Area) (provinceAdapter.getItem(position));
//            selectProvince = area.areaId;
//        }
//
//        if (viewId == R.id.spinner_bank) {
//            Bank bank = (Bank) bankAdapter.getItem(position);
//            selectBank = bank.id;
//        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static List<Area> getAreaList(int parentId, AreaContentProvider2 db) {
        Cursor c = db.queryByParentId(parentId);

        List<Area> result = new ArrayList<>();
        Area area;
        while (c.moveToNext()) {
            area = new Area(c.getInt(0), c.getString(1), c.getInt(2));
            result.add(area);
        }

        return result;
    }


    public static List<Area> getRegionList(int parentId, AreaContentProvider2 db) {
        if (regions == null) {
            regions = getAreaList(parentId, db);
        }

        return regions;
    }

    public static List<Area> getProvinces(int parentId, AreaContentProvider2 db) {
        if (provinces == null) {
            provinces = getAreaList(parentId, db);
        }

        return provinces;
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

    public static class ContentDump {
        public static Bank[] banks = new Bank[4];

        static {
            banks[0] = new Bank(1, "\u4e2d\u56fd\u94f6\u884c");
            banks[1] = new Bank(2, "\u5de5\u5546\u94f6\u884c");
            banks[2] = new Bank(3, "\u519c\u4e1a\u94f6\u884c");
            banks[3] = new Bank(4, "\u5efa\u8bbe\u94f6\u884c");
        }
    }

    public static class BankDump {
        public static Bank[] banks = new Bank[4];

        static {
            banks[0] = new Bank(1, "\u4e2d\u56fd\u94f6\u884c");
            banks[1] = new Bank(2, "\u5de5\u5546\u94f6\u884c");
            banks[2] = new Bank(3, "\u519c\u4e1a\u94f6\u884c");
            banks[3] = new Bank(4, "\u5efa\u8bbe\u94f6\u884c");
        }

    }
}
