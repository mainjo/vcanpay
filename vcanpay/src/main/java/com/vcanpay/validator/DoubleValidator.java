package com.vcanpay.validator;

import java.util.regex.Pattern;

/**
 * Created by patrick wai on 2015/6/29.
 */
public class DoubleValidator implements Validator {

    private static final String DEFAULT_REGEX = "[0-9]+[.]{0,1}[0-9]{0,2}";

    @Override
    public boolean validate(CharSequence content) {
        Pattern p = Pattern.compile(DEFAULT_REGEX);
        return p.matcher(content).matches();
    }
}
