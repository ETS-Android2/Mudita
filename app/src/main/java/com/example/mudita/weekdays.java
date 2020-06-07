package com.example.mudita;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mudita.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class weekdays extends AppCompatDialogFragment implements View.OnClickListener {
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;
    ArrayList<Integer> list;
    WeekdaysListener weekdaysListener;

    TextView addactcap3txt;
    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(),R.style.AlertDialogweekdays);


        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.weekdaysdialog,null);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    weekdaysListener.onDoneclick(list);

                    }
                });
        addactcap3txt=view.findViewById(R.id.addactcapule3);
        cb1=(CheckBox)view.findViewById(R.id.weekcb1);
        cb2=(CheckBox)view.findViewById(R.id.weekcb2);
        cb3=(CheckBox)view.findViewById(R.id.weekcb3);
        cb4=(CheckBox)view.findViewById(R.id.weekcb4);
        cb5=(CheckBox) view.findViewById(R.id.weekcb5);
        cb6=(CheckBox)view.findViewById(R.id.weekcb6);
        cb7=(CheckBox)view.findViewById(R.id.weekcb7);
        cb1.setOnClickListener(this);
        cb2.setOnClickListener(this);
        cb3.setOnClickListener(this);
        cb4.setOnClickListener(this);
        cb5.setOnClickListener(this);
        cb6.setOnClickListener(this);
        cb7.setOnClickListener(this);

        return builder.create();
    }



    @Override
    public void onClick(View v) {
        list=new ArrayList<Integer>();

            if(cb1.isChecked())
                {list.add(0);}
                else {list.remove(Integer.valueOf(0));}

                if (cb2.isChecked()) {
                    list.add(1);
                } else {
                    list.remove(Integer.valueOf(1));
                }


                if (cb3.isChecked()) {
                    list.add(2);
                } else {
                    list.remove(Integer.valueOf(2));
                }

                if (cb4.isChecked()) {
                    list.add(3);
                } else {
                    list.remove(Integer.valueOf(3));
                }

                if (cb5.isChecked()) {
                    list.add(4);
                } else {
                    list.remove(Integer.valueOf(4));
                }

                if (cb6.isChecked()) {
                    list.add(5);
                } else {
                    list.remove(Integer.valueOf(5));
                }

                if (cb7.isChecked()) {
                    list.add(6);
                } else {
                    list.remove(Integer.valueOf(6));
                }

        }


    public interface WeekdaysListener {
        void onDoneclick(ArrayList<Integer> A);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            weekdaysListener=(WeekdaysListener) context;

        }
        catch (ClassCastException e)
        {throw new ClassCastException(context.toString()+" must Implement WeekdaysListener. ");}
    }
}
