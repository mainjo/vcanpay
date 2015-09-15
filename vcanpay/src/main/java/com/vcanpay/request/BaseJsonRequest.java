package com.vcanpay.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.vcanpay.Config;
import com.vcanpay.activity.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick wai on 2015/6/11.
 */
public class BaseJsonRequest<T> extends JsonRequest<T> {

    private static final String BASE_URL = "http://123.1.189.38:8080/v20150910/ws/";
//    public static final String BASE_URL = "http://192.168.1.132:8080/vcanpayNew/ws/";

    public static final String APP_KEY = "app_key";
    public static final String APP_SECRET = "app_secret";
    public static final String APP_SIGN = "app_sign";
    public static final String APP_TIME = "app_time";

    public static final String TAG = "BaseJsonRequest";

    Map<String, String> headers = new HashMap<>();

    private String signBody;

    public BaseJsonRequest(int method, String endPoint, String body, String signBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, BASE_URL + endPoint, body, listener, errorListener);
        this.signBody = signBody;

        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 0));
    }

    public BaseJsonRequest(String endPoint, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, BASE_URL + endPoint, null, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        return null;
    }

    @Override
    public Response.ErrorListener getErrorListener() {
        return super.getErrorListener();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        String app_time = String.valueOf(System.currentTimeMillis() / 1000);
        headers.put("Accept", "*/*");
        try {
            String signString = Config.app_key + Utils.MD5(Config.app_secret) + app_time + getSignBody();
            String app_sign = Utils.MD5(signString);

            headers.put(APP_KEY, Config.app_key);
            headers.put(APP_SECRET, Utils.MD5(Config.app_secret));
            headers.put(APP_SIGN, app_sign);
            headers.put(APP_TIME, app_time);
            headers.put("test", Config.app_key + Utils.MD5(Config.app_secret) + app_time + getSignBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setSignBody(String signBody) {
        this.signBody = signBody;
    }

    public String getSignBody() {
        return signBody;
    }

}
