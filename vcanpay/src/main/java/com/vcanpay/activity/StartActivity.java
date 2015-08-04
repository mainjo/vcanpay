package com.vcanpay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.vcanpay.R;
import com.vcanpay.activity.register.RegisterActivity;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class StartActivity extends BaseActivity {
    private static final String TAG = "StartActivity";

    Button mSignIn;
    Button mRegister;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.sing_in:
                    startSignInActivity(StartActivity.this, SignInActivity.class);
                    break;
                case R.id.register:
                    startActivity(StartActivity.this, RegisterActivity.class);
                    break;
            }
        }
    };

    private void startSignInActivity(Context context, Class<? extends Activity> clazz ) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Full Screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // No Titlebar
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_start);

        mSignIn = (Button) findViewById(R.id.sing_in);
        mRegister = (Button) findViewById(R.id.register);

        mSignIn.setOnClickListener(listener);
        mRegister.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    /**
     * start an activity by context and activity
     *
     * @param ctx
     * @param clazz
     */
    public void startActivity(Context ctx, Class<? extends Activity> clazz) {
        Log.d(TAG, "Start Sign In Activity");
        Intent intent = new Intent(ctx, clazz);
        startActivity(intent);
    }
}
