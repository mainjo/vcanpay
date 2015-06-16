package com.vcanpay.request;

import com.android.volley.Response;

/**
 * Created by patrick wai on 2015/6/12.
 */
public class BillDetailsRequest extends BaseJsonRequest {

    public BillDetailsRequest(int method, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }
}
