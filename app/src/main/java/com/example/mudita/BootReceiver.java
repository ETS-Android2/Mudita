package com.example.mudita;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.util.DataUtils;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(context!=null&&intent!=null&intent.getAction()!=null)
        {
            DbandAppCom dbandAppCom=new DbandAppCom();
            dbandAppCom.sendreqdata(context);
        }

    }
}
