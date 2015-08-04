package com.vcanpay.activity.register;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by patrick wai on 2015/7/17.
 */
public class ActivateAccountRequest extends BaseJsonRequest<ActivateAccountResponse> {
    public static final String endPoint = "RegisterDAO/emailConfirm";

//    public Map<String, String> headers = new HashMap<>();
    Context mContext;

    String mEmail;
    String mActivateCode;

    public ActivateAccountRequest(Context context, String email, String activateCode, Response.Listener<ActivateAccountResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint + "?email=" + email + "&ClientRegisterEmailNumber=" + activateCode, null, null, listener, errorListener);
        mContext = context;
        mEmail = email;
        mActivateCode = activateCode;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers =  super.getHeaders();

        String app_time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
//            headers.put(APP_KEY, Config.app_key);
//            headers.put(APP_SECRET, Utils.MD5(Config.app_secret));
            headers.put(APP_SIGN, Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + mEmail + mActivateCode));
            headers.put(APP_TIME, app_time);
            headers.put("Content-type", "application/json;charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

    @Override
    protected Response<ActivateAccountResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        String message = null;

        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            ActivateAccountResponse activateAccountResponse = new ActivateAccountResponse(statusCode, message);
            return Response.success(activateAccountResponse, entry);
        }

        if (statusCode == 203) {
            return Response.error(new VolleyError(message));
        }

        return Response.error(new VolleyError(message));
    }
}
