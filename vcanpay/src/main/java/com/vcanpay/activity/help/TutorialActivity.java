package com.vcanpay.activity.help;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        tabLayout.setOnTabSelectedListener(this);

        setUpTabCustomView(tabLayout);

    }

    private void setUpTabCustomView(TabLayout tabLayout) {
        int count = tabLayout.getTabCount();

        TextView text;
        ImageView icon;

        for (int i = 0; i < count; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);

            icon = new ImageView(this);
            icon.setLayoutParams(new ViewGroup.LayoutParams(80, 80));
            icon.setImageResource(R.mipmap.ic_launcher);

            text = new TextView(this);
            text.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setTextSize(12);

            text.setText(tabLayout.getTabAt(i).getText());

            linearLayout.addView(icon);
            linearLayout.addView(text);

            tabLayout.getTabAt(i).setCustomView(linearLayout);
        }
    }


    private class TabsAdapter extends FragmentPagerAdapter {

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
                    return "Home";
                case 1:
                    return "Service";
                case 2:
                    return "Bills";
                case 3:
                    return "Account";
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
