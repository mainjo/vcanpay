package com.vcanpay.activity.settings;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.activity.SettingsActivity;
import com.vcanpay.activity.help.FeedbackActivity;
import com.vcanpay.activity.help.HelpActivity;
import com.vcanpay.activity.profile.UpdateAddressActivity;
import com.vcanpay.activity.profile.UpdateMobileActivity;
import com.vcanpay.activity.profile.UpdateUserProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button mActionSignOut;

    ListView mList;

    ListAdapter mAdapter;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String[] items = new String[]{"\u4e2a\u4eba\u8d44\u6599", "\u8d26\u6237\u5b89\u5168", "\u8bbe\u7f6e", "\u5e2e\u52a9", "\u53cd\u9988"};
//        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, R.id.text1, PROFILE_ITEMS);
        mAdapter = new MyAdapter(getActivity(), R.layout.list_item, PROFILE_ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_profile, container, false);




        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // button
        View v = getLayoutInflater(null).inflate(R.layout.sign_out_button, getListView(), false);

        Button signOutButton = (Button) v.findViewById(R.id.button);
        signOutButton.setOnClickListener(this);
        getListView().addFooterView(v);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    public static final String FRAGMENT_TAG = "settings_fragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.me);
    }

    @Override
    public void onClick(View v) {
        signOut();
    }

    private void signOut() {
        getActivity().finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DummyItem item = (DummyItem) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), item.clazz);
        startActivity(intent);
    }

    public static List<DummyItem> SECURITY_ITEMS = new ArrayList<DummyItem>();
    public static List<DummyItem> PROFILE_ITEMS = new ArrayList<>();

    static {
        PROFILE_ITEMS.add(new DummyItem("1", R.string.title_activity_user_profile, UpdateUserProfileActivity.class));
        PROFILE_ITEMS.add(new DummyItem("2", R.string.title_activity_update_address_request, UpdateAddressActivity.class));
        PROFILE_ITEMS.add(new DummyItem("3", R.string.title_activity_update_mobile_request, UpdateMobileActivity.class));
        PROFILE_ITEMS.add(new DummyItem("4", R.string.title_activity_security_management, SecurityManagementActivity.class));
        PROFILE_ITEMS.add(new DummyItem("5", R.string.title_activity_settings, SettingsActivity.class));
//        PROFILE_ITEMS.add(new DummyItem("4", R.string.title_activity_help, HelpActivity.class));
        PROFILE_ITEMS.add(new DummyItem("4", R.string.title_activity_help, HelpActivity.class));
        PROFILE_ITEMS.add(new DummyItem("3", R.string.title_activity_feed_back, FeedbackActivity.class));
    }

//    class DummyContent {
//
//
//        static {
//            SECURITY_ITEMS.add(new DummyItem("1", R.string.title_activity_change_password, ChangePasswordActivity.class));
//            SECURITY_ITEMS.add(new DummyItem("2", R.string.title_activity_change_payment_password, ChangePaymentPasswordActivity.class));
//            SECURITY_ITEMS.add(new DummyItem("3", R.string.title_activity_find_payment_password, FindPaymentPasswordActivity.class));
//        }
//
//
//
//
//
//    }

    public static class DummyItem {
        public String id;
        public int title;
        public Class<? extends Activity> clazz;

        public DummyItem(String id, int title, Class<? extends Activity> clazz) {
            this.id = id;
            this.title = title;
            this.clazz = clazz;
        }
    }

    private class MyAdapter extends ArrayAdapter<DummyItem> {
        LayoutInflater mInflater;
        int mResource;

        public MyAdapter(Context context, int resource, List<DummyItem> objects) {
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

            DummyItem item = getItem(position);
            TextView tv = (TextView) view.findViewById(R.id.text1);
            tv.setText(item.title);

            return view;
        }
    }
}
