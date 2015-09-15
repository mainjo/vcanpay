package com.vcanpay.activity.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.activity.SettingsActivity;
import com.vcanpay.activity.security.ChangePasswordActivity;

public class ProfileFragment2 extends Fragment {
    LinearLayout mContainer;

    LayoutInflater mLayoutInflater;

    int mItemDividerHeight;
    int mCategoryDividerHeight;

    public static ProfileFragment2 newInstance() {
        return new ProfileFragment2();
    }

    public ProfileFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemDividerHeight = getResources().getDimensionPixelSize(R.dimen.profile_item_divider);
        mCategoryDividerHeight = getResources().getDimensionPixelSize(R.dimen.profile_category_divider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;

        View root = inflater.inflate(R.layout.fragment_profile_fragment2, container, false);
        mContainer = (LinearLayout) root.findViewById(R.id.item_container);

        Context context = getActivity();
        Intent intent = new Intent(context, UpdateUserProfileActivity.class);
        addProfileItem(context, R.string.title_activity_change_profile, R.drawable.icon_item_address, intent);

        intent = new Intent(context, UpdateAddressActivity.class);
        addProfileItem(context, R.string.title_activity_update_address_request, R.drawable.icon_item_address, intent);

        intent = new Intent(context, UpdateMobileActivity.class);
        addProfileItem(context, R.string.title_activity_update_mobile_request, R.drawable.icon_item_phone, intent);

        addCategoryDivider();
        intent = new Intent(context, ChangePasswordActivity.class);
        addProfileItem(context, R.string.title_activity_change_password, R.drawable.icon_item_security, intent);

        addCategoryDivider();


        // Settings
        intent = new Intent(getActivity(), SettingsActivity.class);
        addProfileItem(context, R.string.action_settings, R.drawable.icon_item_settings, intent);
        addCategoryDivider();

        // Sign out button

        addSignOutButton(inflater, container, savedInstanceState);

        return root;
    }

    private void addSignOutButton(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_out_button, container, false);
        mContainer.addView(view);

        Button button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    public View createProfileItem(Context context, int textRes, int drawableRes, final Intent intent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.profile_item, mContainer, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(textRes);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        return view;
    }

    public void addProfileItem(Context context, int text, int icon, Intent intent) {

        View view = createProfileItem(context, text, icon, intent);

        mContainer.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addItemDivider();
    }

    public View createDivider() {
        View view = new View(getActivity());
        view.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
        return view;
    }

    public void addItemDivider() {
        View divider = createDivider();
        mContainer.addView(divider, ViewGroup.LayoutParams.MATCH_PARENT, mItemDividerHeight);
    }

    public void addCategoryDivider() {
        mContainer.addView(createDivider(), ViewGroup.LayoutParams.MATCH_PARENT, mCategoryDividerHeight);
    }


    private void signOut() {
        getActivity().finish();
    }
}
