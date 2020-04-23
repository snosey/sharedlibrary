package com.dinnova.sharedlibrary.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dinnova.sharedlibrary.webservice.WebService;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationListener notificationListener = (NotificationListener) intent.getSerializableExtra("listener");
        notificationListener.notificationClicked((NotificationModel) intent.getSerializableExtra(WebService.Data), intent.getAction(), context);
    }


}
