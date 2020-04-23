package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter2;
import com.google.gson.annotations.Expose;

public class NotificationType extends JsonConverter2 {

    @Expose
    public int Id;

    @Expose
    public String Name;
}
