package com.vcanpay.demo;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by patrick wai on 2015/6/29.
 */
public class Main {

    public static void main(String[] args) {
        String.format(Locale.CHINA, "{0:C}", 0.2);


        double d = 300000 / 1.0;

        System.out.println("default: " + d);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        System.out.println("numberFormat:" + numberFormat.format(d));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        System.out.println("currency format: " + currencyFormat.format(d));

        System.out.println(String.format("%.2f", d));

    }
}
