package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class missedact extends AppCompatActivity {
    private ListView listView;
    private Adapter madapter;
    private String Medname[] = {"Azithrom 500", "Rosiflex 500", "Livsave Syrup", "Drep Ear Drop", "SumoCold 20"};
    private String times[] = {"2", "3", "1", "4", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missedact);
        Myadapter2 myadapter2 = new Myadapter2(this, Medname, times);
        listView = (ListView) findViewById(R.id.missedlistview);
        myadapter2.getView(4, null, listView);
        listView.setAdapter(myadapter2);


    }
}

     class Myadapter2 extends ArrayAdapter<String>{
        private Context context;
        private String Medname[],times[];
        private TextView medicine,numoftimes;
        Myadapter2(Context C,String Medname[],String times[])
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
            medicine.setText(Medname[position]);
            numoftimes.setText("Missed "+times[position]+" times");
            return view;
        }
    }

