package com.vcanpay.activity.help;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vcanpay.R;
/**
 * Created by patrick wai on 2015/6/5.
 */
public class FeedbackActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public String toString() {
        return getResources().getString(R.string.title_activity_feed_back);
    }
}
