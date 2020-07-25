package com.example.mudita;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mudita.ui.home.HomeFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;

public class Mainscreen1 extends AppCompatActivity {
    private Button Signoutbutton;
    private GoogleSignIn googleSignIn;
    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private String usernamestr,profileurl;
    private static final String TAG="facebook";
    private DBtoPhone dBtoPhone;
    private int delay=1000;
    private int melay=10000;
    private static ArrayList<Medtimeobj> medtimeobjs=new ArrayList<>();
    private  ArrayList<Medtimeobj> arrayList=new ArrayList<>();
    private HomeFragment homeFragment;
    private Medtimeobj obj;
    private static String checkones=new String();


    private AppBarConfiguration mAppBarConfiguration;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        Intent intent1=getIntent();
       usernamestr=intent1.getStringExtra("username");
       profileurl=intent1.getStringExtra("photourl");
        Log.d(TAG,"MainScreen1 Username "+usernamestr);
        Log.d(TAG,"MainScreen1 profileUrl "+profileurl);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        homeFragment=new HomeFragment();
        Bundle bundle=new Bundle();
       bundle.putString("username",usernamestr);
       bundle.putString("photourl",profileurl);
       homeFragment.setArguments(bundle);
       t.add(R.id.nav_host_fragment,homeFragment);
       t.commit();

        /*replace(R.id.container,homeFragment).commit();*/

        //NO NEED TO USE SEPARATE SIGN OUT CHECK SINCE WE CAN SIGN OUT USING FIREBASE

      /*  //FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());
        //GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth = FirebaseAuth.getInstance();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {  FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(Mainscreen1.this,Welcomescreen.class);
                    startActivity(intent);
                    finish();

                }



              /*  if(account!=null) {
                    Toast.makeText(Mainscreen1.this, "Your Are Signed Out", Toast.LENGTH_SHORT).show();
                    mGoogleSignInClient.signOut();
                    Intent intent=new Intent(Mainscreen1.this,Welcomescreen.class);
                    startActivity(intent);
                    finish();


                }
                if(account==null)
                { LoginManager.getInstance().logOut();
                    Intent intent=new Intent(Mainscreen1.this,Welcomescreen.class);
                    startActivity(intent);
                    finish();}*/
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        //DataBase to Mainscreen
       /* dBtoPhone=new DBtoPhone();
        dBtoPhone.Retriveperday();
        Log.d("DBtoPhone",""+dBtoPhone.alistmedname.size(),null);*/



}
         //onresume
         @Override
         public void onResume() {
             super.onResume();


             //DataBase to Mainscreen
             dBtoPhone = new DBtoPhone();
             dBtoPhone.Retriveperday();
             final Handler handler = new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     if (dBtoPhone.medtimeobjsalist != null && !dBtoPhone.medtimeobjsalist.isEmpty()) {
                         if (medtimeobjs.size() != 0) {
                             medtimeobjs.clear();
                         }
                         for (int i = 0; i < dBtoPhone.medtimeobjsalist.size(); i++) {
                             medtimeobjs.add(dBtoPhone.medtimeobjsalist.get(i));
                             Log.d("tuhaikinhi", " " + medtimeobjs.get(medtimeobjs.size() - 1).getMedicine(), null);
                         }

                         homeFragment= (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                         if(homeFragment!=null)
                         { NotificationHelper.createSampleDataNotification(getApplicationContext(),"Reminder","HI,ALL GOOD?",true);
                         //pehle ke set alarms ko hatayega
                        /* AlarmScheduler.removeAlarmsForReminder(getApplicationContext(),arrayList);*/
                          //naya list
                         arrayList=homeFragment.timerstart(medtimeobjs);
                            //check arraylist
                             for(int j=0;j<arrayList.size();j++)
                             {
                                 Log.d("arraylistcheck","j="+j+" "+arrayList.get(j).getMedicine()+" "+arrayList.get(j).getTime(),null);
                             }
                            if(arrayList.size()!=0&&!checkones.equals(arrayList.get(0).getTime()))
                            {   AlarmScheduler.removeAlarmsForReminder(getApplicationContext(),arrayList);
                                AlarmScheduler.Schedulealarmfromdata(getApplicationContext(),arrayList, arrayList.get(0).getDay());
                                //update everday
                                {
                                    ArrayList<Medtimeobj> updatelist = new ArrayList<>();
                                    Medtimeobj ob1=new Medtimeobj();
                                    ob1.setMedicine("Update491");
                                    ob1.setTime("002");
                                    ob1.setDay(arrayList.get(0).getDay());
                                    updatelist.add(ob1);
                                    AlarmScheduler.removeAlarmsForReminder(getApplicationContext(),updatelist);
                                    AlarmScheduler.Schedulealarmfromdata(getApplicationContext(),updatelist, arrayList.get(0).getDay());
                                }
                            checkones=""+arrayList.get(0).getTime();
                            }


                             Log.d("fragmentnull","null nhi he",null);}
                         else
                         { Log.d("fragmentnull","null he..",null);}

                         Log.d("Sizeofmedtime007", "" + medtimeobjs.size(), null);
                     } else if (melay > 0) {
                         melay = melay - delay;

                         handler.postDelayed(this, delay);
                         Log.d("tuhaikinhi", " " + "nhi he intezar kr", null);
                     }

                 }
             }, delay);
            }


        //agar melay<=0 he to internet on karne bolna hoga.(ek alert deke)

    //}

    @Override
    protected void onPostResume() {
        super.onPostResume();


        Log.d("Sizeofmedtime",""+medtimeobjs.size(),null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainscreen1, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
