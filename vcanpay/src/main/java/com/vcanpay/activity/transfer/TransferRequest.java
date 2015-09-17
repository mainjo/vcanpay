package com.vcanpay.activity.transfer;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/7/6.
 */
public class TransferRequest extends BaseJsonRequest<TransferResponse> {

    public static final String BALANCE_SUFFICIENT = "not sufficient funds";
    public static final String ACCOUNT_NOT_EXIST = "email does not exist";

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
            return Response.success(
                    new TransferResponse(statusCode, message),
                    HttpHeaderParser.parseCacheHeaders(response)
            );
        }

        if (statusCode == 203) {
            if (message != null) {
                if (message.equals(BALANCE_SUFFICIENT)) {
                    return Response.error(new BalanceSufficentException());
                }
                if (message.equals(ACCOUNT_NOT_EXIST)) {
                    return Response.error(new AccountNotExistException());
                }
                return Response.error(new VolleyError(message));
            }
            return Response.error(new UnknownException());
        }

        return Response.error(message == null ? new UnknownException() : new VolleyError(message));
    }

    public class BalanceSufficentException extends VolleyError {
    }

    public class AccountNotExistException extends VolleyError {
    }
}
