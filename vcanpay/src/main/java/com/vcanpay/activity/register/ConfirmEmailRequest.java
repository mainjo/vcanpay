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
public class ConfirmEmailRequest extends BaseJsonRequest<ConfirmEmailResponse> {
    private static final String endPoint = "RegisterDAO/emailConfirm";

    public ConfirmEmailRequest(String email, Response.Listener<ConfirmEmailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, baseUrl + endPoint + "?email="+ email, null, listener, errorListener);
    }

    @Override
    protected Response<ConfirmEmailResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        Map<String, String> headers = response.headers;

        printHeaders(headers, "=====Response Headers======");

//        String message = entry.etag;

        ConfirmEmailResponse confirmEmailResponse = new ConfirmEmailResponse(statusCode, "");
        return Response.success(confirmEmailResponse, entry);
    }
}
