package com.vcanpay.activity.profile;

import com.vcanpay.response.BaseResposne;

/**
 * Created by patrick wai on 2015/7/8.
 */
public class UpdateAddressResponse extends BaseResposne {

    public UpdateAddressResponse(){}

    public UpdateAddressResponse(int statusCode, String message) {
        super(statusCode, message);
    }
}
