package com.example.mudita;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReciever extends BroadcastReceiver  {

    @Override
    public void onReceive(final Context context, Intent intent) {
       if(context!=null&&intent!=null&&intent.getAction()!=null)
       {
           if(intent.getAction().equalsIgnoreCase(context.getString(R.string.action_notify_admin)))
           {if(intent.getExtras()!=null)
           { Bundle bundle=intent.getBundleExtra("Bundle");
             String time=bundle.get("Time").toString();
             String medicine=bundle.getString("Medicine");
           if(time!=null||time.length()!=0)
           {NotificationHelper.createNotificationforAlarm(context,bundle);
           }

        /*   Thread thread=new Thread(new Runnable() {
               @Override
               public void run() {
                   DbandAppCom dbandAppCom=new DbandAppCom();
                   dbandAppCom.sendreqdata(context);
               }
           });
               try {
                   thread.sleep(25000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               thread.start();*/
           }
           }
       }
    }
}
