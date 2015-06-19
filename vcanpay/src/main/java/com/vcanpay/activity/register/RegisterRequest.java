package com.vcanpay.activity.register;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.util.Map;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class RegisterRequest extends BaseJsonRequest<RegisterResponse> {

    private static final String endPoint = "RegisterDAO/insertCAccount";

    public RegisterRequest(int method, String url, String requestBody, Response.Listener<RegisterResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public RegisterRequest(String requestBody, Response.Listener<RegisterResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, baseUrl + endPoint, requestBody, listener, errorListener);
    }

    @Override
    protected Response<RegisterResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        String message = entry.etag;

        Map<String, String> headers = response.headers;

        printHeaders(headers, "=====Response Headers======");

        RegisterResponse registerResponse = new RegisterResponse(statusCode, message);


        return Response.success(registerResponse, entry);
    }
}
