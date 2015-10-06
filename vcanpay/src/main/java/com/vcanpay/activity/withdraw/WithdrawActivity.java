package com.vcanpay.activity.withdraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.recharge.ChooseRegionActivity;
import com.vcanpay.bankcard.AddBankCardActivity;
import com.vcanpay.request.GetBankCardRequest;

import org.vcanpay.eo.CustBankCard;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class WithdrawActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        TextWatcher, AdapterView.OnItemClickListener, View.OnClickListener{

    public static final String BANK_ID = "bank_id";
    public static final String BANK_NAME = "bank_name";
    public static final String AMOUNT = "amount";
    public static final String BANK_CARD_KEY = "bank_card_key";
//    EditText mEtAmount;
    TextView mTvListHeader;
    ListView mLvBankCards;
    Button mBtnSubmit;
    ArrayAdapter<CustBankCard> mAdapter;
    // hidden
    Button mBtnAddBankCard;
    CustBankCard currentSelectedBankCard;

    private void startConfirm(Double amount, ChooseRegionActivity.Bank bank) {
        Intent intent = new Intent(this, WithdrawConfirmActivity.class);
        intent.putExtra(AMOUNT, amount)
                .putExtra(BANK_ID, bank.id)
                .putExtra(BANK_NAME, bank.name);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        init();
    }

    private void init() {

//        mEtAmount = (EditText) findViewById(R.id.amount);
        mBtnAddBankCard = (Button) findViewById(R.id.action_add_bank_card);
        mBtnSubmit = (Button) findViewById(R.id.submit);
        mTvListHeader = (TextView) findViewById(R.id.list_head);


        mLvBankCards = (ListView) findViewById(R.id.bank_cards);
        mLvBankCards.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mLvBankCards.setOnItemClickListener(this);


//        mEtAmount.addTextChangedListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeRequest();
    }

    private void makeRequest() {
        showProgressDialog(this);
        String query = String.valueOf(getCurrentCustomer().getCustomId());
        GetBankCardRequest request = new GetBankCardRequest(
                this,
                query,
                query,
                new Response.Listener<CustBankCard[]>() {
                    @Override
                    public void onResponse(CustBankCard[] response) {
                        closeProgressDialog();
                        if (response != null && response.length != 0) {
                            mAdapter = new MyAdapter(WithdrawActivity.this,
                                    R.layout.list_item_bank_card,
                                    response);
                            mLvBankCards.setAdapter(mAdapter);
                            return;
                        }

                        mTvListHeader.setVisibility(View.GONE);
                        mBtnSubmit.setVisibility(View.GONE);
                        mBtnAddBankCard.setVisibility(View.VISIBLE);
                        mBtnAddBankCard.setOnClickListener(WithdrawActivity.this);
                        setEmptyText(R.string.no_bank_card_hint);
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

    public void setEmptyText(int stringResource) {
        setEmptyText(getString(stringResource));
    }

    public void setEmptyText(CharSequence charSequence) {
        TextView tv = (TextView) findViewById(android.R.id.empty);
        tv.setText(charSequence);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mBtnSubmit.setEnabled(true);

//        int bankId = mAdapter.getItem(position).getCustomBankId();
//        String bankName = mAdapter.getItem(position).getBankName();
//
//        Intent intent = new Intent(this, InputAmountActivity.class);
//        intent.putExtra(BANK_ID, bankId);
//        intent.putExtra(BANK_NAME, bankName);
//        startActivity(intent);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mBtnSubmit.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (validate(mEtAmount, new DoubleValidator())) {
//            mBtnSubmit.setEnabled(true);
//        } else {
//            mBtnSubmit.setEnabled(false);
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mBtnSubmit.setEnabled(true);
        currentSelectedBankCard = mAdapter.getItem(position);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.action_add_bank_card) {
            Intent intent = new Intent(this, AddBankCardActivity.class);
            startActivity(intent);
            return;
        }

        if (id == R.id.submit) {
            if (currentSelectedBankCard == null) {
                throw new IllegalStateException();
            }

            Intent intent = new Intent(this, InputAmountActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BANK_CARD_KEY, currentSelectedBankCard);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    public static class MyAdapter extends ArrayAdapter<CustBankCard> {

        LayoutInflater mInflater;
        int mResource;
        Context mContext;

        public MyAdapter(Context context, int resource, CustBankCard[] objects) {
            super(context, resource, objects);
            mResource = resource;
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            if (null != convertView) {
                view = convertView;
            } else {
                view = mInflater.inflate(mResource, parent, false);
            }

            TextView tvBankName = (TextView) view.findViewById(R.id.bank_name);
            TextView tvBankCardSuffix = (TextView) view.findViewById(R.id.card_num);
            TextView tvBankCardType = (TextView) view.findViewById(R.id.card_type);
            TextView tvMoneyCheckedStatus = (TextView) view.findViewById(R.id.status);

            CustBankCard card = getItem(position);

            if (!TextUtils.isEmpty(card.getHaveMoneyCheck()) && card.getHaveMoneyCheck().equals("5")) {
//                ivBankLogo.setImageResource(R.drawable.bank_01);
                tvMoneyCheckedStatus.setText(getMoneyCheckStatus(Integer.valueOf(card.getHaveMoneyCheck())));

            } else {
//                ivBankLogo.setImageResource(R.drawable.bank_01);
                tvMoneyCheckedStatus.setText(R.string.bank_card_unverified);
            }

            tvBankName.setText(getBankName(Integer.valueOf(card.getBankName())));
            String cardNo = card.getBankCardNo();
//            tvBankCardSuffix.setText(cardNo.substring(cardNo.length() - 5, cardNo.length() - 1));
            tvBankCardSuffix.setText(cardNo);
            tvBankCardType.setText(getAccountType(Integer.valueOf(card.getAccountType())));
            return view;
        }


        public String getAccountType(int code) {
            switch (code) {
                case 0:
                    return mContext.getString(R.string.credit_card);
                case 1:
                    return mContext.getString(R.string.debit_card);
                case 2:
                    return mContext.getString(R.string.bank_card_other);
            }
            return mContext.getString(R.string.bank_card_other);
        }


        public String getBankName(int code) {
            switch (code) {
                case 0:
                    return mContext.getString(R.string.bank0);
                case 1:
                    return mContext.getString(R.string.bank1);
                case 2:
                    return mContext.getString(R.string.bank2);
                case 3:
                    return mContext.getString(R.string.bank3);
            }

            return mContext.getString(R.string.unkown_bank);
        }

        public String getActivationStatus(int code) {
            switch (code) {
                case 0:
                    return mContext.getString(R.string.status_unactivated);
                case 1:
                    return mContext.getString(R.string.status_activated);
            }

            return mContext.getString(R.string.status_unactivated);
        }

        public String getMoneyCheckStatus(int code) {
            switch (code) {
                case 1:
                    return mContext.getString(R.string.money_check_staus_1);
                case 2:
                    return mContext.getString(R.string.money_check_staus_2);
                case 3:
                    return mContext.getString(R.string.money_check_staus_3);
                case 4:
                    return mContext.getString(R.string.money_check_staus_4);
                case 5:
                    return mContext.getString(R.string.money_check_staus_5);
            }

            return mContext.getString(R.string.unknown_status);
        }
    }
}
