package com.vcanpay.activity.help;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vcanpay.R;
import com.vcanpay.activity.AccountFragment;
import com.vcanpay.activity.FinalFragmentIThink;
import com.vcanpay.activity.FuncItemFragment;
import com.vcanpay.activity.bill.BillFragment;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class TutorialActivity extends ActionBarActivity implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        tabLayout = (TabLayout) findViewById(R.id.view);

        tabLayout.setOnTabSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();

        tabLayout.setTabsFromPagerAdapter(new TabsAdapter(fm));
    }


    private class TabsAdapter extends FragmentPagerAdapter{

        Fragment[] fragments = {new FinalFragmentIThink(), new FuncItemFragment(), new BillFragment(), new AccountFragment()};

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "tab1";
                case 1:
                    return "tab2";
                case 2:
                    return "tab3";
                case 3:
                    return "tab4";
            }

            return "Default";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
