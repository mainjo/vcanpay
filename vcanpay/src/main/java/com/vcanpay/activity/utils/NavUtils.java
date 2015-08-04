package com.vcanpay.activity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vcanpay.activity.SignInActivity;
import com.vcanpay.activity.TabhostActivity;
import com.vcanpay.bankcard.ActivateBankCardActivity;
import com.vcanpay.bankcard.BankCardDetailsActivity;

import org.vcanpay.eo.CustBankCard;

/**
 * Created by patrick wai on 2015/7/30.
 */
public class NavUtils {

    public static final String BANK_CARD_KEY = "bank_card_key";


    public static void signIn(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void viewBankCardDetails(Activity context, CustBankCard bankCard) {
        Intent intent = new Intent(context, BankCardDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BANK_CARD_KEY, bankCard);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void activateBankCard(Activity context, CustBankCard bankCard) {
        Intent intent = new Intent(context, ActivateBankCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BANK_CARD_KEY, bankCard);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void goHome(Context context) {
        Intent intent = new Intent(context, TabhostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
