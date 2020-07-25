package com.example.mudita;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotif extends AppCompatActivity {
    private String Username, Message, Title;
    private static String friendname, Medicine,Usernamestr;
    private static int REQ;
    private static DatabaseReference reference;


    private static APIclient clientapiservice;

    public static void Sendnotificationfbase(Context context,int req) {
        clientapiservice = Client.getclient("https://fcm.googleapis.com/").create(APIclient.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.usernameshpref), MODE_PRIVATE);
        String username = sharedPreferences.getString("usernamecurr", null);
        friendname = sharedPreferences.getString("Friendname", null);
        Medicine = sharedPreferences.getString("Medicine", null);
        Log.d("Sendnotif", "friendname" + friendname, null);
         Usernamestr=username;
        if (friendname != null) {
            Log.d("Sendnotif", "friendname_notnull" + friendname, null);
           reference = FirebaseDatabase.getInstance().getReference("Tokens");
          REQ=req;

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String friendtoken="";
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getKey().equals(friendname)) {
                            friendtoken = ds.child("token").getValue().toString().trim();


                            Log.d("Sendnotif", "friendname" + friendname + " friendtoken " + friendtoken, null);
                        } else {//do nothing
                        }
                    }
                    if(REQ==5001)
                    { SendnotificationsSN(friendtoken, "Mudita Notification Services", ""+Usernamestr+" added you as his friend");}
                    else
                    {SendnotificationsSN(friendtoken, "Mudita Notification Services", "Time for "+Usernamestr+"'s "+Medicine+" medicine");}

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    private static void SendnotificationsSN(String friendtoken, String title, String message) {
        Data data = new Data(title, message);
         NotificationSender sender = new NotificationSender(data,
                friendtoken);
         final NotificationSender SENDER= sender;
                 Log.d("Sending", "outside try catch checking Client Service: "+ clientapiservice.toString());

                 if(clientapiservice==null)
              { Log.d("Sending", "Client Service null :"+clientapiservice.toString());}

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clientapiservice.sendNotifcation(SENDER).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("clientapiservice","Exception "+e,null);
                    }

                }
            });
        thread.start();


          /*  clientapiservice.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
                @Override
                public void onResponse(Call<MyResponse> call,
                                       Response<MyResponse> response) {
                    Log.d("Responsecode", ""+response+"code body "+response.code()+" "+response.body());

                    if (response.code() == 200) {
                        if (response.body().success != 1) {
                            Log.d("Sending", "Message Sending Failed");

                        }
                    }
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                    Log.d("Sending", "Message Sending Failed(On Failure)");

                }
            });*/




    }
}



