package com.example.fatkick.subsystem.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private DatabaseUpdateReminder databaseUpdateReminder;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseUpdateReminder = new DatabaseUpdateReminder(context);
        databaseUpdateReminder.updateDatabase();


        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}
