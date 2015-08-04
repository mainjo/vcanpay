package com.vcanpay.validate;

import android.widget.TextView;

import com.example.vcanpay.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by patrick wai on 2015/6/29.
 */
public class EmailValidator extends TextValidator {
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";

    public EmailValidator(TextView textView) {
        super(textView);
    }

    @Override
    public void validate(TextView textView, String text) {

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.matches()) {
            textView.setError(textView.getContext().getString(R.string.email_format_err));
        }

    }
}
