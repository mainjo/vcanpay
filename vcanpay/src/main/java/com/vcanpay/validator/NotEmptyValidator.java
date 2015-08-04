package com.vcanpay.validator;

import android.text.TextUtils;

/**
 * Created by patrick wai on 2015/6/29.
 */
public class NotEmptyValidator implements Validator {
    @Override
    public boolean validate(CharSequence content) {
        return !TextUtils.isEmpty(content);
    }
}
