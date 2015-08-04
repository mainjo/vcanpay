package com.vcanpay.bankcard;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vcanpay.R;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BindBankCardRequest extends BaseJsonRequest<BindBankCardResponse> {
    private static final String endPoint = "MgrBindBankCardDAO/selectedRow";

    Context context;

    public BindBankCardRequest(Context context, String birthday, String requestBody, String signBody, Response.Listener<BindBankCardResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint + "?userBirth=" + birthday, requestBody, signBody, listener, errorListener);
        this.context = context;
    }

    @Override
    protected Response<BindBankCardResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode ==200 || statusCode == 203) {

            if (message == null) {
                message = context.getString(R.string.success);
            }
            BindBankCardResponse bindBankCardResponse = new BindBankCardResponse(statusCode, message);
            return Response.success(bindBankCardResponse, entry);
        }

        return Response.error(new VolleyError(message));
    }
}
