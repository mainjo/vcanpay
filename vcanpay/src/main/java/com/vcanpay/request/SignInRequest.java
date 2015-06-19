package com.vcanpay.request;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.activity.bill.DateDeserializer;
import com.vcanpay.response.SignInResponse;

import org.vcanpay.eo.CustomInfo;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * Created by patrick wai on 2015/6/11.
 */
public class SignInRequest extends BaseJsonRequest<SignInResponse>{
    private static final String endPoint = "PersonalLoginDAO/Login";

    public SignInRequest(String requestBody, Response.Listener<SignInResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, baseUrl + endPoint, requestBody, listener, errorListener);
    }


    public SignInRequest(int method, String url, String requestBody, Response.Listener<SignInResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<SignInResponse> parseNetworkResponse(NetworkResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();

        Map<String, String> headers = response.headers;

        printHeaders(headers, "==========Http Response Headers==========");


        int statusCode = response.statusCode;

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setStatusCode(statusCode);

        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        if (statusCode == 200) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(headers, PROTOCOL_CHARSET));
                Log.i("TEST", jsonString);
                CustomInfo customInfo = gson.fromJson(jsonString, CustomInfo.class);

                signInResponse.setCustomInfo(customInfo);

            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        } else {
            signInResponse.setMessage(entry.etag);
        }
        return Response.success(signInResponse, entry);
    }

    @Override
    public byte[] getBody() {
        byte[] body = super.getBody();

        try {
            String bodyString = new String(body, "UTF-8");
            Log.i("Test", bodyString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return body;
    }
}
