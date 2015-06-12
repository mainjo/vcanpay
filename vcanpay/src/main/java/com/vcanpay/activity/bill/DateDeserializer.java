package com.vcanpay.activity.bill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by patrick wai on 2015/6/5.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date result = null;
        try {
            result = format.parse(json.getAsJsonPrimitive().getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
