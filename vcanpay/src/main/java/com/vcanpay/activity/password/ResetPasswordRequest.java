package com.vcanpay.activity.password;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/14.
 */
public class ResetPasswordRequest extends BaseJsonRequest<ResetPasswordResponse> {

    public static final String endPoint = "FindPassword/confirmByMultiProperty";


    Context mContext;
    public ResetPasswordRequest(Context context, int method, String url, String requestBody, String signBody, Response.Listener<ResetPasswordResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
        mContext = context;
    }


    public ResetPasswordRequest(Context context, String requestBody, String signBody, Response.Listener<ResetPasswordResponse> listener, Response.ErrorListener errorListener) {
        this(context, Method.POST, endPoint, requestBody, signBody, listener, errorListener);
    }

    @Override
    protected Response<ResetPasswordResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(message)) {
            message = entry.etag;
        }

        if (statusCode == 200) {
            return Response.success(new ResetPasswordResponse(statusCode, message), entry);
        }

        return Response.error(new VolleyError(message));
    }
}
