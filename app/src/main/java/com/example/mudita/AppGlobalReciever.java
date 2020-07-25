package com.example.mudita;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AppGlobalReciever extends BroadcastReceiver {
    private static final String NOTIFICATION_ID = "notification_id";
    private FirebaseDatabase databaseroot;
    private FirebaseAuth auth;
    private DatabaseReference user,medicinex491,medx;
    private String userid;
    private Intent intent1;
    private Context context1;


    @Override
    public void onReceive(Context context,  Intent intent) {
        if(context!=null&&intent!=null&&intent.getAction()!=null)
        {   if(intent.hasExtra("Actionname")) {
            String actionname = intent.getStringExtra("Actionname");
            setIntent1(intent);
            setcontext1(context);
            Log.d("Appglobalcheck","actionname "+actionname);
            if (intent.getAction().equalsIgnoreCase(context.getString(R.string.action_medicine_administered) + actionname))
            {
                Log.d("insideif","actionname "+actionname,null);
                if(actionname.equals("Done"))
                {Doneaction(context,intent);}
                else if(actionname.equals("Missed"))
                {MissedAction(context,intent);}
                else if(actionname.equals("Cancel"))
                {CancelAction(context,intent);}

            }
        }
        }


    }

    private void CancelAction(Context context,Intent intent) {

        return;
    }

    private void MissedAction(Context context,Intent intent) {
        databaseroot=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        userid=auth.getCurrentUser().getUid(); //String
        user=databaseroot.getReference("user").child(userid);
        medicinex491=user.child("MedicineX_491");
        Bundle bundle=intent.getBundleExtra("Bundle");
        String Medicinestr=""+bundle.get("Medicine").toString();
        medx=medicinex491.child(Medicinestr);
        Query query=medx.orderByKey().equalTo("TotalDoses");

        medx.addListenerForSingleValueEvent(new ValueEventListener() {
            int outof,totaldoses,missed;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent2=getintent1();
                Context context2=getcontext1();

                for(DataSnapshot ds:snapshot.getChildren()) {
                    if (ds.getKey().equals("Outof")) {
                        String outofstr = "" + ds.getValue().toString();
                        outof = Integer.parseInt(outofstr);
                        outof = outof + 1;
                        outofstr = "" + outof;

                        medx.child("Outof").setValue(outofstr);
                        Log.d("Checkdb", "outof " + outofstr);

                    }
                    else if (ds.getKey().equals("Missed")) {
                        String missedstr = "" + ds.getValue().toString();
                        missed = Integer.parseInt(missedstr);
                        missed = missed + 1;
                        missedstr = "" + missed;

                        medx.child("Missed").setValue(missedstr);

                    }
                    else if (ds.getKey().equals("TotalDoses")) {
                        String totaldosestr = "" + ds.getValue().toString();
                        totaldoses = Integer.parseInt(totaldosestr);

                      /*  if (outof > totaldoses) {
                            RemoveMedicine(medx.getKey());
                        }*/
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context2);
                        if (intent2.hasExtra("Random")) {
                            String idstr = intent2.getStringExtra("Random");
                            int random = Integer.parseInt(idstr);
                            notificationManager.cancel(random);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Doneaction(Context context,Intent intent) {
        databaseroot=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        userid=auth.getCurrentUser().getUid(); //String
        user=databaseroot.getReference("user").child(userid);
        medicinex491=user.child("MedicineX_491");
        Bundle bundle=intent.getBundleExtra("Bundle");
        String Medicinestr=""+bundle.get("Medicine").toString();
        medx=medicinex491.child(Medicinestr);
        Query query=medx.orderByKey().equalTo("TotalDoses");
        Log.d("Doneaction",medx.getKey());


        medx.addListenerForSingleValueEvent(new ValueEventListener() {
            int taken,outof,totaldoses;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent2=getintent1();
                Context context2=getcontext1();
                for(DataSnapshot ds:snapshot.getChildren()) {
                    if (ds.getKey().equals("Taken")) {
                        String takenstr = "" + ds.getValue().toString();
                        taken = Integer.parseInt(takenstr);
                        taken = taken + 1;
                        takenstr = "" + taken;
                        medx.child("Taken").setValue(takenstr);
                        Log.d("Checkdb", "taken " + takenstr);
                    } else if (ds.getKey().equals("Outof")) {
                        String outofstr = "" + ds.getValue().toString();
                        outof = Integer.parseInt(outofstr);
                        outof = outof + 1;
                        outofstr = "" + outof;

                        medx.child("Outof").setValue(outofstr);

                    } else if (ds.getKey().equals("TotalDoses")) {
                        String totaldosestr = "" + ds.getValue().toString();
                        totaldoses = Integer.parseInt(totaldosestr);
                        if (taken > totaldoses) {
                            RemoveMedicine(medx.getKey());
                        }
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context2);
                        if (intent2.hasExtra("Random")) {
                            String idstr = intent2.getStringExtra("Random");
                            int random = Integer.parseInt(idstr);
                            notificationManager.cancel(random);
                        }
                    }
                }
                Log.d("DoneSnapshot","" +snapshot.getKey(),null);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }

    private void RemoveMedicine(final String Medicinename) {
        user.child("Sunday").child(Medicinename).removeValue();
        user.child("Monday").child(Medicinename).removeValue();
        user.child("Tuesday").child(Medicinename).removeValue();
       user.child("Wednesday").child(Medicinename).removeValue();
        user.child("Thursday").child(Medicinename).removeValue();
        user.child("Friday").child(Medicinename).removeValue();
         user.child("Saturday").child(Medicinename).removeValue();

    }
    public Intent getintent1()
    {return intent1;}
   public void  setIntent1(Intent intent1)
    {this.intent1=intent1;}
    public Context getcontext1()
    {return context1;}
    public void setcontext1(Context context1)
    {this.context1=context1;}
}
