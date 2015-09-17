package com.vcanpay.activity.register;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.request.BaseJsonRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by patrick wai on 2015/6/17.
 */
public class ValidEmailRequest extends BaseJsonRequest<ValidEmailResponse> {
    private static final String endPoint = "RegisterDAO/validateEmailJj";

    private Map<String, String> headers = new HashMap<>();

    private String email;

    public ValidEmailRequest(int method, String url, String requestBody, String signBody, Response.Listener<ValidEmailResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, signBody, listener, errorListener);
    }

    public ValidEmailRequest(String email, Response.Listener<ValidEmailResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + "?email=" + email, null, null, listener, errorListener);
        this.email = email;
    }

    @Override
    protected Response<ValidEmailResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        String message = null;

        try {
            message = new String(response.data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (statusCode == 200) {
            ValidEmailResponse validEmailResponse = new ValidEmailResponse();
            validEmailResponse.setStatusCode(statusCode);
            validEmailResponse.setMessage(message);
            return Response.success(validEmailResponse, entry);
        }

        if (message != null) {

            return Response.error(new VolleyError(message));
        }

        return Response.error(new UnknownException());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers = super.getHeaders();
        String app_time = String.valueOf(System.currentTimeMillis() / 1000);

        String signString = null;
        String sign = null;
        try {
            signString = Config.app_key + Utils.MD5(Config.app_secret) + app_time + "\"" + email + "\"";
            sign = Utils.MD5(signString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headers.put(APP_SIGN, sign);

        headers.put(APP_TIME, app_time);

        headers.put("test", signString);


        return headers;
    }
}
