package com.vcanpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.vcanpay.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Project Name:Vcanpay
 * File Name:${FILE_NAME}
 * Date:${Date}
 * Copyright (c) 2015, patrickwai.cn@hotmail.com All Rights Reserved.
 * <p/>
 * Created by patrick wai on 2015/8/10.
 */
public class AgreementAndPrivacyActivity extends BaseActivity{


    public static final String USER_AGREEMENT = "user_agreement";
    public static final String USER_PRIVACY = "user_privacy";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        WebView webView = new WebView(this);
//        setContentView(R.layout.activity_user_guide);
        setContentView(webView);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                activity.setProgress(newProgress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        InputStream in = null;
        if (text.equals(USER_AGREEMENT)) {

            setTitle(R.string.agreement);
            in = getResources().openRawResource(R.raw.agreement);
        }

        if (text.equals(USER_PRIVACY)) {

            setTitle(R.string.privacy);
            in = getResources().openRawResource(R.raw.privacy);
        }


        StringBuilder stringBuffer = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = stringBuffer.toString();
        webView.loadData(content, "text/html", null);
    }
}
