package com.vcanpay.activity.transfer;

import com.vcanpay.response.BaseResposne;

/**
 * Created by patrick wai on 2015/7/11.
 */
public class TransferResponse extends BaseResposne
{
    public TransferResponse(int statusCode, String message) {
        super(statusCode, message);
    }
}
