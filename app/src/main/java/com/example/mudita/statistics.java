package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mudita.ui.Myadapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class statistics extends AppCompatActivity {
    private ListView listView;
    private ProgressBar Circularbar;
    private ArrayList<String> Medname=new ArrayList<>();
    //private ArrayList<String> percent=new ArrayList<>();
    private  ArrayList<Long> perlist=new ArrayList<>();
   private String name[] ;
   private  long perlong[];
    private FirebaseDatabase databaseroot;
    private FirebaseAuth auth;
    private String userid;
    private TextView averagetxt;
    private long averagelong;



    private Context context;
    private DatabaseReference user,medicinex491;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Circularbar=(ProgressBar)findViewById(R.id.progressBarcircular);
        listView=(ListView)findViewById(R.id.listviewstats);
        averagetxt=(TextView)findViewById(R.id.performancenum);



        databaseroot=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        userid=auth.getCurrentUser().getUid(); //String
        user=databaseroot.getReference("user").child(userid);
        medicinex491=user.child("MedicineX_491");
        setContext(this);
        medicinex491.addListenerForSingleValueEvent(new ValueEventListener() {
            long percent,outof,taken,average;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                   outof=Long.parseLong(ds.child("Outof").getValue().toString());
                   if((int)outof==1)
                   {continue;}
                   else
                   {Medname.add(ds.getKey());
                   taken=Long.parseLong(ds.child("Taken").getValue().toString());
                   percent=(((taken-1)*100)/(outof-1));
                   perlist.add(percent);
                   }
                    Log.d("statsticscheck","ABC "/*+Medname.get(Medname.size()-1)*/,null);
                }
                name=new String[Medname.size()];
                perlong= new long[Medname.size()];
                for(int i=0;i<Medname.size();i++)
                {
                    name[i]=Medname.get(i);
                    perlong[i]=perlist.get(i);
                    averagelong+=perlist.get(i);
                }

                Myadapter myadapter = new Myadapter(getContext(),name,perlong);
                if(Medname.size()==0)
                { myadapter.getView(0,null,listView);}
                else
                { myadapter.getView(Medname.size()-1,null,listView);
                }
                if(Medname.size()!=0) {
                    averagelong = (averagelong * 100) / Medname.size();
                    int temp=(int)averagelong;
                    if(temp>100)
                    {temp=temp/100;}
                    Circularbar.setProgress(temp);
                    averagetxt.setText(""+temp);
                }
                else
                {
                    Circularbar.setProgress(0);
                    averagetxt.setText(""+0);
                }
                Medname.clear();
                perlist.clear();
                listView.setAdapter(myadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
