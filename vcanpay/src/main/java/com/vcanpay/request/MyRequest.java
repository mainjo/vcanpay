package com.vcanpay.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by patrick wai on 2015/7/18.
 */
public class MyRequest<T> extends Request<T> {

    Response.Listener<T> mListener;

    T mRequestBody;

    public MyRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public MyRequest(int method, String url, T body, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mRequestBody = body;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return super.getBody();
    }
}
