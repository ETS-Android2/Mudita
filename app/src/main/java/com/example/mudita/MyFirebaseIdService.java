package com.example.mudita;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.mudita.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.IOException;

public class MyFirebaseIdService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(!task.isSuccessful())
                    { }
                    else
                    {
                      String refreshtoken=task.getResult().getToken();
                      updateToken(refreshtoken);
                    }

                }
            });

    }

    private void updateToken(String refreshtoken) {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Token token1=new Token(refreshtoken);
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.usernameshpref),MODE_PRIVATE);
        String username=sharedPreferences.getString("usernamecurr",null);
        FirebaseDatabase.getInstance().getReference().child("Tokens").child(username).setValue(token1);
    }
}
