package com.vcanpay.activity.bill;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.request.BaseJsonRequest;

import org.vcanpay.eo.CustomTrade;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillRequest extends BaseJsonRequest<CustomTrade[]> {

    public static final String endPoint = "MgrCustomTradeDAO/findClientCustomtradeInfo";

    public static final String CUSTOMER_ID = "customId";

    private int customerId;


    public BillRequest(int customerId, Response.Listener<CustomTrade[]> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + "?" + CUSTOMER_ID + "=" + customerId, null, null, listener, errorListener);
        this.customerId = customerId;
    }

    @Override
    protected Response<CustomTrade[]> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Gson gson = gsonBuilder.create();

        String charset = HttpHeaderParser.parseCharset(response.headers);
        String result = null;
        try {
            result = new String(response.data, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200 || statusCode == 204) {

            return Response.success(gson.fromJson(result, CustomTrade[].class), entry);
        }
        if (statusCode == 203) {
            result = null;
            return Response.success(gson.fromJson(result, CustomTrade[].class), entry);
        }
        return Response.error(new VolleyError(result));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        String app_time = String.valueOf(System.currentTimeMillis() / 1000);

        try {
            String app_sign = Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + getCustomerId());
            headers.put(APP_SIGN, app_sign);
            headers.put(APP_TIME, app_time);
            headers.put("test", Config.app_key + Utils.MD5(Config.app_secret) + app_time + getCustomerId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
