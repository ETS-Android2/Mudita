package com.example.mudita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mudita.ui.Myadapter;


public class statistics extends AppCompatActivity {
    private ListView listView;
    private ProgressBar Circularbar;
   private String name[] ={"Azithrol-500","Liv-52","Rosiflex-C","Montair-500","Isotret-20"};
   private  int arr[]={92,67,88,36,80};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Circularbar=(ProgressBar)findViewById(R.id.progressBarcircular);
        listView=(ListView)findViewById(R.id.listviewstats);
        Myadapter myadapter = new Myadapter(this,name,arr);
        myadapter.getView(4,null,listView);
        listView.setAdapter(myadapter);


    }
}
