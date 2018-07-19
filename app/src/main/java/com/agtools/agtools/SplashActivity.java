package com.agtools.agtools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.Model.CommudityResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.agtools.agtools.DashboardActivity.ARG_IS_FROM_SPLASH;

public class SplashActivity extends AppCompatActivity {
    List<CommudityResponse> commudityResponseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        commudityResponseList = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String name=sharedPreferences.getString("Email","");
        Log.d("",""+name);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("key_from_json","");
        Log.d("",""+responce);

        Thread background = new Thread() {

            public void run()
            {
                try
                {
                    sleep(5*1000);
                   // Intent in=new Intent(SplashActivity.this,SigninActivity.class);
                   // startActivity(in);
                   if(name.length()>0)
                    {
                        try {
                            JSONArray jsonArray=new JSONArray(responce);
                            Log.d("",""+responce);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject commudityObject = jsonArray.getJSONObject(i);
                                CommudityResponse commudityResponse = new CommudityResponse(commudityObject.getString("commodityId").toString(),
                                        commudityObject.getString("commodityName").toString(),
                                        commudityObject.getString("commodityType").toString(),
                                        commudityObject.getInt("userId"),
                                        commudityObject.getString("name").toString(),
                                        commudityObject.getInt("usertypeId"),
                                        commudityObject.getInt("subusertypeId"),
                                        commudityObject.getInt("subscriptionId"),
                                        commudityObject.getString("usertype").toString(),
                                        commudityObject.getString("subusertype").toString(),
                                        commudityObject.getString("subscriptionDetails").toString(),
                                        commudityObject.getBoolean("subscriptionStatus"),
                                        commudityObject.getString("enddate").toString(),
                                        commudityObject.getInt("months"),
                                        commudityObject.getInt("packageDetailId"),
                                        commudityObject.getInt("packageId"),
                                        commudityObject.getString("packageName").toString(),
                                        commudityObject.getString("userPkgId").toString(),
                                        commudityObject.getString("id").toString(),
                                        commudityObject.getString("paypal_id").toString()
                                );

                                commudityResponseList.add(commudityResponse);
                                Log.d("",""+commudityResponseList);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //commudities assigning
                        final GlobalData globalData = (GlobalData) getApplicationContext();
                        globalData.setCommudityResponse(commudityResponseList);
                        //Dashboard default widget selection
                        globalData.setDashboardSelection(1);
                            // startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
                            Intent intent=new Intent(SplashActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                   else {
                       Intent in=new Intent(SplashActivity.this,SigninActivity.class);
                       startActivity(in);
                   }
                        }
                        catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    /**/
                    finish();
            }

        };
        background.start();

    }
}
