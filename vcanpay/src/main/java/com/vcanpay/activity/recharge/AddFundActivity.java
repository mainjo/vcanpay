package com.vcanpay.activity.recharge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.NoticeDialogFragment;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.validator.NotEmptyValidator;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.RechargeBill;
import org.vcanpay.eo.VcanpayBankAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddFundActivity extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, TextWatcher, NoticeDialogFragment.NoticeDialogListener {

    public static final String TAG = "RechargeInputActivity";
    TextView mTvGetVcpAccount;
    Spinner mSpTransferType;
    EditText mEtTransactionNo;
    EditText mEtTransAmount;
    EditText mEtVcpAccount;
    EditText mEtDate;
    EditText mEtNote;

    Button mSubmit;

    ArrayAdapter<TransferType> mAdapter;


    int rechargeType;
    String transactionNo;
    BigDecimal transAmount;
    String vcpAccountCardNo;
    Date transDate;
    String note;


    public static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);

        init();

    }

    private void init() {
        mEtTransactionNo = (EditText) findViewById(R.id.transaction_ref_no);
        mEtTransAmount = (EditText) findViewById(R.id.trans_amount);
        mTvGetVcpAccount = (TextView) findViewById(R.id.get_vcp);
        mEtVcpAccount = (EditText) findViewById(R.id.vcp_account);
        mEtNote = (EditText) findViewById(R.id.memo);
        mEtDate = (EditText) findViewById(R.id.date);
        mSubmit = (Button) findViewById(R.id.submit);
        mSpTransferType = (Spinner) findViewById(R.id.transfer_type_spinner);


        mEtDate.addTextChangedListener(this);
        mEtVcpAccount.addTextChangedListener(this);
        mEtTransactionNo.addTextChangedListener(this);
        mEtTransAmount.addTextChangedListener(this);

        mEtDate.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

        String s = getString(R.string.get_vcp);
        SpannableString getVcpAccountLink = new SpannableString(s);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                int id = widget.getId();
                Context context = AddFundActivity.this;
                switch (id) {
                    case R.id.get_vcp:
                        Intent intent = new Intent(AddFundActivity.this, ChooseRegionActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                }
            }
        };

        getVcpAccountLink.setSpan(clickableSpan, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvGetVcpAccount.setText(getVcpAccountLink);
        mTvGetVcpAccount.setTextColor(Color.BLUE);
        mTvGetVcpAccount.setMovementMethod(MyLinkMovementMethod.getInstance());

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TransferTypeDummp.items);
        mSpTransferType.setAdapter(mAdapter);

        AreaContentProvider2 areaContentProvider2 = new AreaContentProvider2(this);
        Cursor c = areaContentProvider2.queryById(10);
        c.moveToNext();
        Log.i(TAG, c.getString(0));
        Log.i(TAG, c.getString(1));
        Log.i(TAG, c.getString(2));


//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Area.AREA_ID, 2000);
//        contentValues.put(Area.AREA_NAME, "hello");
//        contentValues.put(Area.PARENT_ID, "1");
//        areaContentProvider2.insert(contentValues);

//
//        int count = c.getCount();
//        Log.i("Test", count + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == ChooseBankAccountActivity.SUCCESS_CODE) {
                VcanpayBankAccount vcpAccount = data.getParcelableExtra(ChooseBankAccountActivity.VCP_ACCOUNT_KEY);
                mEtVcpAccount.setTag(vcpAccount);
                mEtVcpAccount.setText(vcpAccount.getBankCardNo());
                mEtVcpAccount.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_fund, menu);
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


    public String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'+'hh:mm:ss");
        return format.format(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        super.onDateSet(view, year, monthOfYear, dayOfMonth);

        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);

        setTempCalendar(c);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CALENDAR_KEY, c);

        dismissDialog(DATE_DIALOG_ID);
        showDialog(TIME_DIALOG_ID, bundle);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        super.onTimeSet(view, hourOfDay, minute);
        Calendar c = getTempCalendar();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        mEtDate.setText(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm").format(c.getTime()));
        mEtDate.setTag(c.getTime());
        dismissDialog(TIME_DIALOG_ID);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.date) {
            showDialog(DATE_DIALOG_ID, null);
        }

        if (id == R.id.get_vcp) {
            Intent intent = new Intent(this, ChooseRegionActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return;
        }

        if (id == R.id.submit) {
            makeSubmitRequest();
        }
    }

    private void makeSubmitRequest() {
        showProgressDialog(this);

        rechargeType = ((TransferType) mSpTransferType.getSelectedItem()).id;
        transactionNo = mEtTransactionNo.getText().toString();
        transAmount = BigDecimal.valueOf(Float.valueOf(mEtTransAmount.getText().toString()));
        vcpAccountCardNo = mEtVcpAccount.getText().toString();
        transDate = (Date) mEtDate.getTag();
        note = mEtNote.getText().toString().trim();


        RechargeBill bill = new RechargeBill();
        bill.setBankCardNo(vcpAccountCardNo);
        bill.setNote(note);
        bill.setRemittanceNo(transactionNo);
        bill.setRechargeType(rechargeType);
        bill.setOperateTime(transDate);
        bill.setAmount(transAmount);

        CustomInfo customInfo = getCurrentCustomer();
        bill.setCustomInfo(getCurrentCustomer());

        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setDateFormat("yyyy-MM-dd'+'HH:mm:ss")
                .create();

        String endPoint = "MgrAddFundTransDAO/selectedRow" + "?Date=" +
                new SimpleDateFormat("yyyy-MM-dd'+'HH:mm:ss").format((Date) mEtDate.getTag());
//        String json1 = gson.toJson(bill);


        String json1 = String.format(
                "{\"custRechargeId\":0," +
                        "\"amount\":%s," +
                        "\"bankCardNo\":\"%s\"," +
                        "\"note\":\"%s\"," +
                        "\"rechargeType\":\"%s\"," +
                        "\"remittanceNo\":\"%s\"," +
                        "\"customInfo\":" +
                        "{\"customId\":%d," +
                        "\"customName\":\"%s\"," +
                        "\"customScore\":0," +
                        "\"loginErrTimes\":0}}",
                new DecimalFormat("0.0").format(bill.getAmount().doubleValue()),
                bill.getBankCardNo(),
                bill.getNote(),
                bill.getRechargeType(),
                bill.getRemittanceNo(),
                customInfo.getCustomId(),
                customInfo.getCustomName()
        );


        String json2 = "{rechargeBill: " + json1 + "}";
        AddFundRequest request = new AddFundRequest(
                this,
                endPoint,
                json2,
                json1,
                new Response.Listener<AddFundResponse>() {
                    @Override
                    public void onResponse(AddFundResponse response) {
                        closeProgressDialog();
                        getCurrentCustomer().setCustomAccounts(response.getCustomerAccount());

                        NoticeDialogFragment dialog = NoticeDialogFragment.getInstance(0, R.string.recharge_submit_success, 0, 0);
                        dialog.setNoticeDialogListener(AddFundActivity.this);
                        dialog.show(getSupportFragmentManager(), "recharge");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        closeProgressDialog();

                        if (error instanceof AuthFailureError) {
                            showAlertDialog(AddFundActivity.this, getString(R.string.notify), error.getMessage());
                            finish();
                            return;
                        }

                        if (error != null) {
                            showAlertDialog(AddFundActivity.this, getString(R.string.notify), error.getMessage());
                        }
                    }
                }
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(this);
        queue.addToRequestQueue(request);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (validate(mEtTransactionNo, new NotEmptyValidator()) &&
                validate(mEtTransAmount, new NotEmptyValidator()) &&
                validate(mEtVcpAccount, new NotEmptyValidator()) &&
                validate(mEtDate, new NotEmptyValidator())) {

            mSubmit.setEnabled(true);
        } else {
            mSubmit.setEnabled(false);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, TabhostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public static class TransferType {
        int id;
        String text;

        public TransferType(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class TransferTypeDummp {
        public static TransferType[] items = new TransferType[2];

        static {
            items[0] = new TransferType(0, "Over the counter/ATM");
            items[1] = new TransferType(1, "Online bank");
        }
    }

    public class MyLinkMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            Intent intent = new Intent(AddFundActivity.this, ChooseRegionActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
