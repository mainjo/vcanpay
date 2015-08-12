package com.vcanpay.activity.bill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.vcanpay.activity.OnFragmentInteractionListener;
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.activity.VolleyErrorListener;
import com.vcanpay.activity.pay.BillsToBePaidActivity;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.CustomTrade;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillFragment extends Fragment implements ActionBar.OnNavigationListener {

    public static final String TAG = "BillFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BillFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("DEMO");

        if (fragment == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.content, PlaceholderFragment.newInstance(1), "DEMO")
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.bill));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.content, PlaceholderFragment.newInstance(itemPosition))
                .commit();
        return true;
    }


    public static class PlaceholderFragment extends Fragment implements AbsListView.OnItemClickListener {


        private static String ARG_SECTION_NUMBER = "section_number";

        private AbsListView mListView;

        private ListAdapter mAdapter;

        private TextView mTvEmpty;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_demo, container, false);
            mListView = (AbsListView) view.findViewById(android.R.id.list);
            mTvEmpty = (TextView) view.findViewById(android.R.id.empty);
            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            makeRequest();
        }

        private void makeRequest() {
            ((TabhostActivity) getActivity()).showProgressDialog(getActivity());
            CustomInfo currentCutomer = ((TabhostActivity) getActivity()).getCurrentCustomer();
            BillRequest request = new BillRequest(
                    currentCutomer.getCustomId(),
                    new Response.Listener<CustomTrade[]>() {
                        @Override
                        public void onResponse(CustomTrade[] response) {
                            ((TabhostActivity) getActivity()).closeProgressDialog();
                            if (response != null && response.length > 0) {
                                mAdapter = new MyAdapter(getActivity(), R.layout.list_item_bill, response);
                                mListView.setAdapter(mAdapter);
                                mListView.setOnItemClickListener(PlaceholderFragment.this);
                                return;
                            }
                            setEmptyText(R.string.no_data);
                        }
                    },
                    new VolleyErrorListener(((TabhostActivity) getActivity()))
            );

            AppRequestQueue queue = AppRequestQueue.getInstance(getActivity());
            queue.addToRequestQueue(request);
        }

        public void setEmptyText(int string) {
            setEmptyText(getString(string));
        }

        public void setEmptyText(CharSequence emptyText) {

            mTvEmpty.setText(emptyText);
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
//                TextView tvType = (TextView) view.findViewById(R.id.tradeType);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tvTitle.setText(getTradeType(getContext(), customTrade.getTradeType()));
                tvDate.setText(format.format(new Date()));
                tvAmount.setText(customTrade.getTradeMoney().toString());
                tvStatus.setText(getTradeState(getContext(), (int)customTrade.getTradeState()));
//                tvType.setText(tvType, String.valueOf(customTrade.getTradeType()), "");

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
                        return context.getString(R.string.water_eletric);
                }

                return context.getString(R.string.unknow_fee);
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
                        return context.getString(R.string.water_and_electronice_fee);
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

                return context.getString(R.string.unknow_trade_state);
            }
        }


        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

//            outState.putParcelable();

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


        }


    }
}
