package com.example.mudita;

import android.app.Application;

import java.util.ArrayList;
//I extended Diskpersis... class instead of Application class as we can add only one class name in android:name in manifest file.
//That's why i have used multiple inheritance(Appli..->Diskpersi..->Globalvar... classes).
public class GlobalVariables extends DiskpersistenceHelper {
    private ArrayList<String> arrayList;
    private ArrayList<Medtimeobj> medtimeobjs;
    private String string;

    public GlobalVariables() {
    }



    public ArrayList<String> getArrayList() {
        if(arrayList==null||arrayList.isEmpty())
        {return null;}
        else
        {return arrayList;}
    }

    public ArrayList<Medtimeobj> getMedtimeobjs() {
        return medtimeobjs;
    }

    public String getString() {
        return string;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public void setMedtimeobjs(ArrayList<Medtimeobj> medtimeobjs) {
        this.medtimeobjs = medtimeobjs;
    }

    public void setString(String string) {
        this.string = string;
    }
    public  void clearalist() {
        if(arrayList!=null)
        {arrayList.clear();
        }
    }
}
