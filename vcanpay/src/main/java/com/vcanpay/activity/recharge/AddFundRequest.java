package com.vcanpay.activity.recharge;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vcanpay.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.request.BaseJsonRequest;

import org.vcanpay.eo.CustomerAccount;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/25.
 */
public class AddFundRequest extends BaseJsonRequest<AddFundResponse> {

    public static final String endPoint = "MgrAddFundTransDAO/selectedRow";

    private Context context;
    public AddFundRequest(Context context, String endPoint, String requestBody, String signBody, Response.Listener<AddFundResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
        this.context = context;
    }

    @Override
    protected Response<AddFundResponse> parseNetworkResponse(NetworkResponse response) {

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        int statusCode = response.statusCode;

        String message = null;
        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AddFundResponse addFundResponse;
        if (statusCode == 200) {

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            CustomerAccount account = gson.fromJson(message, CustomerAccount.class);

            addFundResponse = new AddFundResponse(statusCode, message);
            addFundResponse.setCustomerAccount(account);
            return Response.success(addFundResponse, entry);
        }

        if (statusCode == 400) {
            message = message != null ? message : context.getString(R.string.auth_error);
            return Response.error(new AuthFailureError(message));
        }

        return Response.error(new VolleyError(response));
    }
}
