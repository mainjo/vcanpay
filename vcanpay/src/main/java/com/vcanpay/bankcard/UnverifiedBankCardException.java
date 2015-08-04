package com.vcanpay.bankcard;

import android.content.Intent;

import com.android.volley.AuthFailureError;

/**
 * Created by patrick wai on 2015/7/28.
 */
public class UnverifiedBankCardException extends AuthFailureError {
    public UnverifiedBankCardException(String message) {
        super(message);
    }

    public UnverifiedBankCardException(Intent intent) {
        super(intent);
    }

    public UnverifiedBankCardException(String message, Intent intent) {

    }
}
