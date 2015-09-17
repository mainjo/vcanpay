package com.vcanpay.activity.register;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class RegisterRequest extends BaseJsonRequest<RegisterResponse> {
    public static final String DUPLICATE_ID_CARD = "idCard_Input_too_much";

    private static final String endPoint = "RegisterDAO/insertCAccount";

    public RegisterRequest(int method, String url, String requestBody, String signBody, Response.Listener<RegisterResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
    }

    public RegisterRequest(String requestBody, String signBody, Response.Listener<RegisterResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, endPoint, requestBody, signBody, listener, errorListener);
    }

    @Override
    protected Response<RegisterResponse> parseNetworkResponse(NetworkResponse response) {
        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        String message = null;

        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            RegisterResponse registerResponse = new RegisterResponse(statusCode, message);
            return Response.success(registerResponse, entry);
        }

        if (statusCode == 203) {
            if (message != null) {
                if (message.equals(DUPLICATE_ID_CARD)) {
                    return Response.error(new DuplicateIdCardException());
                }
                return Response.error(new VolleyError(message));
            }
            return Response.error(new UnknownException());
        }
        return Response.error(new VolleyError(message));
    }

    public class DuplicateIdCardException extends VolleyError {
    }
}
