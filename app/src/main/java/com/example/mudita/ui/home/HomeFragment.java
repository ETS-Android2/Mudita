package com.example.mudita.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mudita.Mainscreen1;
import com.example.mudita.R;
import com.example.mudita.Welcomescreen;
import com.example.mudita.addactivity;
import com.example.mudita.missedact;
import com.example.mudita.statistics;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView tempsignout,profilepic,Trail,missed,add;
    private FirebaseAuth firebaseAuth;
    private LoginManager loginManager;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView texttips,usernametxt;
    private int tipno;
    private String tipstr,usernamestr,profileurl,sr;
    private static final String TAG="facebook";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        //Profile photo and username





        //Tips
        tipno=new Random().nextInt(15);
        tipstr="tip"+tipno;



        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ///final TextView textView = root.findViewById(R.id.text_home);


        //username and profile photo from facebook/
        try{
        usernamestr=getArguments().getString("username");}
        catch (Exception e)
        { Log.d(TAG,"username: "+e);
        usernamestr="noname";}
        try {
            profileurl=getArguments().getString("photourl");
        }
        catch (Exception e)
        {Log.d(TAG,"url: "+e);
        profileurl="nophoto";}
      /*  try{ sr=getParentFragment().toString();
            Log.d(TAG,"Parent Frag"+sr);}
        catch (Exception e)
        {Log.d(TAG,"Parent Frag"+sr);}*/

       usernametxt=(TextView) root.findViewById(R.id.usernamemain);
        profilepic=(ImageView)root.findViewById(R.id.usericon);
       Log.d(TAG,"We are on HomeFrag: "+usernamestr);
        Log.d(TAG,"We are on HomeFrag: "+profileurl);
         if(!usernamestr.equals("noname"))
        { usernametxt.setText(usernamestr);}
        else if(usernamestr==null||usernamestr=="noname") {usernametxt.setText("UserName");}

       if(!profileurl.equals("nophoto"))
       {  profileurl=profileurl+"?type=large";
       Picasso.get().load(profileurl).into(profilepic);}
      else if(profileurl=="nophoto"||profileurl==null) {profilepic.setImageResource(R.drawable.usericon);}

        //Health Tips
        texttips=root.findViewById(R.id.puratip);
        texttips.setText(getResources().getIdentifier(tipstr,"string",getActivity().getPackageName()));

        //add button oncicklistener
        add=(ImageView) root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentadd = new Intent(getActivity(), addactivity.class);
                startActivity(intentadd);
            }
        });


         //Temp Signout
        tempsignout=(ImageView)root.findViewById(R.id.card3);
        tempsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                loginManager=LoginManager.getInstance();



                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {  firebaseAuth.signOut();
                     loginManager.logOut();
                    mGoogleSignInClient.signOut();

                    Intent intent=new Intent(getActivity(),Welcomescreen.class);
                    startActivity(intent);



                }

            }
        });
        missed=(ImageView)root.findViewById(R.id.missed);

        //Statistics onclicklistener
        Trail=(ImageView)root.findViewById(R.id.stats);
        Trail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(), statistics.class);
                startActivity(intent2);

            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        //missed onclicklistener
        missed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getActivity(), missedact.class);
                startActivity(intent3);
            }
        });

        return root;
    }

}
