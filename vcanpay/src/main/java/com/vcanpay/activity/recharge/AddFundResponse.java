package com.vcanpay.activity.recharge;

import com.vcanpay.response.BaseResposne;

import org.vcanpay.eo.CustomerAccount;

/**
 * Created by patrick wai on 2015/7/10.
 */
public class AddFundResponse extends BaseResposne {

    private CustomerAccount customerAccount;

    public AddFundResponse() {

    }

    public AddFundResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }
}
