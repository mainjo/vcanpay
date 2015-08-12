package com.vcanpay.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project Name:Vcanpay
 * Date:2015/6/18
 *
 * Created by patrick wai on 2015/6/18.
 */
public class EmailValidator implements Validator {

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";

    @Override
    public boolean validate(CharSequence content) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
