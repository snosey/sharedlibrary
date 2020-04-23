package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationActionModel extends JsonConverter {

    @Expose
    public String id;

    @Expose
    public String name;
}
