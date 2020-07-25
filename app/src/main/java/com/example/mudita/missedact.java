package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class missedact extends AppCompatActivity {
    private ListView listView;
    private  ArrayList<String> Medname=new ArrayList<>();
    private  ArrayList<String> times=new ArrayList<>();
    private  String[] Mednamearr;
    private  String[] timesarr;
    private FirebaseDatabase databaseroot;
    private FirebaseAuth auth;
    private String userid;



    private Context context;
    private  DatabaseReference user,medicinex491;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missedact);
        setContext(this);

        listView = (ListView) findViewById(R.id.missedlistview);
        databaseroot=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        userid=auth.getCurrentUser().getUid(); //String
        user=databaseroot.getReference("user").child(userid);
        medicinex491=user.child("MedicineX_491");
        medicinex491.addListenerForSingleValueEvent(new ValueEventListener() {
            int num,missednum;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Medname.clear();
                times.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                { missednum=Integer.parseInt(ds.child("Missed").getValue().toString());
                 if(missednum>0)
                 {Medname.add(ds.getKey());
                   times.add(String.valueOf(missednum));
                 }
                }
                Log.d("missednumcheck"," "+missednum,null);
                num=Medname.size();
                Mednamearr=new String[num];
                timesarr=new String[num];
                for(int i=0;i<Medname.size();i++)
                {Mednamearr[i]=Medname.get(i);
                timesarr[i]=times.get(i);
                }
                Myadapter2 myadapter2 = new Myadapter2(getContext(), Mednamearr, timesarr);
                if(num==0)
                {  myadapter2.getView(0, null, listView);}
                else
                {myadapter2.getView(num-1, null, listView);}
                listView.setAdapter(myadapter2);


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

