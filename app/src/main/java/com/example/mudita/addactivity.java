package com.example.mudita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class addactivity extends AppCompatActivity implements weekdays.WeekdaysListener{
     TextView setdays;
     String Str;
     StringBuilder stringBuilder;
     String[] day={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
      setdays=findViewById(R.id.addactcapule3);
      setdays.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openDialog();
          }
      });



    }

    private void openDialog() {
        weekdays weekday=new weekdays();
        weekday.show(getSupportFragmentManager(),"weekdayclass");

    }


    @Override
    public void onDoneclick(ArrayList<Integer> A) {
        setdays=(TextView)findViewById(R.id.addactcapule3);
        if(A.size()>0) {
            stringBuilder = new StringBuilder();
            Collections.sort(A);
            String comma = "";
            for (int x : A) {
                stringBuilder.append(comma);
                stringBuilder.append(day[x]);
                comma = ",";
            }
        setdays.setText(stringBuilder);}

    }
}
