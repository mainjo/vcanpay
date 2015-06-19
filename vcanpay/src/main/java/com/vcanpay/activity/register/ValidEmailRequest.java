package com.vcanpay.activity.register;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class ValidEmailRequest extends BaseJsonRequest<ValidEmailResponse> {
    private static final String endPoint = "RegisterDAO/validateEmailJj";

    public ValidEmailRequest(int method, String url, String requestBody, Response.Listener<ValidEmailResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public ValidEmailRequest(String email, Response.Listener<ValidEmailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, baseUrl + endPoint + "?email=" + email, null, listener, errorListener);
    }

    @Override
    protected Response<ValidEmailResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);


        String eTag = entry.etag;

        ValidEmailResponse validEmailResponse = new ValidEmailResponse();
        validEmailResponse.setStatusCode(statusCode);
        validEmailResponse.setMessage(eTag);

        return Response.success(validEmailResponse, entry);
    }
}
