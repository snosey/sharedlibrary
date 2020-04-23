package com.dinnova.sharedlibrary.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import com.dinnova.sharedlibrary.utils.views.CustomApplication;
import com.dinnova.sharedlibrary.webservice.WebService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class NotificationReceiving extends com.google.firebase.messaging.FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationRecieved(remoteMessage);
    }


    private void notificationRecieved(RemoteMessage remoteMessage) {
        Log.e("NotificationId", remoteMessage.getMessageId() + "");
        try {
            NotificationModel notificationModel = (NotificationModel) new NotificationModel().jsonToModel(new JSONObject(remoteMessage.getData()).toString());
            notificationModel.convertData();
            NotificationChannel mChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(notificationModel.NotificationTypeModel.Id + "", notificationModel.NotificationTypeModel.Name, NotificationManager.IMPORTANCE_HIGH);
            }
            NotificationDisplay notificationDisplay = new NotificationDisplay(this, notificationModel, mChannel);
            CustomApplication.notificationListener.notificationReceived(notificationModel, notificationDisplay);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}