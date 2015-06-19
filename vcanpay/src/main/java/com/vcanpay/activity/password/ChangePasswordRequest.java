package com.vcanpay.activity.password;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class ChangePasswordRequest extends BaseJsonRequest<ChangePasswordResponse> {

    private static final String endPoint = "FindPassword/updateByProperty";

    public ChangePasswordRequest(int method, String url, String requestBody, Response.Listener<ChangePasswordResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public ChangePasswordRequest(String requestBody, Response.Listener<ChangePasswordResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, baseUrl + endPoint, requestBody, listener, errorListener);
    }


    @Override
    protected Response<ChangePasswordResponse> parseNetworkResponse(NetworkResponse response) {

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String json = "nothing";
        try {
            json = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int statusCode = response.statusCode;

        if (response.statusCode == 200) {
            return Response.success(new ChangePasswordResponse(statusCode, json), entry);
        }

        try {
            String message = new String(response.data, "UTF-8");
            return Response.error(new VolleyError(message));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
