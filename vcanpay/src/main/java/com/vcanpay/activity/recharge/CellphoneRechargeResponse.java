package com.vcanpay.activity.recharge;

import com.vcanpay.response.BaseResposne;

/**
 * Created by patrick wai on 2015/7/13.
 */
public class CellphoneRechargeResponse extends BaseResposne {
    private PhoneCard phoneCard;


    public CellphoneRechargeResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public CellphoneRechargeResponse(PhoneCard phoneCard) {
        this.phoneCard = phoneCard;
    }


    public PhoneCard getPhoneCard() {
        return phoneCard;
    }

    public void setPhoneCard(PhoneCard phoneCard) {
        this.phoneCard = phoneCard;
    }

    public static class PhoneCard {
        public String cardNum;
        public String cardSecret;

        public PhoneCard(String cardNum, String cardSecret) {
            this.cardNum = cardNum;
            this.cardSecret = cardSecret;
        }
    }
}
