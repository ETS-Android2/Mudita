package com.example.mudita;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class DbandAppCom {
    private DBtoPhone dBtoPhone;
    private static ArrayList<Medtimeobj> medtimeobjsarray=new ArrayList<>();
    private int curhour,curmin,nxthour,nxtmin,count;
    private String checkones=new String();
    private Medtimeobj obj;
    private long diffhour, diffmin;
    private  String Medicinestr=new String();
    DbandAppCom(){

    }


    public void sendreqdata(Context context)
    {
        dBtoPhone = new DBtoPhone();
    try {
        dBtoPhone.Retriveperday();

        if (dBtoPhone.medtimeobjsalist != null && !dBtoPhone.medtimeobjsalist.isEmpty()) {
            if (medtimeobjsarray.size() != 0) {
                medtimeobjsarray.clear();
            }
            for (int i = 0; i < dBtoPhone.medtimeobjsalist.size(); i++) {
                medtimeobjsarray.add(dBtoPhone.medtimeobjsalist.get(i));
                Log.d("tuhaikinhiDBandAPP", " " + medtimeobjsarray.get(medtimeobjsarray.size() - 1).getMedicine(), null);
            }
            NotificationHelper.createSampleDataNotification(context, "Reminder", "HI,ALL GOOD?", true);
                medtimeobjsarray=timestarter(medtimeobjsarray);
            for(int j=0;j<medtimeobjsarray.size();j++)
            {Log.d("arraylistcheck","j="+j+" "+medtimeobjsarray.get(j).getMedicine()+" "+medtimeobjsarray.get(j).getTime(),null);}
            if(!checkones.equals(medtimeobjsarray.get(0).getTime())&&medtimeobjsarray.size()!=0)
            {   AlarmScheduler.removeAlarmsForReminder(context,medtimeobjsarray);
                AlarmScheduler.Schedulealarmfromdata(context,medtimeobjsarray, medtimeobjsarray.get(0).getDay());
                checkones=""+medtimeobjsarray.get(0).getTime();
            }
            }

    }
     catch (Exception e)
        {
           Log.d("aforapple","bforballsack",null);
        }

    }




    private  ArrayList<Medtimeobj> timestarter(ArrayList<Medtimeobj> arrayList)
    { Collections.sort(arrayList, new Comparator<Medtimeobj>() {
        @Override
        public int compare(Medtimeobj o1, Medtimeobj o2) {
            int a, b;
            a = Integer.parseInt(o1.getTime());
            b = Integer.parseInt(o2.getTime());
            return a < b ? -1 : 1;

        }
    });

        Calendar calendar = Calendar.getInstance();
        int hour, min, sec;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        String minstr;
        if (min < 10) {
            minstr = "0" + min;
        } else {
            minstr = Integer.toString(min);
        }
        String Curtimestr = "" + hour + minstr;
        String Nexttimestr = new String();

        for (int i = 0; i < arrayList.size(); i++) {
            int cur, nxt;
            cur = Integer.parseInt(Curtimestr);
            nxt = Integer.parseInt(arrayList.get(i).getTime());
            if (cur >= nxt) {
                continue;
            } else {
                Nexttimestr = "" + nxt;
                Medicinestr=arrayList.get(i).getMedicine();
                count=i;
                break;
            }

        }
        Log.d("TimestarterDBandAPP", "current " + Curtimestr + " nxt " + Nexttimestr);

        curhour = curmin = nxthour = nxtmin = 0;
        diffhour = diffmin = 0;
        if (!Nexttimestr.isEmpty()) {
            if (Nexttimestr.length() == 4) {
                nxthour = (Nexttimestr.charAt(0) - '0') * 10 + (Nexttimestr.charAt(1) - '0');
                nxtmin = (Nexttimestr.charAt(2) - '0') * 10 + (Nexttimestr.charAt(3) - '0');
            }
            if (Nexttimestr.length() == 3) {
                nxthour = (Nexttimestr.charAt(0) - '0');
                nxtmin = (Nexttimestr.charAt(1) - '0') * 10 + (Nexttimestr.charAt(2) - '0');
            }
            if (Curtimestr.length() == 4) {
                curhour = (Curtimestr.charAt(0) - '0') * 10 + (Curtimestr.charAt(1) - '0');
                curmin = (Curtimestr.charAt(2) - '0') * 10 + (Curtimestr.charAt(3) - '0');
            }
            if (Curtimestr.length() == 3) {
                curhour = (Curtimestr.charAt(0) - '0');
                curmin = (Curtimestr.charAt(1) - '0') * 10 + (Curtimestr.charAt(2) - '0');
            }
            if (Nexttimestr.length() == 2) {
                nxthour =0;
                nxtmin = (Nexttimestr.charAt(0) - '0') * 10 + (Nexttimestr.charAt(1) - '0');
            }
            diffmin = (nxtmin - curmin);
            if (diffmin < 0) {
                diffmin = (nxtmin - curmin + 60) * 60 * 1000;
                diffhour = (nxthour - curhour - 1) * 60 * 60 * 1000;
                Log.d("Timetesterlessthnzero", "diffhour " + (diffhour / 3600000) + " diffmin " + (diffmin / 60000), null);
            } else {
                diffhour = (nxthour - curhour) * 60 * 60 * 1000;
                diffmin = (nxtmin - curmin) * 60 * 1000;
                Log.d("Timetestergreaterthnzero", "diffhour " + (diffhour / 3600000) + " diffmin " + (diffmin / 60000), null);
            }
            Log.d("Timetester", "curhour " + curhour + " curmin " + curmin + " nxthour " + nxthour + " nxtmin " + nxtmin);
        }




        for (int i = 0; i < arrayList.size();) {
            int cur, nxt;
            cur = Integer.parseInt(Curtimestr);
            nxt = Integer.parseInt(arrayList.get(i).getTime());
            if (cur >= nxt) {
                arrayList.remove(i);
            } else {
                break;
            }
        }
        for(int i=0;i<arrayList.size()-1;i++)
        {for(int j=i+1;j<arrayList.size();)
        {if(arrayList.get(i).getMedicine().equals(arrayList.get(j).getMedicine())&&arrayList.get(i).getTime().equals(arrayList.get(j).getTime()))
        {arrayList.remove(j);}
        else {j++;}
        }
        }


        return  arrayList;
    }


}
