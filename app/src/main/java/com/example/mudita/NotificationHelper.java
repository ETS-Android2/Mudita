package com.example.mudita;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.ACTIVITY_SERVICE;

public class NotificationHelper extends AppCompatActivity {
    private static int ADMINISTER_REQUEST_CODE = 2019;


    public static void createNotificationChannel(Context context, int importance, Boolean showBadge, String name, String description) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           /* name = context.getString(R.string.channelname);
            description = context.getString(R.string.channelDescription);
            importance = NotificationManager.IMPORTANCE_HIGH;*/
            NotificationChannel Channel = new NotificationChannel("Alarmtone", name, importance);
            Channel.setDescription(description);
            Channel.setShowBadge(showBadge);
            Channel.enableVibration(true);

            NotificationManager manager = context.getSystemService(NotificationManager.class);


            manager.createNotificationChannel(Channel);
        }


    }

    public static void createSampleDataNotification(Context context, String title, String conttxt, boolean autocancel) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Alarmtone")
                .setSmallIcon(R.drawable.med)
                .setContentTitle(title)
                .setContentText(conttxt)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setAutoCancel(autocancel);


        Intent intent1 = new Intent(context, Splashscreen.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, 0);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        builder.setContentIntent(pi);
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(1001, builder.build());
    }


    public static void createNotificationforAlarm(Context context, Bundle bundle) {
        int random= new Random().nextInt(1000);
        if(bundle.get("Medicine").toString().equals("Update491")&&bundle.get("Time").toString().equals("002"))
        {
            return;}
        NotificationCompat.Builder groupbuilder = BuildGroupNotification(context, bundle, random);
        NotificationCompat.Builder notificationbuilder = BuildNotificationforalarm(context, bundle, random);
        PendingIntent adminpendingIntent1 = CreatePendingIntentForAction(context, bundle,"Done",random);
        PendingIntent adminpendingIntent2 = CreatePendingIntentForAction(context, bundle,"Cancel",random);
        PendingIntent adminpendingIntent3 = CreatePendingIntentForAction(context, bundle,"Missed",random);

        notificationbuilder.addAction(R.drawable.ic_menu_slideshow, context.getString(R.string.DoneAction), adminpendingIntent1);
        notificationbuilder.addAction(R.drawable.ic_menu_slideshow, context.getString(R.string.CancelAction), adminpendingIntent2);
        notificationbuilder.addAction(R.drawable.ic_menu_slideshow, context.getString(R.string.MissedAction), adminpendingIntent3);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(random, groupbuilder.build());
        notificationManager.notify(random, notificationbuilder.build());
        final Ringtone ringtone=RingtoneManager.getRingtone(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        final Context context1=context;
        final Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               if(ringtone.isPlaying())
               {ringtone.stop();
               timer.cancel();}
               else
               {ringtone.play();}
            }

        },0,10000);



    }

    private static NotificationCompat.Builder BuildGroupNotification(Context context, Bundle bundle,int random) {

        String channelid = "Alarmtone";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelid);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/alarmtone");
        builder.setSmallIcon(R.drawable.med)
                .setColor(Color.BLACK)
                .setContentTitle(bundle.get("Medicine").toString())
                .setContentText("Hi,Content Text Group")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This a group Notification bigtextstyle"))
              /*  .setSound(uri)*/  //may give error check!!
                .setAutoCancel(true)
                .setGroupSummary(true)
                .setGroup("Mudita_Reminder");

        return builder;
    }

    private static NotificationCompat.Builder BuildNotificationforalarm(Context context, Bundle bundle,int random) {
        String channelid = "Alarmtone";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelid);
        builder.setSmallIcon(R.drawable.med)
               /* .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/raw/alarmtone"))*/
                .setContentTitle(bundle.get("Medicine").toString())
                .setContentText("Content Text!")
                .setColor(Color.BLACK)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("It's time for your medicine "+bundle.get("Medicine").toString()))
                .setGroup("Mudita_Reminder")
                .setAutoCancel(true);
        //Notification ko dabane se kya hoga?
        Intent intent = new Intent(context, Splashscreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Bundle", bundle);
        PendingIntent pendingintent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingintent);
        return builder;
    }

    private static PendingIntent CreatePendingIntentForAction(Context context, Bundle bundle,String actionname,int random) {
        Intent adminintent;
        {adminintent = new Intent(context, AppGlobalReciever.class);}
        adminintent.setAction(context.getString(R.string.action_medicine_administered)+actionname);
        adminintent.putExtra("Bundle",bundle);
        adminintent.putExtra("Random",""+random);
        adminintent.putExtra("Actionname",actionname);
        Log.d("CreatePendingIforAction",bundle.getString("Medicine")+" "+bundle.get("Time").toString(), null);
        return PendingIntent.getBroadcast(context, ADMINISTER_REQUEST_CODE, adminintent, PendingIntent.FLAG_UPDATE_CURRENT);

    }
  /*  public static boolean isForeground(String myPackage,Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equals(myPackage);
      *//*  *//*
    }*/
}


