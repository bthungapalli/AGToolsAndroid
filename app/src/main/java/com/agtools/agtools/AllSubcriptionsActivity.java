package com.agtools.agtools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.agtools.agtools.Adapter.VolumeAdapter;
import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.Model.CommudityResponse;
import com.agtools.agtools.Model.VolumeResponse;
import com.agtools.agtools.custommessages.CustomToast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agtools.agtools.DashboardActivity.ARG_IS_FROM_SPLASH;

public class AllSubcriptionsActivity extends AppCompatActivity {
    ListView  listViewVolume;
    List<VolumeResponse>  volumeResponseList = new ArrayList<>();
    VolumeResponse volumeResponse,volumeResponse1,volumeResponse2;
    TransparentProgressDialog transparentProgressDialog;
    String sessionid,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subcriptions);
        Button back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("Email", "");
        Log.d("", "" + name);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String password = sharedPreferences1.getString("password", "");
        Log.d("", "" + password);
        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.sgg);
        listViewVolume = (ListView) findViewById(R.id.listViewVolume);
        transparentProgressDialog.show();
        SharedPreferences sharedPreferences4 = getSharedPreferences("MyPref", 0);
        final String finalcheckuser = sharedPreferences4.getString("check", null);
        Log.d("finalcheckuser", "finalcheckuser" + finalcheckuser);
        transparentProgressDialog.dismiss();
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(finalcheckuser);
            JSONObject jsonObject1=jsonObject.getJSONObject("user");
            JSONArray jsonArray=jsonObject1.getJSONArray("userCommodities");
            Log.d("",""+jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    String Commodity = jsonObject.getString("commodityName");
                    String Planning = jsonObject.getString("subusertype");
                    String Package=jsonObject.getString("subscriptionDetails");
                    volumeResponse = new VolumeResponse(Commodity,Planning,Package);
                    volumeResponseList.add(volumeResponse);
                    VolumeAdapter volumeAdapter = new VolumeAdapter(AllSubcriptionsActivity.this, volumeResponseList);
                    listViewVolume.setAdapter(volumeAdapter);
                    volumeAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}