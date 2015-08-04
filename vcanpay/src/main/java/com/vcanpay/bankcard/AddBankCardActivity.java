package com.vcanpay.bankcard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.NoticeDialogFragment;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.recharge.AreaContentProvider2;
import com.vcanpay.activity.recharge.ChooseRegionActivity;

import org.vcanpay.eo.CustBankCard;
import org.vcanpay.eo.CustomInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class AddBankCardActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, NoticeDialogFragment.NoticeDialogListener, TextWatcher {

    static final int DATE_DIALOG_ID = 1;


    EditText mEtCardNum;
    EditText mEtAccountName;
    EditText mEtBankBranch;
    EditText mEtCellPhone;
    EditText mEtBirthday;

    Spinner mSpinnerCountry;
    Spinner mSpinnerRegion;
    Spinner mSpinnerProvince;
    Spinner mSpinnerBank;
    Spinner mSpinnerSex;
    Spinner mSpinnerCardType;

    ArrayAdapter countryAdapter;
    SpinnerAdapter regionAdapter;
    SpinnerAdapter provinceAdapter;
    SpinnerAdapter bankAdapter;
    SpinnerAdapter sexAdapter;

    static AreaContentProvider2 mDb;
    static List<ChooseRegionActivity.Area> regions;
    static List<ChooseRegionActivity.Area> provinces;

    Button mButton;

    CustBankCard bankCard;

    public static final String BANK_CARD_KEY = "bank_card_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);

        if (mDb == null) {
            mDb = new AreaContentProvider2(this);
        }

        mEtCardNum = (EditText) findViewById(R.id.card_num);
        mEtAccountName = (EditText) findViewById(R.id.account_name);
        mEtBankBranch = (EditText) findViewById(R.id.bank_branch);
        mEtCellPhone = (EditText) findViewById(R.id.cell_phone);
        mEtBirthday = (EditText) findViewById(R.id.birthday);

        mSpinnerCountry = (Spinner) findViewById(R.id.spinner_country);
        mSpinnerRegion = (Spinner) findViewById(R.id.spinner_region);
        mSpinnerProvince = (Spinner) findViewById(R.id.spinner_province);
        mSpinnerBank = (Spinner) findViewById(R.id.spinner_bank);
        mSpinnerSex = (Spinner) findViewById(R.id.spinner_sex);

        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{getString(R.string.thailand)});
        regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getRegionList(0, mDb));
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getAreaList(1, mDb));
        bankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.bank_names));
        sexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sex));

        mSpinnerCountry.setAdapter(countryAdapter);
        mSpinnerProvince.setAdapter(provinceAdapter);
        mSpinnerRegion.setAdapter(regionAdapter);
        mSpinnerBank.setAdapter(bankAdapter);
        mSpinnerSex.setAdapter(sexAdapter);

        mSpinnerCountry.setOnItemSelectedListener(this);
        mSpinnerRegion.setOnItemSelectedListener(this);
        mSpinnerProvince.setOnItemSelectedListener(this);

        mSpinnerCardType = (Spinner) findViewById(R.id.card_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.account_type));
        mSpinnerCardType.setAdapter(adapter);


        mButton = (Button) findViewById(R.id.submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankCard = new CustBankCard();
                bankCard.setAccountType(String.valueOf(mSpinnerCardType.getSelectedItemPosition() + 1));
                bankCard.setBankArea(((ChooseRegionActivity.Area) mSpinnerRegion.getSelectedItem()).areaId + "");
                bankCard.setBankCardNo(mEtCardNum.getText().toString());
                bankCard.setBankName(String.valueOf(mSpinnerBank.getSelectedItemPosition()));
                bankCard.setBankProvince(((ChooseRegionActivity.Area) mSpinnerProvince.getSelectedItem()).areaId + "");
                bankCard.setBranchName(mEtBankBranch.getText().toString());
                bankCard.setHaveMobileCheck("1");
                bankCard.setMobilePhone(mEtCellPhone.getText().toString());
                bankCard.setUserName(mEtAccountName.getText().toString());
                bankCard.setUserSex((String.valueOf(mSpinnerSex.getSelectedItemPosition())));
                bankCard.setCustomInfo(getCurrentCustomer());
                bankCard.setUserBirth((Date)mEtBirthday.getTag());

                makeRequest(bankCard);
            }
        });

//        mEtBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    showDialog(DATE_DIALOG_ID);
//                }
//            }
//        });

        mEtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                Calendar calendar = Calendar.getInstance();
                return new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, datePickerListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }


    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);

            mEtBirthday.setText(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
            mEtBirthday.setTag(c.getTime());
        }
    };

    private void makeRequest(CustBankCard custBankCard) {
        showProgressDialog(this);

//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'+'HH:mm:ss")
//                .create();

//        String json1 = gson.toJson(custBankCard, CustBankCard.class);

        CustomInfo customer = getCurrentCustomer();

        String json1 = String.format("{" +
                        "\"customBankId\":0," +
                        "\"accountType\":\"0\"," +
                        "\"bankArea\":\"%s\"," +
                        "\"bankCardNo\":\"%s\"," +
                        "\"bankName\":\"%s\"," +
                        "\"bankProvince\":\"%s\"," +
                        "\"branchName\":\"%s\"," +
                        "\"haveMobileCheck\":\"1\"," +
                        "\"mobilePhone\":\"%s\"," +
                        "\"userName\":\"%s\"," +
                        "\"userSex\":\"%s\"," +
                        "\"remitPeopleId\":0," +
                        "\"checkErrTimes\":0," +
                        "\"customInfo\":" +
                        "{" +
                        "\"customId\":%d," +
                        "\"customScore\":0," +
                        "\"email\":\"%s\"," +
                        "\"loginErrTimes\":0}}",
                custBankCard.getBankArea(),
                custBankCard.getBankCardNo(),
                custBankCard.getBankName(),
                custBankCard.getBankProvince(),
                custBankCard.getBranchName(),
                custBankCard.getMobilePhone(),
                custBankCard.getUserName(),
                custBankCard.getUserSex(),
                customer.getCustomId(),
                customer.getEmail()
        );

        String json2 = "{custBankCard:" + json1 + "}";

        String birthday = new SimpleDateFormat("yyyy-MM-dd'+'HH:mm:ss").format(custBankCard.getUserBirth());

        BindBankCardRequest request = new BindBankCardRequest(
                this,
                birthday,
                json2,
                json1,
                new Response.Listener<BindBankCardResponse>() {
                    @Override
                    public void onResponse(BindBankCardResponse response) {
                        closeProgressDialog();
                        NoticeDialogFragment dialog;

                        if (response.getStatusCode() == 203) {
                            dialog = NoticeDialogFragment.getInstance(0, response.getMessage(), R.string.now_to_activate, 0);
                        } else {

                            dialog = NoticeDialogFragment.getInstance(0, R.string.add_bank_card_success, R.string.now_to_activate, 0);
                        }
                        dialog.setNoticeDialogListener(AddBankCardActivity.this);
                        dialog.show(getSupportFragmentManager(), "add_bank_card");
                    }
                },
                new VolleyErrorListener(this)
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);

    }

    public static List<ChooseRegionActivity.Area> getAreaList(int parentId, AreaContentProvider2 db) {
        Cursor c = db.queryByParentId(parentId);

        List<ChooseRegionActivity.Area> result = new ArrayList<>();
        ChooseRegionActivity.Area area;
        while (c.moveToNext()) {
            area = new ChooseRegionActivity.Area(c.getInt(0), c.getString(1), c.getInt(2));
            result.add(area);
        }

        return result;
    }


    public static List<ChooseRegionActivity.Area> getRegionList(int parentId, AreaContentProvider2 db) {
        if (regions == null) {
            regions = getAreaList(parentId, db);
        }

        return regions;
    }

    public static List<ChooseRegionActivity.Area> getProvinces(int parentId, AreaContentProvider2 db) {
        if (provinces == null) {
            provinces = getAreaList(parentId, db);
        }

        return provinces;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, ActivateBankCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BANK_CARD_KEY, bankCard);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static class Sex {
        public String id;
        public String text;

        public Sex(String id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class CardType {
        int id;
        String text;

        public CardType(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class SexDummy {
        public static final Sex[] ITEMS = new Sex[3];

        static {
            ITEMS[0] = new Sex("0", "\u7537");
            ITEMS[1] = new Sex("1", "\u5973");
            ITEMS[2] = new Sex("2", "\u5176\u4ed6");
        }
    }

    public static class CardTypeDummy {
        public static final CardType[] ITEMS = new CardType[3];

        static {
            ITEMS[0] = new CardType(1, "\u94f6\u884c\u5361");
            ITEMS[1] = new CardType(2, "\u4fe1\u7528\u5361");
            ITEMS[2] = new CardType(3, "\u5176\u4ed6");
        }
    }
}
