package com.vcanpay.activity.dummy;

import com.example.vcanpay.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        addItem(new DummyItem("1", R.mipmap.ic_launcher, "\u5145\u503c"));
        addItem(new DummyItem("2", R.mipmap.ic_launcher, "\u4ed8\u6b3e"));
        addItem(new DummyItem("3", R.mipmap.ic_launcher, "\u8f6c\u8d26"));
        addItem(new DummyItem("4", R.mipmap.ic_launcher, "\u624b\u673a\u5145\u503c"));
        addItem(new DummyItem("5", R.mipmap.ic_launcher, "\u5546\u57ce"));
        addItem(new DummyItem("6", R.mipmap.ic_launcher, "\u7ea2\u5305"));
        addItem(new DummyItem("7", R.mipmap.ic_launcher, "\u63d0\u73b0"));
        addItem(new DummyItem("8", R.mipmap.ic_launcher, "\u6307\u5357"));
        addItem(new DummyItem("9", R.mipmap.ic_launcher, "\u5c0f\u60ca\u559c"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static List<DummyItem> SETTING_ITEMS = new ArrayList<>();

    /**
     * A dummy item representing a piece of content.
     */
    static {
        SETTING_ITEMS.add(new DummyItem("1", "\u4e2a\u4eba\u8d44\u6599"));
        SETTING_ITEMS.add(new DummyItem("2", "\u8d26\u6237\u5b89\u5168"));
        SETTING_ITEMS.add(new DummyItem("3", "\u8bbe\u7f6e"));
        SETTING_ITEMS.add(new DummyItem("3", "\u5e2e\u52a9"));
    }

    public static final List<DummyItem> SECURITY_ITEMS = new ArrayList<>();
    static {
        SECURITY_ITEMS.add(new DummyItem("1", "\u4fee\u6539\u767b\u5f55\u5bc6\u7801"));
        SECURITY_ITEMS.add(new DummyItem("2", "\u4fee\u6539\u652f\u4ed8\u5bc6\u7801"));
        SECURITY_ITEMS.add(new DummyItem("5", "\u627e\u56de\u652f\u4ed8\u5bc6\u7801"));
        SECURITY_ITEMS.add(new DummyItem("3", "\u5f00\u542f\u624b\u52bf\u5bc6\u7801"));
        SECURITY_ITEMS.add(new DummyItem("4", "\u4fee\u6539\u624b\u52bf\u5bc6\u7801"));
    }

    public static class DummyItem {
        public String id;
        public int drawable;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        public DummyItem(String id, int drawable, String content) {
            this.id = id;
            this.content = content;
            this.drawable = drawable;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
