package com.dinnova.sharedlibrary.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import com.dinnova.sharedlibrary.utils.views.CustomApplication;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class NotificationReceiving extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationRecieved(remoteMessage);
    }


    private void notificationRecieved(RemoteMessage remoteMessage) {
        Log.e("NotificationId", remoteMessage.getMessageId() + "");
        NotificationModel notificationModel;
        NotificationChannel mChannel = null;
        try {
            notificationModel = (NotificationModel) new NotificationModel().jsonToModel(new JSONObject(remoteMessage.getData()).toString());
            notificationModel.convertData();

        } catch (Exception e) {
            notificationModel = getDefaultNotification(remoteMessage);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(notificationModel.NotificationTypeModel.Id + "", notificationModel.NotificationTypeModel.Name, NotificationManager.IMPORTANCE_HIGH);
        }
        NotificationDisplay notificationDisplay = new NotificationDisplay(this, notificationModel, mChannel);
        CustomApplication.notificationListener.notificationReceived(notificationModel, notificationDisplay);

    }

    private NotificationModel getDefaultNotification(RemoteMessage remoteMessage) {

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.NotificationTypeModel = new NotificationType();
        notificationModel.NotificationTypeModel.Id = 0;
        notificationModel.TargetIdInt = 0;
        notificationModel.NotificationTypeModel.Name = "Default";
        if (remoteMessage.getNotification().getTitle() != null)
            notificationModel.Title = remoteMessage.getNotification().getTitle();
        else notificationModel.Title = "";
        notificationModel.Body = remoteMessage.getNotification().getBody();
        return notificationModel;
    }

}