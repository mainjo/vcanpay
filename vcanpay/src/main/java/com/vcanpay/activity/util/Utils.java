package com.vcanpay.activity.util;

import java.security.MessageDigest;

/**
 * Created by patrick wai on 2015/7/3.
 */
public class Utils {

    public static String MD5(String s) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        return byteToHex(messageDigest.digest(s.getBytes("utf-8")));
    }


    private static String byteToHex(byte[] tmp) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        char[] str = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        String s = new String(str);
        return s;
    }
}
