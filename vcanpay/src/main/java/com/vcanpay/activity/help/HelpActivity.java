package com.vcanpay.activity.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.vcanpay.R;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class HelpActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    ListView mListViewHelp;
    ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        mListViewHelp = (ListView) findViewById(R.id.help_list);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"User Guide"});
        mListViewHelp.setAdapter(mAdapter);
        mListViewHelp.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
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
    public String toString() {
        return getResources().getString(R.string.title_activity_help);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
    }
}
