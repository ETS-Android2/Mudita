package com.example.mudita;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;

public class AppGlobalReciever extends BroadcastReceiver {
    private static final String NOTIFICATION_ID = "notification_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(context!=null&&intent!=null&&intent.getAction()!=null)
        {
            if(intent.getAction().equalsIgnoreCase(context.getString(R.string.action_medicine_administered)))
            {       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.cancelAll();



            }
        }


    }
}
