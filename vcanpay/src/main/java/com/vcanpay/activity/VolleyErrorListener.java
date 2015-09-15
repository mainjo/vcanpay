package com.vcanpay.activity;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vcanpay.R;
import com.vcanpay.exception.PrepaidCardSoldOutException;
import com.vcanpay.exception.ReduplicatedSubmitError;
import com.vcanpay.exception.UnknownException;

import org.apache.http.HttpStatus;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/4.
 */
public class VolleyErrorListener implements Response.ErrorListener {
    public static final String TAG = "VolleyErrorListener";
    BaseActivity mActivity;

    Context mContext;

    public VolleyErrorListener(BaseActivity activity) {
        mActivity = activity;
        mContext = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mActivity.closeProgressDialog();


        if (error instanceof TimeoutError) {
            mActivity.showAlertDialog(
                    mActivity,
                    getString(R.string.notify), getString(R.string.timeout_message));
            return;
        }

        if (error instanceof NetworkError) {
            mActivity.showAlertDialog(
                    mActivity,
                    getString(R.string.notify),
                    getString(R.string.network_error_message));
            return;
        }

        if (error instanceof AuthFailureError) {
            int statusCode = error.networkResponse.statusCode;
            switch (statusCode) {
                case HttpStatus.SC_UNAUTHORIZED:
                    Toast.makeText(mActivity, getString(R.string.AUTHORIZED_FAILURE_MESSAGE), Toast.LENGTH_LONG).show();
                    return;
                case HttpStatus.SC_FORBIDDEN:
                    mActivity.showAlertDialog(mActivity,
                            getString(R.string.notify), getString(R.string.FORBIDDEN_MESSAGE));
                    Toast.makeText(mActivity, getString(R.string.VISIT_FIRBIDDEN_MESSAGE), Toast.LENGTH_LONG).show();
                    return;
            }
            return;
        }

        // TODO 此处应该放到最后？？？
        if (error instanceof ServerError) {
            NetworkResponse response = error.networkResponse;

            int statusCode = response.statusCode;

            if (response.statusCode >= 500 && response.statusCode <= 509) {
                mActivity.showAlertDialog(mActivity, getString(R.string.notify), getString(R.string.server_error_message, statusCode));
                return;
            }

            if (statusCode == 400) {
                mActivity.showAlertDialog(mActivity, getString(R.string.notify), getString(R.string.auth_error));
                return;
            }

            // 以下是未知的服务器返回状态
            String message = null;
            try {
                message = new String(response.data, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(message)) {
                message = HttpHeaderParser.parseCacheHeaders(response).etag;
            }

            mActivity.showAlertDialog(
                    mActivity,
                    getString(R.string.notify),
                    message
            );
            return;
        }


        // 重复提交
        if (error instanceof ReduplicatedSubmitError) {
            mActivity.showAlertDialog(
                    mActivity,
                    getString(R.string.notify),
                    getString(R.string.reduplicate_submit_message)
            );
            return;
        }

        if (error instanceof UnknownException) {
            mActivity.showAlertDialog(mActivity, getString(R.string.notify), getString(R.string.unknown_error));
        }

        // 充值卡已售完
        if (error instanceof PrepaidCardSoldOutException) {
            mActivity.showAlertDialog(mActivity, getString(R.string.notify), getString(R.string.prepaid_card_sold_out_message));
            return;
        }

    }
    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    protected String getString(int resId, Object... args) {
        return mContext.getString(resId, args);
    }
}
