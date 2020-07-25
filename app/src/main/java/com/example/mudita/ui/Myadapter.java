package com.example.mudita.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mudita.R;

public class Myadapter extends ArrayAdapter<String> {
    private Context context;
    private long arr[];
    private TextView medicinename;
    private ProgressBar Horizontalbar;
    private String naam[];
   private GradientDrawable gradientDrawable;

    public Myadapter(Context C, String name1[], long arr1[])
    {super(C, R.layout.helperstats,R.id.Medicinename,name1);
     this.context=C;
     this.naam=name1;
     this.arr=arr1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View helper=layoutInflater.inflate(R.layout.helperstats,parent,false);
         medicinename=(TextView)helper.findViewById(R.id.Medicinename);
         Horizontalbar=(ProgressBar)helper.findViewById(R.id.horizontalprogbar);
         if(naam!=null&&naam.length!=0)
         {medicinename.setText(naam[position]+"  "+arr[position]+"%");
          Horizontalbar.setProgress((int) arr[position]);}
          gradientDrawable =new GradientDrawable();
          gradientDrawable.setShape(GradientDrawable.RECTANGLE);
          gradientDrawable.setCornerRadius(50);



          if(position%4==0)
          {gradientDrawable.setColor(Color.parseColor("#4AACDD"));
           helper.setBackground(gradientDrawable);}
          else if(position%4==1)
          {gradientDrawable.setColor(Color.parseColor("#ECF2FF"));
              helper.setBackground(gradientDrawable);}
          else if(position%4==2)
          { gradientDrawable.setColor(Color.parseColor("#4AACDD"));
              helper.setBackground(gradientDrawable);}
          else if(position%4==3)
          { gradientDrawable.setColor(Color.parseColor("#ECF2FF"));
              helper.setBackground(gradientDrawable);}


        return helper;
    }




}
