package com.example.fatkick.subsystem.reminder;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.fatkick.R;

public class NotificationHelper extends ContextWrapper {


    public static final String channelID = "channelID";
    public static final String channelName = "DATABASE UPDATE";


    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Notification")
                .setContentText("Updating database")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active);
    }
}