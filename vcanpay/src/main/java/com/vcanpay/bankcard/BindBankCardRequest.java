package com.vcanpay.bankcard;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.vcanpay.request.BaseJsonRequest;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BindBankCardRequest extends BaseJsonRequest<BindBankCardResponse> {
    private static final String endPoint = "";

    public BindBankCardRequest(int method, String url, String requestBody, Response.Listener<BindBankCardResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public BindBankCardRequest(String requestBody, Response.Listener<BindBankCardResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, baseUrl + endPoint, requestBody, listener, errorListener);
    }

    @Override
    protected Response<BindBankCardResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;


        if (statusCode == 201) {


        } else {

        }


        return super.parseNetworkResponse(response);
    }
}
