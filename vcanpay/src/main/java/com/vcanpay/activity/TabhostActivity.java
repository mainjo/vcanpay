package com.vcanpay.activity;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.activity.bill.BillFragment;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class TabhostActivity extends BaseActivity implements OnFragmentInteractionListener, ActionBar.OnNavigationListener {

    FragmentTabHost mTabHost;
    TabWidget mTabWidget;

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            // TODO : 使spinner只显示在账单tab中
            if (!"Tab3".equals(tabId)) {
                ActionBar actionBar = getSupportActionBar();
//                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost2);
        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


//        View tabIndicator = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_indicator, )

//        addTab("首页", R.mipmap.icon, FinalFragmentIThink.class);
        addTab(getString(R.string.home), R.drawable.tab_1_1, FuncItemFragment.class);
        addTab(getString(R.string.bill), R.drawable.tab_2_1, BillFragment.class);
        addTab(getString(R.string.account), R.drawable.tab_3_1, AccountFragment.class);
        addTab(getString(R.string.me), R.drawable.tab_4_1, com.vcanpay.activity.settings.ProfileFragment.class);

        mTabWidget.setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(listener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("TabhostActivity", newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "Landscape" : "Portrait");
        newConfig.orientation = Configuration.ORIENTATION_LANDSCAPE;
        super.onConfigurationChanged(newConfig);

    }

    public FragmentTabHost getTabHost() {
        return mTabHost;
    }

    public TabWidget getTabWidget() {
        return mTabWidget;
    }

    private void addTab(String text, int drawableId, Class<? extends Fragment> clazz) {
        addTab(text, drawableId, clazz, null);
    }

    private void addTab(String text, int drawableId, Class<? extends Fragment> clazz, Bundle bundle) {
        FragmentTabHost tabHost = getTabHost();
        FragmentTabHost.TabSpec spec = tabHost.newTabSpec(text);

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(text);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        spec.setIndicator(tabIndicator);
        tabHost.addTab(spec, clazz, bundle);
    }

    public View createIndicatorView(CharSequence string, Drawable drawable) {
        TextView text;
        ImageView icon;
        LinearLayout linearLayout;

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);

        icon = new ImageView(this);
        icon.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
        icon.setImageResource(R.mipmap.ic_launcher);

        text = new TextView(this);
        text.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setTextSize(12);

        text.setText(string);

        linearLayout.addView(icon);
        linearLayout.addView(text);

        return linearLayout;
    }


    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }
}
