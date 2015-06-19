package com.vcanpay.activity.password;

/**
 * Created by patrick wai on 2015/6/19.
 */
public class ChangePasswordResponse {
    int statusCode;
    String message;

    public ChangePasswordResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
