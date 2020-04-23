package com.dinnova.sharedlibrary.notification;

import android.content.Context;

import java.io.Serializable;

public interface NotificationListener extends Serializable {
    void notificationClicked(NotificationModel notificationModel, String action, Context context);

    void notificationDisplayed(NotificationModel notificationModel);

    void notificationReceived(NotificationModel notificationModel, NotificationDisplay notificationDisplay);
}
