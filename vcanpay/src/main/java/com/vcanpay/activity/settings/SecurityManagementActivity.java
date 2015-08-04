package com.vcanpay.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vcanpay.R;
import com.vcanpay.activity.security.DummyContent;

import java.util.List;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class SecurityManagementActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView mListView;
    ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_management);

        mListView = (ListView) findViewById(android.R.id.list);

        mAdapter = new MyAdapter(
                this,
                R.layout.simple_list_item,
                DummyContent.SECURITY_ITEMS
        );

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_security_management, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clazz =  ((DummyContent.DummyItem)mAdapter.getItem(position)).clazz;

        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private class MyAdapter extends ArrayAdapter<DummyContent.DummyItem> {
        LayoutInflater mInflater;
        int mResource;

        public MyAdapter(Context context, int resource, List<DummyContent.DummyItem> objects) {
            super(context, resource, objects);
            this.mResource = resource;
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = mInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }

            DummyContent.DummyItem item = getItem(position);
            TextView tv = (TextView) view.findViewById(R.id.text1);
            tv.setText(item.title);

            return view;
        }
    }
}
