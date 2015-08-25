package com.vcanpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vcanpay.R;

public class VcanPayApp extends BaseActivity {

    ImageView mImageView;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (getCurrentCustomer() != null) {
//                startActivity(VcanPayApp.this, TabhostActivity.class);
//            } else {
//                startActivity(VcanPayApp.this, StartActivity.class);
//            }

            boolean isFirstSignIn = getSharedPreferences(BaseActivity.CUSTOMER_INFO, MODE_PRIVATE)
                    .getBoolean(SignInActivity.IS_FIRST_SIGN_IN, true);

            if (isFirstSignIn) {
                Intent intent = new Intent(VcanPayApp.this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else {
                startActivity(VcanPayApp.this, SignInActivity.class);
            }

            VcanPayApp.this.finish();
        }
    };

    static final int IS_WHAT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // No title bar
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vcan_pay_app);

        mImageView = (ImageView) findViewById(R.id.start_image);

        Glide.with(this).fromResource().load(R.drawable.start_image).into(mImageView);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mHandler.sendEmptyMessage(IS_WHAT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacksAndMessages(null);
    }
}
