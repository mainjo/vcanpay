package com.vcanpay.request;

import com.android.volley.Response;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class GetVcpAccountsRequest extends BaseJsonRequest<GetVcpAccountsResponse> {
    private static final String endPoint = "";

    public GetVcpAccountsRequest(int method, String url, String requestBody, Response.Listener<GetVcpAccountsResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }
}
