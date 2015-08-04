package com.vcanpay.activity;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vcanpay.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/4.
 */
public class VolleyErrorListener implements Response.ErrorListener {
    public static final String TAG = "VolleyErrorListener";
    BaseActivity mActivity;

    public VolleyErrorListener(BaseActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mActivity.closeProgressDialog();


        if (error instanceof TimeoutError) {
            mActivity.showAlertDialog(
                    mActivity,
                    mActivity.getString(R.string.notify), mActivity.getString(R.string.timeout));
            return;
        }

        if (error instanceof NetworkError) {
            mActivity.showAlertDialog(
                    mActivity,
                    mActivity.getString(R.string.notify),
                    mActivity.getString(R.string.network_error) + mActivity.getString(R.string.network_error_hint));
            return;
        }

        if (error instanceof AuthFailureError) {
            mActivity.showAlertDialog(
                    mActivity,
                    mActivity.getString(R.string.notify),
                    error.getMessage());
            return;
        }

        if (error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;
            String message = null;
            try {
                message = new String(response.data, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(message)) {
                message = HttpHeaderParser.parseCacheHeaders(response).etag;
            }

            if (TextUtils.isEmpty(message)) {
                if (response.statusCode >= 500 && response.statusCode <= 509) {
                    message = mActivity.getString(R.string.server_error);
                } else {
                    message = mActivity.getString(R.string.unknown_error);
                }
            }

            mActivity.showAlertDialog(
                    mActivity,
                    mActivity.getString(R.string.notify),
                    message == null ? mActivity.getString(R.string.server_error) : message
            );
            return;
        }

        if (error != null) {
            mActivity.showAlertDialog(
                    mActivity,
                    mActivity.getString(R.string.notify), error.getMessage());
        }
    }
}
