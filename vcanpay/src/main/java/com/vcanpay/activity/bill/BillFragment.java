package com.vcanpay.activity.bill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.vcanpay.R;
import com.vcanpay.activity.OnFragmentInteractionListener;

import org.vcanpay.eo.CustomTrade;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.Response.ErrorListener;
import static com.android.volley.Response.Listener;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillFragment extends Fragment implements ActionBar.OnNavigationListener {

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BillFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.all_bills),
                                getString(R.string.recharge_bills),
                                getString(R.string.payment_bills)
                        }),
                this
        );
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

        /**
         * The fragment's ListView/GridView.
         */
        private AbsListView mListView;

        /**
         * The Adapter which will be used to populate the ListView/GridView with
         * Views.
         */
        private ListAdapter mAdapter;

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


            // fetch json data

            String url = "http://123.1.189.38:8080/vcanpay123/ws/MgrCustomTradeStatic/queryData?_type=json";

            BillRequest request = new BillRequest(
                    url,
                    new Listener<CustomTrade[]>() {
                        @Override
                        public void onResponse(CustomTrade[] response) {
                            // TODO: Change Adapter to display your content

                            if (response == null || response.length == 0) {
                                setEmptyText("There are no records.");
                                return;
                            }

                            mAdapter = new MyAdapter(getActivity(), R.layout.list_item_trade, response);
                            // Set the adapter
                            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

                            // Set OnItemClickListener so we can be notified on item clicks
                            mListView.setOnItemClickListener(PlaceholderFragment.this);
                        }
                    },
                    new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("BillFragment", error.toString());
                        }
                    }
            );

            AppRequestQueue queue = AppRequestQueue.getInstance(getActivity());
            queue.addToRequestQueue(request);

            return view;
        }

        /**
         * The default content for this Fragment has a TextView that is shown when
         * the list is empty. If you would like to change the text, call this method
         * to supply the text it should use.
         */
        public void setEmptyText(CharSequence emptyText) {
            View emptyView = mListView.getEmptyView();

            if (emptyView instanceof TextView) {
                ((TextView) emptyView).setText(emptyText);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showBillDetails();
        }

        void showBillDetails() {
            Intent intent = new Intent(getActivity(), BillDetailsActivity.class);
            startActivity(intent);
        }

        public class MyAdapter extends ArrayAdapter<CustomTrade> {
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
                TextView tvDate = (TextView) view.findViewById(R.id.date);
                TextView tvAmount = (TextView) view.findViewById(R.id.amount);
                TextView tvType = (TextView) view.findViewById(R.id.tradeType);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                tvDate.setText(format.format(new Date()));
                tvAmount.setText(customTrade.getTradeMoney().toString());
                setText(tvType, String.valueOf(customTrade.getTradeType()), "");

                return view;
            }

            void setText(TextView textView, String value, String defaultValue) {
                if (value != null && !value.equals("")) {
                    textView.setText(value);
                    return;
                }
                textView.setText(defaultValue);
            }
        }
    }
}
