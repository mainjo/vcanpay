package com.vcanpay.activity.profile;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

/**
 * Created by patrick wai on 2015/7/6.
 */
public class UpdateMobileRequest extends BaseJsonRequest<NetworkResponse> {

    public static final String TAG = "UpdateMobileRequest";
    public static final String endpoint = "PersonalLoginDAO/updateMobile";

    public UpdateMobileRequest(int method, String url, String requestBody, String signBody, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
    }

    public UpdateMobileRequest(String requestBody, String signBody, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endpoint, requestBody, signBody, listener, errorListener);
    }


    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        Log.i(TAG, "status code: " + statusCode);

        return Response.success(response, entry);

    }
}