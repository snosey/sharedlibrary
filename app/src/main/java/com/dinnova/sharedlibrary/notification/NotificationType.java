package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.google.gson.annotations.Expose;

public class NotificationType extends JsonConverter {

    @Expose
    public int Id;

    @Expose
    public String Name;
}
