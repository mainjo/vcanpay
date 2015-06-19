package com.vcanpay.activity.register;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class SendEmailRequest extends BaseJsonRequest<SendEmailResponse> {

    private static final String endPoint = "RegisterDAO/sendEmail";

    public SendEmailRequest(String email, Response.Listener<SendEmailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, baseUrl + endPoint + "?email=" + email, null, listener, errorListener);
    }

    @Override
    protected Response<SendEmailResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
//        String message = entry.etag;
        String message = "";
        try {
            message = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("SendEmailRequest", "activation code: " + message);

        SendEmailResponse sendEmailResponse = new SendEmailResponse(statusCode, message);


        return Response.success(sendEmailResponse, entry);
    }
}
