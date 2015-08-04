package com.vcanpay.response;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class BaseResposne {
    protected int statusCode;
    protected String message;

    public BaseResposne() {
    }

    public BaseResposne(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

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
