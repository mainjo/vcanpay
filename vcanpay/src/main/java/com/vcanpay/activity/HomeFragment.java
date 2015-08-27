package com.vcanpay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.vcanpay.MyApplication;
import com.vcanpay.activity.bill.AppRequestQueue;
import com.vcanpay.activity.dummy.DummyContent;
import com.vcanpay.request.SignInRequest;
import com.vcanpay.response.SignInResponse;
import com.vcanpay.view.CustomProgressBarDialog;
import com.vcanpay.view.GridViewItemLayout;

import org.vcanpay.eo.CustomInfo;
import org.vcanpay.eo.Login;

import java.text.NumberFormat;
import java.util.List;

import static com.example.vcanpay.R.id.text1;

public class HomeFragment extends BaseFragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;

    TextView mTvTotalAmount;
    TextView mTvBalance;
    TextView mTvFrozenAmount;
    TextView mWelcome;
    TextView mLocation;

    Toolbar mToolbar;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                setupGridView(getView());
            }
        });

        mTvTotalAmount = (TextView) view.findViewById(R.id.totalAmount);
        mTvBalance = (TextView) view.findViewById(R.id.balance);
        mTvFrozenAmount = (TextView) view.findViewById(R.id.frozenAmount);
        mWelcome = (TextView) view.findViewById(R.id.welcome);
        mLocation = (TextView) view.findViewById(R.id.location);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((TabhostActivity)getActivity()).setSupportActionBar(mToolbar);

        ((TabhostActivity)getActivity()).getWindow().setBackgroundDrawableResource(R.drawable.background);

        return view;
    }

    private void setupGridView(View view) {
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mAdapter = new CustomAdapter(getActivity(), R.layout.grid_item, text1, DummyContent.ITEMS);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        if (mListView instanceof GridView) {
            ((GridView) mListView).setNumColumns(3);
            ((GridView) mListView).setGravity(Gravity.CENTER);
        }
    }

    private int calculateSpanCount() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.home_grid_size);
        int avatarPadding = getResources().getDimensionPixelSize(R.dimen.spacing_double);
        return mListView.getWidth() / (avatarSize + avatarPadding);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.home));

        NumberFormat format = NumberFormat.getCurrencyInstance();
        CustomInfo currentCustomer = ((TabhostActivity) getActivity()).getCurrentCustomer();
        mTvTotalAmount.setText(format.format(currentCustomer.getCustomAccounts().getAmount()));
        mTvBalance.setText(format.format(currentCustomer.getCustomAccounts().getCashAmount()));
        mTvFrozenAmount.setText(format.format(currentCustomer.getCustomAccounts().getUncashAmount()));

        mWelcome.setText(getString(R.string.welcome_prefix) + currentCustomer.getLastName());
        mLocation.setText(getCountry(getActivity(), Integer.valueOf(currentCustomer.getCountry())) + ", " + currentCustomer.getCity());

    }

    public String getCountry(Context context, int code) {
        switch (code) {
            case 0:
                return context.getString(R.string.thailand);
            case 1:
                return context.getString(R.string.china);
        }

        return context.getString(R.string.other);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_func_item_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        CustomInfo currentCustomer = ((TabhostActivity) getActivity()).getCurrentCustomer();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BaseActivity.CUSTOMER_INFO, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(BaseActivity.CUSTOMER_EMAIL, "");
        String password = sharedPreferences.getString(BaseActivity.CUSTOMER_PASSWORD, "");

        Login login = new Login(email, password);
        Gson gson = new Gson();
        String json1 = gson.toJson(login);

        Log.i("TEST", "begin login");
        String json2 = "{\"login\":" + json1 + "}";
        SignInRequest request = new SignInRequest(
                json2,
                json1,
                new Response.Listener<SignInResponse>() {
                    @Override
                    public void onResponse(SignInResponse response) {
                        closeProgressDialog();
                        CustomInfo customInfo = response.getCustomInfo();
                        ((MyApplication)getActivity().getApplication()).setCustomInfo(customInfo);
                        NumberFormat format = NumberFormat.getCurrencyInstance();
                        mTvTotalAmount.setText(format.format(customInfo.getCustomAccounts().getAmount()));
                        mTvBalance.setText(format.format(customInfo.getCustomAccounts().getCashAmount()));
                        mTvFrozenAmount.setText(format.format(customInfo.getCustomAccounts().getUncashAmount()));
                    }
                },
                new VolleyErrorListener((BaseActivity) getActivity()));

        AppRequestQueue queue = AppRequestQueue.getInstance(getActivity());
        queue.addToRequestQueue(request);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }

        Class<? extends Activity> clazz = ((DummyContent.DummyItem) mAdapter.getItem(position)).clazz;
        if (clazz != null) {
            startActivity(getActivity(), clazz);
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public static class CustomAdapter extends ArrayAdapter<DummyContent.DummyItem> {

        private static final int ROW_NUMBER = 3;
        LayoutInflater mInflater;
        Context mContext;
        int mResource;
        List<DummyContent.DummyItem> mItems;

        public CustomAdapter(Context context, int resource, int textViewResourceId, List<DummyContent.DummyItem> objects) {
            super(context, resource, textViewResourceId, objects);
            mContext = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mResource = resource;
            mItems = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            if (convertView == null) {
                view = mInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }

//            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView = (TextView) view.findViewById(R.id.text1);

            DummyContent.DummyItem item = getItem(position);

//            imageView.setImageResource(item.drawable);
            textView.setText(item.title);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, item.drawable, 0, 0);
            return view;
        }


        public void measureItems(int columnWidth) {
            // Obtain system inflater
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Inflate temp layout object for measuring
            GridViewItemLayout itemView = (GridViewItemLayout) inflater.inflate(R.layout.grid_item, null);

            // Create measuring specs
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(columnWidth, View.MeasureSpec.EXACTLY);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

            // Loop through each data object
            for (int index = 0; index < mItems.size(); index++) {
                DummyContent.DummyItem item = mItems.get(index);

                // Set position and data
                itemView.setPosition(index);
//                itemView.updateItemDisplay(item, mLanguage);

                // Force measuring
                itemView.requestLayout();
                itemView.measure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private CustomProgressBarDialog mDialog;

    public void showProgressDialog(Context context) {
        mDialog = CustomProgressBarDialog.createDialog(context);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
