package com.dinnova.sharedlibrary.webservice;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ahmed on 4/24/2017.
 */

//customize url data which need to send in post
public class UrlData extends JsonConverter implements Serializable {
    String string = "";
    public static String tokenkey = "", tokenValu = "";

    public static void init(String tokenKey, String tokenValu) {
        UrlData.tokenkey = tokenKey;
        UrlData.tokenValu = tokenValu;
    }

    public UrlData() {
        if (!UrlData.tokenValu.isEmpty()) {
            put(UrlData.tokenkey, UrlData.tokenValu);
        }
    }

    public void put(String key, String value) {

        if (value.equals("-1") || value.isEmpty())
            return;

        try {
            if (string.equals(""))
                string += key + "=" + URLEncoder.encode(value, "UTF-8");
            else
                string += "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String get() {
        return "?" + string;
    }
}
