package com.vcanpay.activity.profile;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/6.
 */

public class UpdateAddressRequest extends BaseJsonRequest<UpdateAddressResponse> {
    public static final String endPoint = "PersonalLoginDAO/updateAddress";

    private Context mContext;
    public UpdateAddressRequest(Context context, int method, String url, String requestBody, String signBody, Response.Listener<UpdateAddressResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
        this.mContext = context;
    }

    public UpdateAddressRequest(Context context, String requestBody, String signBody, Response.Listener<UpdateAddressResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
        this.mContext = context;
    }

    @Override
    protected Response<UpdateAddressResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        if (statusCode == 200) {
            return Response.success(new UpdateAddressResponse(statusCode, message), entry);
        }

        if (statusCode == 400) {
            return Response.error(new AuthFailureError(message));
        }

        return Response.error(new VolleyError(message));
    }
}
