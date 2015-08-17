package com.vcanpay.bankcard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.BaseActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.utils.NavUtils;
import com.vcanpay.request.GetBankCardRequest;

import org.vcanpay.eo.CustBankCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageBankCardActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    ListView mBankCards;

    ArrayAdapter<CustBankCard> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bank_card);

        mBankCards = (ListView) findViewById(R.id.bank_card_list);
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

                        ArrayList<CustBankCard> bankCards = new ArrayList<>(Arrays.asList(response));
                        
                        if (response != null && response.length > 0) {
                            mAdapter = new MyAdapter(ManageBankCardActivity.this,
                                    R.layout.list_item_my_bank_card,
                                    bankCards);
                            mBankCards.setAdapter(mAdapter);
                            mBankCards.setOnItemClickListener(ManageBankCardActivity.this);

                            setEmptyText("");

                            return;
                        }
                        setEmptyText(R.string.no_data);
                    }
                },
                new VolleyErrorListener(this)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_bank_card, menu);
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

        if (id == R.id.action_add_bank_card) {
            startActivity(this, AddBankCardActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustBankCard bankCard = mAdapter.getItem(position);
        NavUtils.viewBankCardDetails(this, bankCard);
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return false;
    }

    private void showPopupMenu(View view) {
//        final PopupAdapter adapter = (PopupAdapter) getListAdapter();

        // Retrieve the clicked item from view's tag
        final CustBankCard item = (CustBankCard) view.getTag();

        // Create a PopupMenu, giving it the clicked view for an anchor
        PopupMenu popup = new PopupMenu(this, view);

        // Inflate our menu resource into the PopupMenu's Menu
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        // Set a listener so we are notified if a menu item is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_remove:
                        // Remove the item from the adapter
                        mAdapter.remove(item);
                        return true;
                    case R.id.action_activate:
                        NavUtils.activateBankCard(ManageBankCardActivity.this, item);
                        return true;
                }
                return false;
            }
        });

        // Finally show the PopupMenu
        popup.show();
    }

    @Override
    public void onClick(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(view);
            }
        });
    }

    public class MyAdapter extends ArrayAdapter<CustBankCard> {

        LayoutInflater mInflater;
        int mResource;
        Context mContext;

        public MyAdapter(Context context, int resource, CustBankCard[] objects) {
            super(context, resource, objects);
            mResource = resource;
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            mContext = context;
        }

        public MyAdapter(Context context, int resource, List<CustBankCard> bankCards) {
            super(context, resource, bankCards);
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


            ImageView ivBankLogo = (ImageView) view.findViewById(R.id.bank_logo);


            TextView tvBankName = (TextView) view.findViewById(R.id.bank_name);
            TextView tvBankCardSuffix = (TextView) view.findViewById(R.id.card_num);
            TextView tvBankCardType = (TextView) view.findViewById(R.id.card_type);
//            TextView tvMobileChecked = (TextView) view.findViewById(R.id.mobile);
//            TextView tvMoneyCheckedStatus = (TextView) view.findViewById(R.id.money);

            CustBankCard card = getItem(position);

            View actionIndicator = view.findViewById(R.id.action_indicator);
            actionIndicator.setTag(card);
            actionIndicator.setOnClickListener(ManageBankCardActivity.this);


            String moneyChecked = card.getHaveMoneyCheck();

            if (!TextUtils.isEmpty(moneyChecked)) {
                if (card.getHaveMoneyCheck().equals("5")) {

                    ivBankLogo.setImageResource(R.drawable.bank_01);
                }
//                tvMoneyCheckedStatus.setText(getMoneyCheckStatus(Integer.valueOf(card.getHaveMoneyCheck())));

            } else {
                ivBankLogo.setImageResource(R.drawable.bank_01);
//                tvMoneyCheckedStatus.setText(R.string.bank_card_unverified);
            }


            tvBankName.setText(getBankName(Integer.valueOf(card.getBankName())));
            String cardNo = card.getBankCardNo();
//            tvBankCardSuffix.setText(cardNo.substring(cardNo.length() - 5, cardNo.length() - 1));
            tvBankCardSuffix.setText(cardNo);
            tvBankCardType.setText(getAccountType(Integer.valueOf(card.getAccountType())));
//            tvMobileChecked.setText(card.getHaveMobileCheck().equals("1")? R.string.activated : R.string.unactivated);

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
