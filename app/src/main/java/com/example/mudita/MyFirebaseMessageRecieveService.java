package com.example.mudita;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessageRecieveService extends FirebaseMessagingService {
   String title,message;
    Context context;



    /*  SharedPreferences pref=getSharedPreferences(getString(R.string.usernameshpref), Context.MODE_PRIVATE);
        String Medicine=pref.getString("Medicine",null);*/


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");
        Log.d("OnRecievemsg","titie Message "+title+"  "+message,null);
        NotificationHelper.createSampleDataNotification(this,title,message,true);

    }
    public Context getContext() {
        return context;
    }


}
