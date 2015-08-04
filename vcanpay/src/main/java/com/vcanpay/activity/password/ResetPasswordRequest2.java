package com.vcanpay.activity.password;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/24.
 */
public class ResetPasswordRequest2 extends BaseJsonRequest<ResetPasswordResponse2> {

    public static final String endPoint = "FindPassword/updateByProperty";

    public ResetPasswordRequest2(String randomNumber, String body, String signBody, Response.Listener<ResetPasswordResponse2> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint + "?randomNumber=" + randomNumber, body, signBody, listener, errorListener);
    }

    @Override
    protected Response<ResetPasswordResponse2> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;

        String message = null;

        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            return Response.success(new ResetPasswordResponse2(statusCode, message), HttpHeaderParser.parseCacheHeaders(response));
        }
        return Response.error(new VolleyError(message));
    }
}
