package com.example.mudita;

import android.app.Application;
import android.content.Context;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DBtoPhone {
    private FirebaseDatabase root;
    private DatabaseReference DRuser,DRday,DRmedicine,DRtime;
    private Query querymed,querymedtime;
    private  String userid;
    private FirebaseAuth auth;
    public static  ArrayList<String> alistmedname=new ArrayList<>();
    public static  ArrayList<String> getAlistmedname;
    private Map<String,Object> mainmap;
    public static ArrayList<Medtimeobj> medtimeobjsalist;
   /* private static ArrayList<Medtimeobj> getMedtimeobjsalist=new ArrayList<>();*/
    private  int ass=0;



  public DBtoPhone()
  {}
  public void Retriveperday() {
      try {

          root = FirebaseDatabase.getInstance();
          auth = FirebaseAuth.getInstance();
          userid = auth.getCurrentUser().getUid();
          DRuser = root.getReference("user").child(userid);
          Calendar calendar = Calendar.getInstance();
          int daynum = calendar.get(Calendar.DAY_OF_WEEK) - 1;
          String Today = "";
          if (daynum == 0) {
              Today = "Sunday";
          } else if (daynum == 1) {
              Today = "Monday";
          } else if (daynum == 2) {
              Today = "Tuesday";
          } else if (daynum == 3) {
              Today = "Wednesday";
          } else if (daynum == 4) {
              Today = "Thursday";
          } else if (daynum == 5) {
              Today = "Friday";
          } else if (daynum == 6) {
              Today = "Saturday";
          } else {
              Today = "Noday";
          }
          DRday = DRuser.child(Today);
          querymed = DRday.orderByKey();
          alistmedname = new ArrayList<>();

          querymed.addValueEventListener(new ValueEventListener()
           {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot!=null)
                  {  alistmedname.clear();
                  medtimeobjsalist = new ArrayList<Medtimeobj>();
                  for (DataSnapshot ds : snapshot.getChildren()) {
                      alistmedname.add(ds.getKey());

                      Log.d("Medicine", "" + alistmedname.get(alistmedname.size() - 1), null);
                  if(!medtimeobjsalist.isEmpty()){medtimeobjsalist.clear();}
                  }
                  for (int i = 0; i <alistmedname.size(); i++) {
                      String str = alistmedname.get(i);
                      DatabaseReference DRmed;
                      DRmed = DRday.child(str);
                      querymedtime = DRmed.orderByKey();

                        final int j=i;
                      querymedtime.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              String medplustime = snapshot.getKey();
                              for (DataSnapshot DS : snapshot.getChildren()) {
                                  if (DS.getKey().compareTo("Time1")==0 || DS.getKey().compareTo( "Time2") ==0 || DS.getKey().compareTo( "Time3") == 0 || DS.getKey().compareTo("Time4") == 0 || DS.getKey().compareTo("Time5") == 0) {
                                      if (!medplustime.isEmpty()) {
                                          String p=snapshot.getKey();
                                          medplustime = p + DS.getKey();
                                          Medtimeobj medtimeobj = new Medtimeobj();
                                          medtimeobj.setMedicine(medplustime);
                                          medtimeobj.setTime(DS.getValue().toString());
                                          medtimeobjsalist.add(medtimeobj);
                                          Log.d("m321", "" + medtimeobj.getMedicine()+" "+medtimeobj.getTime(), null);
                                      }

                                  }

                                  Log.d("Chekkey",""+DS.getKey(),null);
                              }


                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {
                              Log.d("m123", "Got Cancelled", null);
                          }
                      });
                  }

                  }



                  Log.d("m123", "hihiii"+alistmedname.size(), null);
              }


              @Override
              public void onCancelled(@NonNull DatabaseError error) {
                  Log.d("Retrive:", "fail", null);

              }
          });




          {Log.d("m123", "hihi"+alistmedname.size(), null);}
          /*  ass = alistmedname.size();*/




      }



      catch (Exception e)
      {
          Log.d("ERROR","Invalid Something"+e,null);
      }
  }
    ArrayList<Medtimeobj> getarrayobj()
    {
        return medtimeobjsalist;
    }




}






