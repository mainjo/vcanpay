package com.vcanpay.bankcard;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vcanpay.R;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BindBankCardRequest extends BaseJsonRequest<BindBankCardResponse> {
    public static final String VERIFICATION_CODE_ERROR = "Code is incorrect, please re-enter:";
    public static final String HAVE_CARD_NOT_CONFIRMED = "You have a credit card to be confirmed, please go to confirm";


    private static final String endPoint = "MgrBindBankCardDAO/selectedRow";

    Context context;

    public BindBankCardRequest(Context context, String birthday, String requestBody, String signBody, Response.Listener<BindBankCardResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint + "?userBirth=" + birthday, requestBody, signBody, listener, errorListener);
        this.context = context;
    }

    @Override
    protected Response<BindBankCardResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;
        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {

            if (message == null) {
                message = context.getString(R.string.success);
            }
            BindBankCardResponse bindBankCardResponse = new BindBankCardResponse(statusCode, message);
            return Response.success(bindBankCardResponse, entry);
        }
        if (statusCode == 203) {
            if (message != null) {
                if (message.equals(HAVE_CARD_NOT_CONFIRMED)) {
                    return Response.error(new HaveCardNotConfirmedException());
                }
                if (message.equals(VERIFICATION_CODE_ERROR)) {
                    return Response.error(new VerificationCodeError());
                }
                return Response.error(new VolleyError(message));
            }
            return Response.error(new UnknownException());
        }
        return Response.error(message == null ? new UnknownException() : new VolleyError(message));
    }

    public class VerificationCodeError extends VolleyError {
    }

    public class HaveCardNotConfirmedException extends VolleyError {
    }
}
