package com.example.mudita;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class Myadapter3 extends ArrayAdapter<String> {
    Context context;
    TextView settime1;
    TextView time1;
    GradientDrawable gradientDrawable;

    int n;
    Myadapter3(Context C,int N,String S[])
    {super(C,R.layout.settimehelper,R.id.timetextid,S);
      this.context=C;
      this.n=N;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.settimehelper,parent,false);
        settime1=(TextView) view.findViewById(R.id.settimetextid);
        time1=(TextView)view.findViewById(R.id.timetextid);
        int pos=position;
        String str=Integer.toString((pos+1));
        settime1.setText("Set Time "+str);
        time1.setText("Time "+str);
       /* gradientDrawable=new GradientDrawable();*/
        /*time1.setText(Color.parseColor("2C2C36"));*/

        return view;
    }


}
