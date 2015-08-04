package com.vcanpay.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class GetVcpAccountsRequest extends BaseJsonRequest<NetworkResponse> {
    private static final String endPoint = "VcanpayBankAccountDAO/findVcanpayBankAccounttoSearch";

    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    public GetVcpAccountsRequest(String params, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + "?" + params, null, null, listener, errorListener);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        return Response.success(response, entry);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return headers;
    }

    @Override
    public String getBodyContentType() {
        return null;
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
