package com.vcanpay.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick wai on 2015/6/11.
 */
public class BaseJsonRequest<T> extends JsonRequest<T> {

    public static final String baseUrl = "http://123.1.189.38:8080/vcanpayNew456/ws/";

    public static final String TAG = "BaseJsonRequest";

    private Map<String, String> headers = new HashMap<>();


    public BaseJsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        Log.i("TEST", "method: " + method + "url: " + url);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {


        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        headers.put("Content-Type", "application/json");


        printHeaders(headers, "=========Http Request Headers=====");
        return headers;
    }

    @Override
    public Response.ErrorListener getErrorListener() {
        return super.getErrorListener();
    }

    public void printHeaders(Map<String, String> headers, String tag) {
        Log.d(TAG, tag);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(String.format("\n%s: %s", header.getKey(), header.getValue()));
        }
        Log.d(TAG, sb.toString());
    }

    @Override
    public byte[] getBody() {
        byte[] body = super.getBody();

        try {
            Log.i("TEST", "Request body: " + new String(body, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return body;
    }
}
