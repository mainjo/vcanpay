package com.vcanpay.exception;

import com.android.volley.VolleyError;

/**
 * Created by patrick wai on 2015/9/10.
 */
public class ReduplicatedSubmitError extends VolleyError {
    public ReduplicatedSubmitError() {
    }

    public ReduplicatedSubmitError(String exceptionMessage) {
        super(exceptionMessage);
    }
}
