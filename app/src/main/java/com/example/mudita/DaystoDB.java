package com.example.mudita;



import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class DaystoDB {
    private String medicinename, doses, times, days;
    private final String TAG = "DAYWISECOUNT";
    private final String TAG1 = "DAYS";
    private final String TAG2 = "TIME";
    private FirebaseDatabase databaseroot;
    private DatabaseReference reference;
    private String userid;
    private FirebaseAuth auth;
    private ArrayList<String> medtime=new ArrayList<String>();


    private int arr[] = {0, 0, 0, 0, 0, 0, 0};
    private boolean bool[] = {false, false, false, false, false, false, false};

    DaystoDB(String medicinename, String doses, String days, String times) {
        this.medicinename = medicinename;
        this.doses = doses;
        this.times = times; //24hours format
        this.days = days;
    }

    int ReadyDB() {
        int i, dose, p, day, time, currentmin, currenthour;
        char c[] = new char[3];
        String stemp;
        StringBuilder str = new StringBuilder();
        str.append(days);
        Calendar calendar = Calendar.getInstance();
        int aajkadin = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String currenttimestr, lasttimestr;
        currenthour = calendar.get(Calendar.HOUR_OF_DAY);
        currentmin = calendar.get(Calendar.MINUTE);
//        ampm = "am";
       /* if (currenthour > 12) {
            currenthour = currenthour - 12;
            ampm = "pm";
        }*/
        if (currentmin > 9) {
            currenttimestr = "" + currenthour + currentmin;
            Log.d(TAG2, "" + currenttimestr, null);
        } else {
            currenttimestr = currenthour + "0" + currentmin;
            Log.d(TAG2, "" + currenttimestr, null);
        }


        //days
        for (i = 0; i < str.length(); i++) {
            stemp = "";
            while (i < str.length() && str.charAt(i) != ',') {
                c[0] = str.charAt(i);
                c[1] = str.charAt(i + 1);
                c[2] = str.charAt(i + 2);
                i = i + 3;
                stemp = "" + c[0] + c[1] + c[2];
            }
            Log.d(TAG1, "" + stemp, null);
            if (stemp.compareTo("Sun") == 0) {
                bool[0] = true;
            }
            if (stemp.compareTo("Mon") == 0) {
                bool[1] = true;
            }
            if (stemp.compareTo("Tue") == 0) {
                bool[2] = true;
            }
            if (stemp.compareTo("Wed") == 0) {
                bool[3] = true;
            }
            if (stemp.compareTo("Thu") == 0) {
                bool[4] = true;
            }
            if (stemp.compareTo("Fri") == 0) {
                bool[5] = true;
            }
            if (stemp.compareTo("Sat") == 0) {
                bool[6] = true;
            }
        }
        str.setLength(0);
        str.append(times);
        Log.d("Check1", "" + str, null);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (i = 0; i < str.length(); i++) {
            stemp = "";
            while ((i < str.length()) && (str.charAt(i) != ',')) {
                stemp = stemp + str.toString().charAt(i);
                i++;
            }
            arrayList.add(stemp);
            medtime.add(stemp);
        }
        Collections.sort(arrayList);
        lasttimestr = arrayList.get(arrayList.size() - 1);
        dose = Integer.parseInt(doses);
        int ctime, ltime;
        ctime = Integer.parseInt(currenttimestr);
        ltime = Integer.parseInt(lasttimestr);
        if (ltime > ctime) {
            int correction = 0;
            int numtime=medtime.size();
            int tempdose=dose;
            for (i = 0; i < tempdose; ) {

                if (bool[(i + aajkadin + correction) % 7] == true) {
                    if(tempdose<numtime)
                    { arr[(i + aajkadin + correction) % 7]+=(tempdose);
                       tempdose=0;
                    correction++;}
                    else
                    {arr[(i + aajkadin + correction) % 7]+=numtime;
                    tempdose=tempdose-numtime;
                    correction++;}
                } else {
                    correction++;
                }
            }
        } else {
            int correction = 0;
            int numtime=medtime.size();
            int tempdose=dose;
            for (i = 0; i <tempdose; ) {

                if (bool[(i + aajkadin + 1 + correction) % 7] == true) {
                    if(tempdose<numtime) {
                        arr[(i + aajkadin + 1 + correction) % 7] +=tempdose;
                        tempdose=0;
                    correction++;}
                    else
                    {
                        arr[(i + aajkadin + 1 + correction) % 7] +=numtime;
                      tempdose=tempdose-numtime;
                    correction++;}
                } else {
                    correction++;
                }
            }
        }


        Log.d(TAG, "" + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6], null);

        return 1;
    }

    int UpdateDBdays() {
        databaseroot = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();
        reference = databaseroot.getReference("user");
        DatabaseReference refuser=reference.child(userid);
        /*FirebaseDatabase.getInstance().getReference("user").child(userid)*/
        DatabaseReference refsun=refuser.child("Sunday");
        DatabaseReference refmon=refuser.child("Monday");
        DatabaseReference reftue=refuser.child("Tuesday");
        DatabaseReference refwed=refuser.child("Wednesday");
        DatabaseReference refthu=refuser.child("Thursday");
        DatabaseReference reffri=refuser.child("Friday");
        DatabaseReference refsat=refuser.child("Saturday");
        String Medicinenamestr="";
        Medicinenamestr=Medicinenamestr+medicinename;
       Collections.sort(medtime);


        for (int i = 0; i < 7; i++) {
            if (arr[i] > 0) {
                if (i == 0) {
                    DatabaseReference medref=refsun.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                    medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 1) {
                    DatabaseReference medref=refmon.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 2) {
                    DatabaseReference medref=reftue.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 3) {
                    DatabaseReference medref=refwed.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 4) {
                    DatabaseReference medref=refthu.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 5) {
                    DatabaseReference medref=reffri.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }
                else if (i == 6) {
                    DatabaseReference medref=refsat.child(Medicinenamestr);
                    medref.child("Totalcount").setValue(Integer.toString(arr[i]));
                    medref.child("Perdaycount").setValue(Integer.toString(medtime.size()));
                    for(int j=0;j<medtime.size();j++)
                    {String s="Time"+(j+1);
                        medref.child(s).setValue(medtime.get(j));

                    }
                }


            }

        }


    return 1;}
}
