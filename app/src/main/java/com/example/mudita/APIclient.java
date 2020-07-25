package com.example.mudita;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIclient {
   @Headers(
                {
                        "Content-Type:application/json",
                        "Authorization:key=AAAADLaLrLI:APA91bFav796G_cYW2bzB-YVN23-P_k3dUNoPZuC8xtEmPNnzKV5VK3ihi4-zPhdx0aBofPQIqPqEVmsFAZpWT96WPia3ACx38VqiqQ4ZgdBeelm5yEX-Z8Z1-qm0o9dl-y6DryxRH_v"
                }
           )
   @POST("fcm/send")
   Call<MyResponse> sendNotifcation(@Body NotificationSender body);

}
