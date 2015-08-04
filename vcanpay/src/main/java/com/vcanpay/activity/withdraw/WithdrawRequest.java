package com.vcanpay.activity.withdraw;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.Config;
import com.vcanpay.request.BaseJsonRequest;

import org.vcanpay.eo.CustomerAccount;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/25.
 */
public class WithdrawRequest extends BaseJsonRequest<CustomerAccount> {
    public static final String endPoint = "MgrWithdrawTransDAO/iosWithdrawBill";

    public WithdrawRequest(String requestBody, String signBody, Response.Listener<CustomerAccount> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
    }

    @Override
    protected Response<CustomerAccount> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;

        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (message == null) {

        }

        if (statusCode == 200) {

            Gson gson = new GsonBuilder().setDateFormat(Config.DATE_FORMAT_2).create();

            CustomerAccount account = gson.fromJson(message, CustomerAccount.class);

            return Response.success(account, entry);
        } else {
            return Response.error(new VolleyError(message));
        }
    }
}
