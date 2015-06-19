package com.vcanpay.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by patrick wai on 2015/6/18.
 */
public class EmailValidator implements Validator {

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";

    @Override
    public boolean validate(String content) {

        String str="ceponline@yahoo.com.cn";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        EmailValidator validator = new EmailValidator();

        System.out.println(validator.validate(""));
    }
}
