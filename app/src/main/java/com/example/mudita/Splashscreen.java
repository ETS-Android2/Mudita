package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.Serializable;

public class Splashscreen extends AppCompatActivity {
      private  GoogleSignInClient mGoogleSignInClient;
        private FirebaseAuth firebaseAuth;
        private String usernamestr,profileurl,TAG="fbgoogle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        //NO NEED FOR THESE LINES SINCE WE CAN DIRECTLY USE FIREBASE TO CHECK ALREADY LOGGED IN USER

        /* *//* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(Splashscreen.this,Mainscreen1.class);
                    startActivity(intent);
                    finish();
                }
            },3000);}
        if(account==null)
        {  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splashscreen.this,Welcomescreen.class);
                startActivity(intent);
                finish();
            }
        },3000);
*//*
        }*/

        //USING FIREBASE TO CHECK LOGGED IN USER
        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user !=null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(Splashscreen.this,Mainscreen1.class);
                    if(user.getDisplayName()!=null)
                    {   usernamestr=user.getDisplayName();
                        intent.putExtra("username",usernamestr);
                        Log.d(TAG,"Username"+usernamestr);
                    }
                    else {intent.putExtra("username","noname");}
                    if(user.getPhotoUrl()!=null)
                    { profileurl=user.getPhotoUrl().toString();
                        intent.putExtra("photourl",profileurl);
                        Log.d(TAG,"photourl: "+profileurl);;
                    }
                    else {intent.putExtra("photourl","nophoto");}
                    startActivity(intent);
                    finish();
                }
            },3000);}
            if(user==null)
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(Splashscreen.this,Welcomescreen.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }








    }


}
