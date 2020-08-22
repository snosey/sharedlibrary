package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.dinnova.sharedlibrary.webservice.JsonConverter2;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationModel extends JsonConverter2 {

    public static class channelTypeEnum{
        public static int normal =1;
        public static int call =2;
    }
    public int channelType;

    @Expose
    public NotificationType NotificationTypeModel;

    @Expose
    public int TargetIdInt;

    @Expose
    public String NotificationType;

    @Expose
    public String TargetId;

    @Expose
    public String Title;

    @Expose
    public String Extra;

    @Expose
    public String Body;

    public void convertData() {
        TargetIdInt = Integer.parseInt(TargetId);
        try {
            NotificationTypeModel = (com.dinnova.sharedlibrary.notification.NotificationType) new NotificationType().jsonToModel(NotificationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
