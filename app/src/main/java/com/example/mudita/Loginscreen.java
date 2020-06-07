package com.example.mudita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class Loginscreen extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private LoginButton loginButton;
    private FirebaseAuth auth,muth;
    private CallbackManager mcallbackManager;
    private Button Registerbutton,MainLoginButton;
    private ImageView fbbutton,googlebutton,profilepic;
    private int RC_SIGN_IN=9001;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG="LOGINTAG";
    private  FirebaseAuth.AuthStateListener authStateListener;
    private static int flag;
    private EditText Email,Password;
    private static final String TAG1="facebook";
    private static final String TAG2="google";
   String Email_txt,Password_txt,usernamestr,photourl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        Registerbutton=findViewById(R.id.button2);
        signInButton=findViewById(R.id.googlesignin);
        auth=FirebaseAuth.getInstance();
        MainLoginButton=findViewById(R.id.loginbutton);
        Email=findViewById(R.id.emailenter);
        Password=findViewById(R.id.passwordenter);
        fbbutton=findViewById(R.id.facebooklogo);
        googlebutton=findViewById(R.id.googlelogo);




        //GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       /* if(account!=null)
        { FirebaseGoogleAuth(account);}*/
        //FACEBOOK

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton=findViewById(R.id.facebooksignin);
        mcallbackManager =CallbackManager.Factory.create();
        fbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();

            }
        });

        loginButton.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG,"ONSUCCESS "+loginResult);
                        //flag=4;
                      handlefacebooktoken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG,"ONCANCEL ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                       Log.d(TAG,"ONERROR");
                    }
                });
         //FACEBOOK
      /*  authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    updateUIFB(user);
                }
                else
                {updateUIFB(null);}
            }
        };
        accessTokenTracker =new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null)
                {auth.signOut();}
            }
        };*/

        //GOOGLE
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButton.performClick();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsignin();
            }
        });

        //Made this Button for using Login button as Signout button for temporary purpose

       /*SignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(Loginscreen.this,"Your Are Signed Out",Toast.LENGTH_SHORT).show();
                SignoutButton.setVisibility(View.INVISIBLE);
            }
        });*/

        MainLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email_txt=Email.getText().toString();
                Password_txt=Password.getText().toString();
                auth.signInWithEmailAndPassword(Email_txt,Password_txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {Intent intent=new Intent(Loginscreen.this,Mainscreen1.class);
                        intent.putExtra("username","noname");
                        intent.putExtra("photourl","nophoto");
                         startActivity(intent);
                         finish();

                        }

                        else
                        {String msg =task.getException().getMessage();
                        Toast.makeText(Loginscreen.this,""+msg,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Loginscreen.this,Registerscreen.class);
                startActivity(intent);
            }
        });



    }
    //FACEBOOK FIREBASE AUTHENTICATION
    private  void handlefacebooktoken(AccessToken token)
    {
        AuthCredential authCredential= FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   Toast.makeText(Loginscreen.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser user=auth.getCurrentUser();
                    updateUIFB(user);

                }
                else
                {
                    Toast.makeText(Loginscreen.this,"Login UnSuccessful",Toast.LENGTH_SHORT).show();
                    updateUIFB(null);
                }

            }
        });
    }

    private void onsignin() {
        //flag=3;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }*/

   // @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // Log.d(TAG,"flag="+flag);
        {mcallbackManager.onActivityResult(requestCode, resultCode, data);}
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FirebaseGoogleAuth(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(Loginscreen.this,"Signed in Unsuccessful",Toast.LENGTH_SHORT).show();
                FirebaseGoogleAuth(null);

           }
        }
    }

   /* private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            FirebaseGoogleAuth(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(Loginscreen.this,"Signed in Unsuccessful",Toast.LENGTH_SHORT).show();
           FirebaseGoogleAuth(null);
        }
    }*/

    //GOOLGLE FIREBASE AUTHENTICATION
    private void FirebaseGoogleAuth(GoogleSignInAccount acct)
    {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful())
              {   Toast.makeText(Loginscreen.this,"Login Successful",Toast.LENGTH_SHORT).show();
                  FirebaseUser user=auth.getCurrentUser();
                  updateUI(user);

              }
              else
              {
                  Toast.makeText(Loginscreen.this,"Login UnSuccessful",Toast.LENGTH_SHORT).show();
                  updateUI(null);
              }
            }
        });

    }

    //GOOGLE
    private void updateUI(FirebaseUser fuser)
    { /*SignoutButton.setVisibility(View.VISIBLE);*/
     GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
     if(account!=null)
     {    Intent intent=new Intent(Loginscreen.this,Mainscreen1.class);
         if(fuser.getDisplayName()!=null)
     { usernamestr=fuser.getDisplayName();
         intent.putExtra("username",usernamestr);
         Log.d(TAG2,"username: "+usernamestr);;
     }
         else { intent.putExtra("username","noname");}

         if(fuser.getPhotoUrl()!=null)
         {photourl=fuser.getPhotoUrl().toString();
             intent.putExtra("photourl",photourl);
             Log.d(TAG2,"photourl: "+photourl);;}
         else { intent.putExtra("photourl","nophoto");}

               startActivity(intent);
               finish();
     }
    }
    //FACEBOOK
    private void updateUIFB(FirebaseUser fuser)
    {
        if(fuser!=null)
        {  Intent intent=new Intent(Loginscreen.this,Mainscreen1.class);
            usernamestr=fuser.getDisplayName();
        if(fuser.getPhotoUrl()!=null)
        { photourl=fuser.getPhotoUrl().toString();
            intent.putExtra("photourl",photourl);
            Log.d(TAG1,"photourl: "+photourl);;
        }
        else {intent.putExtra("photourl","nophoto");}
        if(usernamestr!=null)
        { intent.putExtra("username",usernamestr);
        Log.d(TAG1,"username: "+usernamestr);}

            startActivity(intent);
            finish();

        }
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if(flag==4)
        {auth.addAuthStateListener(authStateListener);}
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(flag==4&&authStateListener!=null)
        {auth.removeAuthStateListener(authStateListener);}
    }*/
    /*  public void onsignout()
    {

    }
*/
}
