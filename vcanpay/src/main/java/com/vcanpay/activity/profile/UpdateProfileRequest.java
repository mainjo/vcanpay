package com.vcanpay.activity.profile;

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
 * Created by patrick wai on 2015/7/6.
 */
public class UpdateProfileRequest extends BaseJsonRequest<UpdateProfileResponse> {
    public static final String endPoint = "PersonalLoginDAO/update";

    Context mContext;

    public UpdateProfileRequest(Context context, int method, String query, String requestBody,String signBody, Response.Listener<UpdateProfileResponse> listener, Response.ErrorListener errorListener) {
        super(method, endPoint + "?birthData=" + query, requestBody, signBody, listener, errorListener);
        mContext = context;
    }


    @Override
    protected Response<UpdateProfileResponse> parseNetworkResponse(NetworkResponse response) {

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        int statusCode = response.statusCode;

        String message = null;
        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            if (message == null) {
                message = mContext.getString(R.string.success);
            }

            return Response.success(new UpdateProfileResponse(statusCode, message), entry);
        }

        if (message == null) {
            message = mContext.getString(R.string.fail);
        }

        return Response.error(new VolleyError(message));
    }
}
