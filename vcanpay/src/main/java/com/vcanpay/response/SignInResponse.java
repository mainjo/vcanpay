package com.vcanpay.response;

/**
 * Created by patrick wai on 2015/6/17.
 */
public class SignInResponse {
    int statusCode;
    String message;

    org.vcanpay.eo.CustomInfo customInfo;

    public org.vcanpay.eo.CustomInfo getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(org.vcanpay.eo.CustomInfo customInfo) {
        this.customInfo = customInfo;
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
