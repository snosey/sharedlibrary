package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.dinnova.sharedlibrary.webservice.JsonConverter2;
import com.google.gson.annotations.Expose;

public class NotificationActionModel extends JsonConverter2 {

    @Expose
    public String id;

    @Expose
    public String name;
}
