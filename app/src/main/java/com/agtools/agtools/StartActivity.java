package com.agtools.agtools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    FrameLayout dashboardLayout;
    TextView textDate1,textTemp1,textprecipitaion1,textwindspeed1,texthimidity1,textdew1,textcloud1,textsnow1,textwindgust1;
    TextView textDate2;
    TextView textTemp2;
    TextView textprecipitaion2;
    View textwindspeed2;
    TextView texthimidity2;
    TextView textdew2;
    TextView textcloud2;
    TextView textsnow2;
    TextView textwindgust2;
    TextView textDate3,textTemp3,textprecipitaion3,textwindspeed3,texthimidity3,textdew3,textcloud3,textsnow3,textwindgust3;
    TextView textDate4,textTemp4,textprecipitaion4,textwindspeed4,texthimidity4,textdew4,textcloud4,textsnow4,textwindgust4;
    TextView textDate5,textTemp5,textprecipitaion5,textwindspeed5,texthimidity5,textdew5,textcloud5,textsnow5,textwindgust5;
    TextView textDate6,textTemp6,textprecipitaion6,textwindspeed6,texthimidity6,textdew6,textcloud6,textsnow6,textwindgust6;
    TextView textDate7,textTemp7,textprecipitaion7,textwindspeed7,texthimidity7,textdew7,textcloud7,textsnow7,textwindgust7;


    public ArrayList<String> dataarrays, TempArrays, PerfictionArrayList, windSpeedArrayList, humidityList, dewlist, cloudList, SnowFallList, windGustList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button1= (Button) findViewById(R.id.button1);
        dashboardLayout = (FrameLayout) findViewById(R.id.dashboard_widget);
        dataarrays=new ArrayList<>();
        TempArrays=new ArrayList<>();PerfictionArrayList=new ArrayList<>();
        windSpeedArrayList=new ArrayList<>();
        humidityList=new ArrayList<>();
        dewlist=new ArrayList<>();cloudList=new ArrayList<>();SnowFallList=new ArrayList<>();
        windGustList=new ArrayList<>();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,MainPackagesActivity.class);
                startActivity(intent);
            }
        });
        Button button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater.from(StartActivity.this).inflate(R.layout.weather_historiclayout, dashboardLayout,true);

                //historicWeather();
            }


        });
        Button button3= (Button) findViewById(R.id.button3);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://my.ag.tools/agtools/historic/oilprices/"+responce+"/Strawberries?type=gas&subuserType=0";
                NukeSSLCerts.NukeSSLCerts();
                JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",""+response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(StartActivity.this);
                requestQueue.add(jsonArrayRequest);
            }
        });
    }
    /*private void historicWeather() {
        //Dates
        textDate1= (TextView) findViewById(R.id.textDate1);textDate2= (TextView) findViewById(R.id.textDate2);textDate3= (TextView) findViewById(R.id.textDate3);
        textDate4= (TextView) findViewById(R.id.textDate4);textDate5= (TextView) findViewById(R.id.textDate5);
        textDate6= (TextView) findViewById(R.id.textDate6);textDate7= (TextView) findViewById(R.id.textDate7);
        //temperature
        textTemp1= (TextView) findViewById(R.id.texttemp1);textTemp2= (TextView) findViewById(R.id.texttemp2);
        textTemp3= (TextView) findViewById(R.id.texttemp3);textTemp4= (TextView) findViewById(R.id.texttemp4);
        textTemp5= (TextView) findViewById(R.id.texttemp5);textTemp6= (TextView) findViewById(R.id.texttemp6);textTemp7= (TextView) findViewById(R.id.texttemp7);
        //Precipitayion
        textprecipitaion1= (TextView) findViewById(R.id.textpreci1);textprecipitaion2= (TextView) findViewById(R.id.textpreci2);
        textprecipitaion3= (TextView) findViewById(R.id.textpreci3);textprecipitaion4= (TextView) findViewById(R.id.textpreci4);
        textprecipitaion5= (TextView) findViewById(R.id.textpreci5);textprecipitaion6= (TextView) findViewById(R.id.textpreci6);textprecipitaion7= (TextView) findViewById(R.id.textpreci7);
        //windspeed
        textwindspeed1= (TextView) findViewById(R.id.textwindspeed1);textwindspeed2=findViewById(R.id.textwindspeed2);
        textwindspeed3= (TextView) findViewById(R.id.textwindspeed3);textwindspeed4= (TextView) findViewById(R.id.textwindspeed4);
        textwindspeed5= (TextView) findViewById(R.id.textwindspeed5);textwindspeed6= (TextView) findViewById(R.id.textwindspeed6);textwindspeed7= (TextView) findViewById(R.id.textwindspeed7);
        //humididty
        texthimidity1= (TextView) findViewById(R.id.textlivehumidity1);texthimidity2= (TextView) findViewById(R.id.textlivehumidity2);
        texthimidity3= (TextView) findViewById(R.id.textlivehumidity3);texthimidity4= (TextView) findViewById(R.id.textlivehumidity4);
        texthimidity5= (TextView) findViewById(R.id.textlivehumidity5);texthimidity6= (TextView) findViewById(R.id.textlivehumidity6);texthimidity7= (TextView) findViewById(R.id.textlivehumidity7);
        //Dewpoint
        textdew1= (TextView) findViewById(R.id.textlivedewpoint1);textdew2= (TextView) findViewById(R.id.textlivedewpoint2);
        textdew3= (TextView) findViewById(R.id.textlivedewpoint3);textdew4= (TextView) findViewById(R.id.textlivedewpoint4);
        textdew5= (TextView) findViewById(R.id.textlivedewpoint5);textdew6= (TextView) findViewById(R.id.textlivedewpoint6);
        textdew7= (TextView) findViewById(R.id.textlivedewpoint7);
        //cloud
        textcloud1= (TextView) findViewById(R.id.textliveuvIndex1);textcloud2= (TextView) findViewById(R.id.textliveuvIndex2);
        textcloud3= (TextView) findViewById(R.id.textliveuvIndex3);textcloud4= (TextView) findViewById(R.id.textliveuvIndex4);
        textcloud5= (TextView) findViewById(R.id.textliveuvIndex5);textcloud6= (TextView) findViewById(R.id.textliveuvIndex6);textcloud7= (TextView) findViewById(R.id.textliveuvIndex7);
        //snow fall
        textsnow1= (TextView) findViewById(R.id.textsnowfall1);textsnow2= (TextView) findViewById(R.id.textsnowfall2);
        textsnow3= (TextView) findViewById(R.id.textsnowfall3);textsnow4= (TextView) findViewById(R.id.textsnowfall4);
        textsnow5= (TextView) findViewById(R.id.textsnowfall5);textsnow6= (TextView) findViewById(R.id.textsnowfall6);textsnow7= (TextView) findViewById(R.id.textsnowfall7);
        //windgust
        textwindgust1= (TextView) findViewById(R.id.textwindgust1);textwindgust2= (TextView) findViewById(R.id.textwindgust2);textwindgust3= (TextView) findViewById(R.id.textwindgust3);
        textwindgust4= (TextView) findViewById(R.id.textwindgust4);textwindgust5= (TextView) findViewById(R.id.textwindgust5);textwindgust6= (TextView) findViewById(R.id.textwindgust6);
        textwindgust7= (TextView) findViewById(R.id.textwindgust7);
        getHistoric();
    }*/

    private void getHistoric() {
        String Url="https://my.ag.tools/agtools/weather/historic/Strawberries/0/37.7749295/-122.41941550000001/e?startDate=03/09/2018";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("",""+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
}
