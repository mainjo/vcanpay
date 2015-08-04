package com.vcanpay.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.Config;
import com.vcanpay.activity.bill.DateDeserializer;
import com.vcanpay.response.SignInResponse;

import org.vcanpay.eo.CustomInfo;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * Created by patrick wai on 2015/6/11.
 */
public class SignInRequest extends BaseJsonRequest<SignInResponse> {
    private static final String endPoint = "PersonalLoginDAO/Login";

    public SignInRequest(String requestBody, String signBody, Response.Listener<SignInResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, endPoint, requestBody, signBody, listener, errorListener);
    }


    @Override
    protected Response<SignInResponse> parseNetworkResponse(NetworkResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.setDateFormat(Config.DATE_FORMAT_2).create();

        Map<String, String> headers = response.headers;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        int statusCode = response.statusCode;
        String message = entry.etag;

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setStatusCode(statusCode);
        signInResponse.setMessage(message);

        if (statusCode == 200) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(headers, PROTOCOL_CHARSET));
                CustomInfo customInfo = gson.fromJson(jsonString, CustomInfo.class);
                signInResponse.setCustomInfo(customInfo);
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }

            return Response.success(signInResponse, entry);
        }

        if (statusCode == 204) {
            return Response.error(new AuthFailureError(message));
        } else {
            return Response.error(new VolleyError(message));
        }
    }
}
