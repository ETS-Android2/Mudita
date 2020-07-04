package com.example.mudita;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class settimedialog extends AppCompatDialogFragment {
    ListView listView;
    Myadapter3 myadapter3;
    TimePickerDialog timePickerDialog;
    int mhour,mmin;
    int N=5;
    Settimeinterface settimeinterface;
    ArrayList arrayList;
    TextView settimetxt;
    String[] S=new String[N];
    String[] SS=new String[N];
    String[] S24=new String[N];

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.AlertDialogweekdays);
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.settimedialog,null);


         builder.setView(view);

              builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                {
                       @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Set Time");

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settimeinterface.CustomTimeSet(SS,S24);
                    }
                });






                listView=(ListView) view.findViewById(R.id.settimeid);




        for(int i=0;i<N;i++)
        {S[i]=Integer.toString(N);}
        myadapter3=new Myadapter3(getContext(),4,S);
        myadapter3.getView(4,null,listView);
        listView.setAdapter(myadapter3);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent,View viewww, final int position, long id) {

                int mhour,mmin;
                settimetxt=(TextView)viewww.findViewById(R.id.settimetextid);
                arrayList=new ArrayList<String>();
                Calendar calendar=Calendar.getInstance();
                mhour=calendar.get(Calendar.HOUR_OF_DAY);
                mmin=calendar.get((Calendar.MINUTE));
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
                        String Str="";
                        if(hourOfDay==12&&minute!=0)
                        { if(minute>9){Str=Integer.toString(hourOfDay)+":"+Integer.toString(minute);}
                        else {Str=Integer.toString(hourOfDay)+":"+"0"+Integer.toString(minute);}
                            Str=Str+" pm";}
                        else if(hourOfDay>12)
                        { if(hourOfDay!=24){if(minute>9){Str=Integer.toString(hourOfDay%12)+":"+Integer.toString(minute);}
                        else {Str=Integer.toString(hourOfDay%12)+":"+"0"+Integer.toString(minute);}}
                            else {
                            if(minute>9){Str=Integer.toString(12)+":"+Integer.toString(minute);}
                            else {Str=Integer.toString(12)+":"+"0"+Integer.toString(minute);}
                        }
                            Str=Str+" pm";}
                        else if (hourOfDay==0)
                        {if(minute>9){Str=Integer.toString(hourOfDay+12)+":"+Integer.toString(minute);}
                        else {Str=Integer.toString(hourOfDay+12)+":"+"0"+Integer.toString(minute);}
                            Str=Str+" am";
                        }
                        else if(hourOfDay<12&&hourOfDay!=0)
                        { if(minute>9){Str=Integer.toString(hourOfDay)+":"+Integer.toString(minute);}
                        else {Str=Integer.toString(hourOfDay)+":"+"0"+Integer.toString(minute);}
                            Str=Str+" am";}
                       SS[position]=Str;
                        if(minute>9)
                        {S24[position]=""+hourOfDay+minute;}
                        else if(minute<10)
                        {S24[position]=""+hourOfDay+"0"+minute;}

                        settimetxt.setText(SS[position]);

                    }
                },mhour,mmin,false);
                timePickerDialog.show();}


        });


       return builder.create();}

    /*private void onclickcustom(*//*AdapterView<?> parent, View view, int position, long id*//*) {*/

    public interface Settimeinterface {
        void CustomTimeSet(String S[],String S24[]);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            settimeinterface=(Settimeinterface) context;

        }
        catch (ClassCastException e)
        {throw new ClassCastException(context.toString()+" must Implement Settimeinterface. ");}



    }
}
