package com.example.mudita;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

public class DiskpersistenceHelper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        boolean  dot=true;
        NotificationHelper.createNotificationChannel(this, NotificationManager.IMPORTANCE_HIGH,dot,getString(R.string.channelname1),getString(R.string.channelDescription1));
        NotificationHelper.createNotificationChannel(this,NotificationManager.IMPORTANCE_HIGH,dot,getString(R.string.channelname2),getString(R.string.channelDescription2));
        NotificationHelper.createNotificationChannel(this,NotificationManager.IMPORTANCE_HIGH,dot,getString(R.string.ReceiveNoti),getString(R.string.channelDescription2));
    }
}
