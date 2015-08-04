package com.vcanpay.activity.transfer;

import android.content.Context;

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
public class TransferRequest extends BaseJsonRequest<TransferResponse> {

    public static final String endPoint = "MgrSendMoneyTransDAO/sendMoneyTrans";

    Context mContext;
    public TransferRequest(Context context, String requestBody, String signBody, Response.Listener<TransferResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
        mContext = context;
    }


    @Override
    protected Response<TransferResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;

        String message = null;

        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            if (message == null) {
                message = mContext.getString(R.string.success);
            }
            return Response.success(new TransferResponse(statusCode, message),
                    HttpHeaderParser.parseCacheHeaders(response));
        }

        if (message == null) {
            message = mContext.getString(R.string.fail);
        }
        return Response.error(new VolleyError(message));
    }
}
