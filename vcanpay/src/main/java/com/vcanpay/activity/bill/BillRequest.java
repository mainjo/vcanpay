package com.vcanpay.activity.bill;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.vcanpay.eo.CustomTrade;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class BillRequest extends JsonRequest<CustomTrade[]> {

    public BillRequest(String url, Response.Listener<CustomTrade[]> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }


    public BillRequest(int method, String url, String requestBody, Response.Listener<CustomTrade[]> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<CustomTrade[]> parseNetworkResponse(NetworkResponse response) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.create();

        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(gson.fromJson(jsonString, CustomTrade[].class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
