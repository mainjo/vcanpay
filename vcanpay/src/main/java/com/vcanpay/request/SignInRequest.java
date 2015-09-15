package com.vcanpay.request;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcanpay.Config;
import com.vcanpay.activity.bill.DateDeserializer;
import com.vcanpay.exception.EmailNotConfirmedException;
import com.vcanpay.exception.EmailNotExsitException;
import com.vcanpay.exception.PasswordIncorrectException;
import com.vcanpay.exception.UnknownException;
import com.vcanpay.response.SignInResponse;

import org.vcanpay.eo.CustomInfo;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;


/**
 * Created by patrick wai on 2015/6/11.
 */
public class SignInRequest extends BaseJsonRequest<SignInResponse> {
    private static final String endPoint = "PersonalLoginDAO/Login";

    public static final String EMAIL_NOT_CONFIRMED = "\"E-mail is not confirmed\"";
    public static final String PASSWORD_INCORRECT = "\"The password is incorrect\"";
    public static final String EMAIL_NOT_EXIST = "\"email does not exist\"";
    public static final String PASSWORD_ERROR_UP_TO_3_TIMES = "You have reached the third error, please continue to operate after 5 minutes";
    public static final String PASSWORD_ERROR_UP_TO_6_TIMES = "Up to 6 times the number of errors, please log in 10 minutes";
    public static final String PASSWORD_ERROR_UP_TO_9_TIMES = "Login too many times, please contact the staff to unlock";

    public SignInRequest(String requestBody, String signBody, Response.Listener<SignInResponse> listener, Response.ErrorListener errorListener) {
        super(Method.PUT, endPoint, requestBody, signBody, listener, errorListener);
    }

    @Override
    protected Response<SignInResponse> parseNetworkResponse(NetworkResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gsonBuilder.setDateFormat(Config.DATE_FORMAT_2).create();

        Map<String, String> headers = response.headers;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        int statusCode = response.statusCode;

        String message = null;

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setStatusCode(statusCode);
//        signInResponse.setMessage(message);

        if (statusCode == 200) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(headers, PROTOCOL_CHARSET));
                CustomInfo customInfo = gson.fromJson(jsonString, CustomInfo.class);
                signInResponse.setCustomInfo(customInfo);
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }

            return Response.success(signInResponse, entry);
        }

        if (statusCode == 204) {
            message = null;
            if (entry != null) {
                message = entry.etag;
                if (message.equals(EMAIL_NOT_EXIST)) {
                    return Response.error(new EmailNotExsitException());
                }
                if (message.equals(PASSWORD_INCORRECT)) {
                    return Response.error(new PasswordIncorrectException());
                }

                if (message.equals(EMAIL_NOT_CONFIRMED)) {
                    return Response.error(new EmailNotConfirmedException());
                }
            }

            return Response.error(message == null ? new UnknownException() : new VolleyError(message));
        }

        if (statusCode == 203) {
            message = null;
            try {
                message = new String(response.data, HttpHeaderParser.parseCharset(headers, PROTOCOL_CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (message != null && message.equals(PASSWORD_ERROR_UP_TO_3_TIMES)) {
                return Response.error(new PasswordErrorUpTo3TimesException());
            }

            if (message != null && message.equals(PASSWORD_ERROR_UP_TO_6_TIMES)) {
                return Response.error(new PasswordErrorUpTo6TimesException());
            }

            if (message != null && message.equals(PASSWORD_ERROR_UP_TO_9_TIMES)) {
                return Response.error(new PasswordErrorUpTo9TimesException());
            }

            return Response.error(message == null ? new UnknownException() : new VolleyError(message));

        }

        try {
            message = new String(response.data, HttpHeaderParser.parseCharset(headers, PROTOCOL_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (message == null && entry != null) {
            message = entry.etag;
        }
        return Response.error(message == null ? new UnknownException() : new VolleyError(message));
    }


    @Override
    protected void deliverResponse(SignInResponse response) {
        super.deliverResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    public class PasswordErrorUpTo3TimesException extends VolleyError {
    }

    public class PasswordErrorUpTo6TimesException extends VolleyError {
    }

    public class PasswordErrorUpTo9TimesException extends VolleyError {
    }
}
