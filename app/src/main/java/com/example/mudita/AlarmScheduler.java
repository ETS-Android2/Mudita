package com.example.mudita;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmScheduler extends DiskpersistenceHelper {



    public static void  Schedulealarmfromdata(Context context,ArrayList<Medtimeobj> arrayList, String day)
    {
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(arrayList!=null&&arrayList.size()!=0)
        { for(int i=0;i<arrayList.size();i++)
        { PendingIntent alarmIntent = createPendingIntent(context,arrayList.get(i),day,i);
          alarmscheduler(arrayList.get(i),alarmIntent,alarmManager);}

        }
    }





    public  static  void removeAlarmsForReminder(Context context, ArrayList<Medtimeobj> arrayList) {
        Intent intent=new Intent(context.getApplicationContext(),AlarmReciever.class);
        if(arrayList!=null&&arrayList.size()!=0) {
            if(arrayList.get(0).getMedicine().equals("Update491")&&arrayList.get(0).getTime().equals("002"))
            {  int requestcode=491491;
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmMgr.cancel(alarmIntent);
                return;}
            for(int i=0;i<arrayList.size();i++) {
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmMgr.cancel(alarmIntent);
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, Medtimeobj medtimeobj, String day, int requestcode)
    {    if(medtimeobj.getMedicine().equals("Update491")&&medtimeobj.getTime().equals("002"))
        {requestcode=491491;}
        Intent intent=new Intent(context, AlarmReciever.class);
        intent.setAction(context.getString(R.string.action_notify_admin));
        StringBuilder stringBuilder=new StringBuilder();
       String str1=""+medtimeobj.getTime();
        Bundle bundle=new Bundle();
        bundle.putString("Medicine",stringBuilder.toString());
        bundle.putString("Time",str1);
        bundle.putString("Day",day);
        intent.putExtra("Bundle",bundle);
       Log.d("CreatependingIntent","Time "+str1+" Medicine "+stringBuilder.toString()+"Day "+day);

        return PendingIntent.getBroadcast(context, requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }
    private static void alarmscheduler(Medtimeobj medtimeobj, PendingIntent intent, AlarmManager alarmManager)
    {
        Calendar calendar=Calendar.getInstance();
         long timeinmillis=calendar.getTimeInMillis();
         String time=medtimeobj.getTime();
         int hour,min;

         hour=(Integer.parseInt(time))/100;
          min=(Integer.parseInt(time))%100;

         calendar.set(Calendar.HOUR_OF_DAY,hour);
         calendar.set(Calendar.MINUTE,min);
         calendar.set(Calendar.MILLISECOND,0);
         calendar.set(Calendar.SECOND,0);
        Calendar cal=Calendar.getInstance();

         Log.d("alarmschedul","medobj.gettime() "+medtimeobj.getTime(),null);

         Log.d("Alarmtime",""+hour+"  "+min+" "+calendar.getTimeInMillis()+" "+cal.getTimeInMillis(),null);
        if(medtimeobj.getMedicine().equals("Update491")&&medtimeobj.getTime().equals("002")) {
            Calendar malendar = Calendar.getInstance();
            malendar.set(Calendar.HOUR_OF_DAY,0);
            malendar.set(Calendar.MINUTE,2);
            malendar.set(Calendar.MILLISECOND,0);
            malendar.set(Calendar.SECOND,0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,malendar.getTimeInMillis(),60000*60*24,intent);
        }
        else {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent);
        }
    }




}
