package com.vcanpay.activity.recharge;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by patrick wai on 2015/7/13.
 */
public class CellphoneRechargeRequest extends BaseJsonRequest<CellphoneRechargeResponse>{
    public static final String endPoint = "MobileRechargeDAO/ClientMobileRecharge";

    int mAmount;
    int mCustomerId;
    String mCarrier;
    String mPhoneNum;

    public CellphoneRechargeRequest(String carrier, String phoneNumber, int amount, int customerId, Response.Listener<CellphoneRechargeResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, endPoint + "?payeeMoney=" + amount + "&customId=" + customerId + "&company=" + carrier + "&mobilePhone=" + phoneNumber, null, null, listener, errorListener);
        mAmount = amount;
        mCustomerId = customerId;
        mCarrier = carrier;
        mPhoneNum = phoneNumber;
    }

    @Override
    protected Response<CellphoneRechargeResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;

        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {

            String[] cardStrings = message.split(":");

            return Response.success(new CellphoneRechargeResponse(new CellphoneRechargeResponse.PhoneCard(cardStrings[0], cardStrings[1])), entry);
        }

        return Response.error(new VolleyError(message));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> headers = super.getHeaders();

        long app_time = System.currentTimeMillis()/1000;

        try {
            headers.put(APP_SIGN, Utils.MD5(Config.app_key + Utils.MD5(Config.app_secret) + app_time + mCarrier + mPhoneNum + mAmount + mCustomerId));
            headers.put(APP_TIME, String.valueOf(app_time));
            headers.put("Content-type", "application/json;charset=utf-8");
            headers.put("test", Config.app_key + Utils.MD5(Config.app_secret) + app_time + mCarrier + mPhoneNum + mAmount + mCustomerId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return headers;
    }
}
