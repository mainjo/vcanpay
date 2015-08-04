package com.vcanpay.activity.dummy;

import android.app.Activity;

import com.example.vcanpay.R;
import com.vcanpay.activity.bill.PayActivity;
import com.vcanpay.activity.recharge.AddFundActivity;
import com.vcanpay.activity.recharge.CellphoneRechargeActivity;
import com.vcanpay.activity.transfer.SendMoneyActivity;
import com.vcanpay.activity.withdraw.WithdrawActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {

    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        addItem(new DummyItem("1", R.drawable.add_fund, R.string.add_fund, AddFundActivity.class));
        addItem(new DummyItem("2", R.drawable.pay, R.string.pay, PayActivity.class));
        addItem(new DummyItem("3", R.drawable.send_money, R.string.send_money, SendMoneyActivity.class));
        addItem(new DummyItem("4", R.drawable.cellphone_recharge, R.string.cellphone_recharge, CellphoneRechargeActivity.class));
        addItem(new DummyItem("5", R.drawable.mall, R.string.mall, null));
        addItem(new DummyItem("6", R.drawable.envelope, R.string.envelop, null));
        addItem(new DummyItem("7", R.drawable.withdraw, R.string.withdraw, WithdrawActivity.class));
        addItem(new DummyItem("8", R.drawable.guide, R.string.guide, null));
        addItem(new DummyItem("9", R.drawable.guide, R.string.surprise, null));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class DummyItem {
        public String id;
        public int drawable;
        public int title;
        public Class<? extends Activity> clazz;

        public DummyItem(String id, int title) {
            this.id = id;
            this.title = title;
        }

        public DummyItem(String id, int drawable, int title, Class<? extends Activity> clazz) {
            this.id = id;
            this.title = title;
            this.drawable = drawable;
            this.clazz = clazz;
        }
    }
}
