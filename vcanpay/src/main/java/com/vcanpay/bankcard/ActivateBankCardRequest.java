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
import java.util.Map;

/**
 * Created by patrick wai on 2015/7/22.
 */
public class ActivateBankCardRequest extends BaseJsonRequest<ActivateBankCardResponse> {
    public static final String VERIFICATION_CODE_ERROR = "Code is incorrect, please re-enter:";

    public static final String endPoint = "MgrBindBankCardDAO/bankCardByEmailValidate";

    int customerId;
    String mobilePhone;
    String randomCode;
    String regName;

    public ActivateBankCardRequest(int customerId, String mobilePhone, String randomCode, String regName, Response.Listener<ActivateBankCardResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + String.format("?customId=%d&mobilePhone=%s&randomCode=%s&regName=%s", customerId, mobilePhone, randomCode, regName), null, null, listener, errorListener);
        this.customerId = customerId;
        this.mobilePhone = mobilePhone;
        this.randomCode = randomCode;
        this.regName = regName;
    }

    @Override
    protected Response<ActivateBankCardResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            return Response.success(new ActivateBankCardResponse(statusCode, message), entry);
        }

        if (statusCode == 203) {
            if (!TextUtils.isEmpty(message)) {
                if (message.equals(VERIFICATION_CODE_ERROR)) {
                    return Response.error(new VerificationCodeError());
                }
                return Response.error(new VolleyError(message));
            }
            return Response.error(new UnknownException());
        }
        return Response.error(message == null ? new UnknownException() : new VolleyError(message));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        String app_time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            headers.put(APP_SIGN, Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + randomCode + mobilePhone + regName + customerId));
            headers.put(APP_TIME, app_time);
            headers.put("test", Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + randomCode + mobilePhone + regName + customerId));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return headers;
    }

    public class VerificationCodeError extends VolleyError {
    }
}
