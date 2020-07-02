package com.dinnova.sharedlibrary.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dinnova.sharedlibrary.utils.views.CustomApplication;
import com.dinnova.sharedlibrary.webservice.WebService;

    public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        CustomApplication.notificationListener.notificationClicked((NotificationModel) intent.getSerializableExtra(WebService.Data), intent.getAction(), context);
    }


}
