package com.example.mudita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public  class Myadapter2 extends ArrayAdapter<String> {
     private Context context;
     private String Medname[],times[];
     private TextView medicine,numoftimes;
     Myadapter2(Context C,String[] Medname,String[]times)
     {super(C,R.layout.helpermissed,R.id.missedmed,Medname);
      this.context=C;
      this.Medname=Medname;
      this.times=times;
     }

     @NonNull
     @Override
     public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View view=layoutInflater.inflate(R.layout.helpermissed,parent,false);
         medicine=(TextView)view.findViewById(R.id.missedmed);
         numoftimes=(TextView)view.findViewById(R.id.numbermissed);
         if(Medname.length==0)
         { medicine.setText("Chill!");
             numoftimes.setText("Noting missed yet");}
         else {
             medicine.setText(Medname[position]);
             numoftimes.setText("Missed " + times[position] + " times");
         }
         return view;
     }
 }
