package com.example.mudita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mudita.DaystoDB;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;

import java.util.ArrayList;
import java.util.Collections;


public class addactivity extends AppCompatActivity implements weekdays.WeekdaysListener,settimedialog.Settimeinterface{
     private TextView setdays,settime,donetxt;
     private String Str;
     private StringBuilder stringBuilder;
    private String[] day={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    private ImageView maindonebuttonIV;
    private EditText medicinename,doses;
    private  String medicinenamestr,dosestr,settimestr,setdaystr,settimestr24;
    StringBuilder stringBuilder2=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
      setdays=findViewById(R.id.addactcapule3);
      settime=findViewById(R.id.addactcapule4);
      maindonebuttonIV=findViewById(R.id.doneview);
      medicinename=findViewById(R.id.addactcapule1);
      doses=findViewById(R.id.addactcapule2);
      setdays.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openDialog();
          }
      });
    settime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           openDialog1();
        }
    });
    maindonebuttonIV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            medicinenamestr=medicinename.getText().toString();
            dosestr=doses.getText().toString();
           settimestr= settime.getText().toString();
           setdaystr=setdays.getText().toString();

           if(TextUtils.isEmpty(medicinenamestr)||TextUtils.isEmpty(dosestr)||TextUtils.isEmpty(settimestr)||TextUtils.isEmpty(setdaystr))
           {
               Toast.makeText(addactivity.this,"Please fill all the Fields",Toast.LENGTH_LONG).show();
           }
           else
           {
               DaystoDB daystoDB=new DaystoDB(medicinenamestr,dosestr,setdaystr,settimestr24);
               int r=daystoDB.ReadyDB();
               if(r==1)
               {daystoDB.UpdateDBdays();}
           }


        }
    });
    donetxt=findViewById(R.id.donetext);
    donetxt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            medicinenamestr=medicinename.getText().toString();
            dosestr=doses.getText().toString();
            settimestr= settime.getText().toString();
            setdaystr=setdays.getText().toString();

            if(TextUtils.isEmpty(medicinenamestr)||TextUtils.isEmpty(dosestr)||TextUtils.isEmpty(settimestr)||TextUtils.isEmpty(setdaystr))
            {
                Toast.makeText(addactivity.this,"Please fill all the Fields",Toast.LENGTH_LONG).show();
            }
            else
            {  Log.d("settimestr24",""+settimestr24,null);

                DaystoDB daystoDB=new DaystoDB(medicinenamestr,dosestr,setdaystr,settimestr24);
              int r=daystoDB.ReadyDB();
              int rr=0;
                if(r==1)
                {rr=daystoDB.UpdateDBdays();}
                if(r==1&&rr==1)
                {
                    finish();
                }

            }


        }
    });


    }

    private void openDialog1() {
        settimedialog Stimedialog=new settimedialog();
        Stimedialog.show(getSupportFragmentManager(),"settimedialog");
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
            for (int x :A) {
                stringBuilder.append(comma);
                stringBuilder.append(day[x]);
                comma = ",";
            }
        setdays.setText(stringBuilder);}

    }

    @Override
    public void CustomTimeSet(String S[],String S24[]) {
        settime=(TextView)findViewById(R.id.addactcapule4);
        StringBuilder stringBuilder1 = new StringBuilder();

        String comma1 = "";
        for (String y : S)
        {   if(y!=null)
        {stringBuilder1.append(comma1);
            stringBuilder1.append(y);
            comma1 = ",";}
        }
        comma1="";
        stringBuilder2.setLength(0);
        for (String y :S24)
        {if(y!=null) {
            stringBuilder2.append(comma1);
            stringBuilder2.append(y);
            comma1 = ",";
        }
        }
        settimestr24=stringBuilder2.toString();
        settime.setText(stringBuilder1);
        settime.setTextSize(15);


    }
}
