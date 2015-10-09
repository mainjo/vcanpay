package com.vcanpay.activity.bill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.TabHostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.pay.BillsToBePaidActivity;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.CustomTrade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillFragment extends Fragment implements AbsListView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "BillFragment";

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static Handler mHandler = new Handler();

    private int mListCurrentPosition;

    private static String ARG_SECTION_NUMBER = "section_number";
    private int mCount;
    private List<CustomTrade> mCustomTrade;

    public BillFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.bill));
        makeRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void makeRequest() {
        ((TabHostActivity) getActivity()).showProgressDialog(getActivity());
        CustomInfo currentCustomer = ((TabHostActivity) getActivity()).getCurrentCustomer();
        BillRequest request = new BillRequest(
                currentCustomer.getCustomId(),
                new Response.Listener<CustomTrade[]>() {
                    @Override
                    public void onResponse(CustomTrade[] response) {
                        ((TabHostActivity) getActivity()).closeProgressDialog();

                        if (response != null && response.length > 0) {
                            mAdapter = new MyAdapter(getActivity(), R.layout.list_item_bill, response);
                            mListView.setAdapter(mAdapter);
                            mListView.setOnItemClickListener(BillFragment.this);

                            if (mListView instanceof ListView) {
                                ((ListView) mListView).setFooterDividersEnabled(true);
                                TextView footer = new TextView(getActivity());
                                footer.setText(R.string.load_more);
                                footer.setGravity(Gravity.CENTER);
                                footer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("TextView", "footer was clicked.");
                                    }
                                });
                                ((ListView) mListView).addFooterView(footer, null, false);
                            }

                            return;
                        }
                        setEmptyText(R.string.no_data);
                    }
                },
                new VolleyErrorListener(((TabHostActivity) getActivity()))
        );

        AppRequestQueue queue = AppRequestQueue.getInstance(getActivity());
        queue.addToRequestQueue(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showBillDetails(parent, view, position, id);
    }

    void showBillDetails(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BillDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BillsToBePaidActivity.BILL_KEY, (CustomTrade) mAdapter.getItem(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }

    public void setEmptyText(int string) {
        setEmptyText(getString(string));
    }

    public void setEmptyText(String text) {
        TextView textView = (TextView) getActivity().findViewById(android.R.id.empty);
        textView.setText(text);
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

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tvTitle.setText(getTradeType(getContext(), customTrade.getTradeType()));
            tvDate.setText(format.format(new Date()));
            tvAmount.setText(customTrade.getTradeMoney().toString());
            tvStatus.setText(getTradeState(getContext(), (int) customTrade.getTradeState()));

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
