package com.vcanpay.activity.password;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class ChangePasswordRequest extends BaseJsonRequest<ChangePasswordResponse> {
    public static final String PASSWORD_ERROR = "OldPassWord Error";

    private static final String endPoint = "FindPassword/updateBypassWord";

    public ChangePasswordRequest(int method, String url, String requestBody, String signBody, Response.Listener<ChangePasswordResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
    }

    public ChangePasswordRequest(String requestBody, String signBody, Response.Listener<ChangePasswordResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
    }

    @Override
    protected Response<ChangePasswordResponse> parseNetworkResponse(NetworkResponse response) {

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        int statusCode = response.statusCode;

        String message = null;
        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            return Response.success(new ChangePasswordResponse(statusCode, message), entry);
        }

        if (statusCode == 203) {
            if (message != null) {
                if (message.equals(PASSWORD_ERROR)) {
                    return Response.error(new PasswordErrorException());
                }

                return Response.error(new VolleyError(message));
            }

            return Response.error(new UnknownException());
        }

        return Response.error(message == null ? new UnknownException() : new VolleyError(message));
    }

    public class PasswordErrorException extends VolleyError {
    }
}
