package com.dinnova.sharedlibrary.notification;

import com.dinnova.sharedlibrary.webservice.JsonConverter;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationModel extends JsonConverter {

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
    public String Body;

    public void convertData() {
        TargetIdInt = Integer.parseInt(TargetId);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebService.Data, new JSONObject(NotificationType));
            NotificationTypeModel = (com.dinnova.sharedlibrary.notification.NotificationType) new NotificationType().jsonToModel(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
