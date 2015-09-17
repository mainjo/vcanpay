package com.vcanpay.bankcard;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by patrick wai on 2015/7/22.
 */
public class InputAccountToValidateRequest extends BaseJsonRequest<InputAccountToValidateResponse> {
    public static final String endPoint = "MgrBindBankCardDAO/confirmBankCardByremitMoney";

    int customerId;
    BigDecimal remitMoney;

    public InputAccountToValidateRequest(int customerId, BigDecimal remitMoney, Response.Listener<InputAccountToValidateResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + String.format("?customId=%d&remitMoney=%s", customerId, remitMoney), null, null, listener, errorListener);
        this.customerId = customerId;
        this.remitMoney = remitMoney;
    }

    @Override
    protected Response<InputAccountToValidateResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        if (statusCode == 200) {
            if (TextUtils.isEmpty(message)) {
                message = "success";
            }
            return Response.success(new InputAccountToValidateResponse(statusCode, message), entry);
        }

        return Response.error(TextUtils.isEmpty(message) ? new UnknownException() : new VolleyError(message));

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        String app_time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            headers.put(APP_SIGN, Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + customerId + String.format("%s", remitMoney)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }
}
