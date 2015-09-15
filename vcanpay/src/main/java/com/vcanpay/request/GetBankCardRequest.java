package com.vcanpay.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vcanpay.eo.CustBankCard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick wai on 2015/7/2.
 */
public class GetBankCardRequest extends BaseJsonRequest<CustBankCard[]> {

    public static final String endPoint = "MgrBindBankCardDAO/findBankCardByCustomId";

    Map<String, String> params = new HashMap<>();

    Context mContext;
    public GetBankCardRequest(Context context, String query, String signBody, Response.Listener<CustBankCard[]> listener, Response.ErrorListener errorListener) {
        super(Method.GET, endPoint + "?customId=" + query, null, signBody, listener, errorListener);
        mContext = context;
    }

    @Override
    protected Response<CustBankCard[]> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        CustBankCard[] cards = null;

        if (statusCode == 200) {
            try {
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                JSONArray array = new JSONArray(json);
                Log.i(TAG, array.toString());
                cards = new CustBankCard[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    cards[i] = new CustBankCard();
                    if (object.has("customBankId")) {

                        cards[i].setCustomBankId(object.getInt("customBankId"));
                    }

                    if (object.has("accountType")) {

                        cards[i].setAccountType(object.getString("accountType"));
                    }

                    if (object.has("bankCardNo")) {

                        cards[i].setBankCardNo(object.getString("bankCardNo"));
                    }

                    if (object.has("bankName")) {

                        cards[i].setBankName(object.getString("bankName"));
                    }

                    if (object.has("haveMobileCheck")) {

                        cards[i].setHaveMobileCheck(object.getString("haveMobileCheck"));
                    }

                    if (object.has("haveMoneyCheck")) {
                        cards[i].setHaveMoneyCheck(object.getString("haveMoneyCheck"));
                    }

                    if (object.has("userName")) {
                        cards[i].setUserName(object.getString("userName"));
                    }

                    if (object.has("mobilePhone")) {
                        cards[i].setMobilePhone(object.getString("mobilePhone"));
                    }
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
            return Response.success(cards, entry);
        } else {
            return Response.error(new VolleyError("fail."));
        }
    }

}
