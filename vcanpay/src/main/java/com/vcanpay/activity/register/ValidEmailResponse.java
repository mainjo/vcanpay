package com.vcanpay.activity.register;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class ValidEmailResponse {
    int statusCode;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
