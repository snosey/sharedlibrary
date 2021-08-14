package com.dinnova.sharedlibrary.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dinnova.sharedlibrary.utils.views.CustomApplication;
import com.dinnova.sharedlibrary.webservice.WebService;

import java.util.Iterator;
import java.util.List;

public class NotificationDisplay {
    private Context context;
    private NotificationModel notificationModel;
    private NotificationChannel mChannel;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    NotificationDisplay(Context context, NotificationModel notificationModel, NotificationChannel mChannel) {
        this.context = context;
        this.mChannel = mChannel;
        this.notificationModel = notificationModel;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init(int logo, List<NotificationActionModel> actions) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = notificationModel.NotificationTypeModel.Id + "";
        notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(logo)
                .setContentTitle(notificationModel != null ? notificationModel.Title : "")
                .setContentText(notificationModel != null ? notificationModel.Body : "")
                .setContentIntent(getPendingIntent("default"));

        for (NotificationActionModel notificationActionModel : actions) {
            notificationBuilder.addAction(0, notificationActionModel.name, getPendingIntent(notificationActionModel.id));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.setPriority(NotificationManager.IMPORTANCE_MAX);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void callType(int ringtone, Intent intent) {
        addCustomVideoNotification(context, ringtone, intent);
        addCustomVideoChannel(context, ringtone);
    }

    public void display() {
        CustomApplication.notificationListener.notificationDisplayed(notificationModel);
        notificationManager.notify(notificationModel.TargetIdInt, notificationBuilder.build());
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        intent.putExtra(WebService.Data, notificationModel);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    private void addCustomVideoChannel(Context context, int ringtone) {
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getApplicationContext().getPackageName() + "/" + ringtone);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.setSound(soundUri, audioAttributes);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void addCustomVideoNotification(Context context, int ringtone, Intent intent) {
        PendingIntent fullScreenIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder
                .setCategory(Notification.CATEGORY_CALL)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true)
                .setTimeoutAfter(30000)
                .setOngoing(true)
                .setFullScreenIntent(fullScreenIntent, true)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + ringtone))
                        .setWhen(System.currentTimeMillis());
    }


}
