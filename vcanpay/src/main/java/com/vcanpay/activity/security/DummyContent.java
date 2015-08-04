package com.vcanpay.activity.security;

import android.app.Activity;

import com.example.vcanpay.R;
import com.vcanpay.activity.SettingsActivity;
import com.vcanpay.activity.help.FeedBackActivity;
import com.vcanpay.activity.help.HelpActivity;
import com.vcanpay.activity.profile.UpdateAddressActivity;
import com.vcanpay.activity.profile.UpdateMobileActivity;
import com.vcanpay.activity.profile.UserProfileActivity;
import com.vcanpay.activity.settings.SecurityManagementActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick wai on 2015/5/29.
 */
public class DummyContent {

    public static List<DummyItem> SECURITY_ITEMS = new ArrayList<DummyItem>();

    static {
        SECURITY_ITEMS.add(new DummyItem("1", R.string.title_activity_change_password, ChangePasswordActivity.class));
        SECURITY_ITEMS.add(new DummyItem("2", R.string.title_activity_change_payment_password, ChangePaymentPasswordActivity.class));
        SECURITY_ITEMS.add(new DummyItem("3", R.string.title_activity_find_payment_password, FindPaymentPasswordActivity.class));
    }

    public static List<DummyItem> SETTINGS_ITEMS = new ArrayList<>();

    static {
        SETTINGS_ITEMS.add(new DummyItem("1", R.string.title_activity_user_profile, UserProfileActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("2", R.string.title_activity_security_management, SecurityManagementActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("3", R.string.title_activity_feed_back, FeedBackActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("4", R.string.title_activity_help, HelpActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("5", R.string.title_activity_settings, SettingsActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("6", R.string.title_activity_update_address_request, UpdateAddressActivity.class));
        SETTINGS_ITEMS.add(new DummyItem("7", R.string.title_activity_update_mobile_request, UpdateMobileActivity.class));
    }

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
}
