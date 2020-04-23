package com.dinnova.sharedlibrary.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.dinnova.sharedlibrary.webservice.WebService;

import java.util.Iterator;
import java.util.List;

public class NotificationDisplay {
    private Context context;
    private NotificationModel notificationModel;
    private NotificationListener notificationListener;
    public NotificationChannel mChannel;

    NotificationDisplay(Context context, NotificationModel notificationModel, NotificationChannel mChannel, NotificationListener notificationListener) {
        this.context = context;
        this.mChannel = mChannel;
        this.notificationModel = notificationModel;
        this.notificationListener = notificationListener;
    }

    void showNotification(int logo, List<NotificationActionModel> actions) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = notificationModel.NotificationTypeModel.Id + "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(logo)
                .setContentTitle(notificationModel != null ? notificationModel.Title : "")
                .setContentText(notificationModel != null ? notificationModel.Body : "")
                .setContentIntent(getPendingIntent("default"));

        for (Iterator<NotificationActionModel> it = actions.iterator(); it.hasNext(); ) {
            NotificationActionModel notificationActionModel = it.next();
            notificationBuilder.addAction(0, notificationActionModel.name, getPendingIntent(notificationActionModel.id));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.setPriority(NotificationManager.IMPORTANCE_MAX);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationListener.notificationDisplayed(notificationModel);
        notificationManager.notify(notificationModel.TargetIdInt, notificationBuilder.build());
    }


    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        intent.putExtra(WebService.Data, notificationModel);
        intent.putExtra("listener", notificationListener);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
