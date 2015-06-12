package com.vcanpay.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.vcanpay.R;
import com.vcanpay.activity.bill.BillFragment;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class TabhostActivity extends ActionBarActivity implements OnFragmentInteractionListener, ActionBar.OnNavigationListener {

    FragmentTabHost mTabHost;
    TabWidget mTabWidget;

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            // TODO : 对象没有释放
            if (!"Tab3".equals(tabId)) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
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
        // TODO why doesn't the tab icons display?

        mTabHost.addTab(
                mTabHost.newTabSpec("Tab1")
                        .setIndicator("首页", getResources().getDrawable(R.drawable.icon)),
                FinalFragmentIThink.class,
                null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Tab2")
                        .setIndicator("功能", getResources().getDrawable(R.drawable.icon)),
                FuncItemFragment.class,
                null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Tab3")
                        .setIndicator("账单", getResources().getDrawable(R.drawable.icon)),
                BillFragment.class,
                null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Tab4")
                        .setIndicator("账户", getResources().getDrawable(R.drawable.icon)),
                AccountFragment.class,
                null);

        mTabHost.addTab(
                mTabHost.newTabSpec("Tab5")
                        .setIndicator("设置", getResources().getDrawable(R.drawable.icon)),
                SettingsFragment.class,
                null);

        mTabWidget.setStripEnabled(false);


        mTabHost.setOnTabChangedListener(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabhost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }
}
