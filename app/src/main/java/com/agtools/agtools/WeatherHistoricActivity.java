package com.agtools.agtools;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Adapter.PlacesAdapter;
import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Model.PlacesModal;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import xdroid.toaster.Toaster;

public class WeatherHistoricActivity extends AppCompatActivity {
    public static String ARG_IS_FROM_SPLASH = "is_from_splash";
    RelativeLayout relativesegbutton, relative_resp, relative_exchange, relativeshipping, relativeSearch, relativeHeader, relativeFrame;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

   /* String temptype = "e";
    String tempType = "F";  */
    ImageView imgPlaceClear;
    double n1;
    double n2;
    PlacesModal placesModal;
    List<PlacesModal> placesModalList;
    EditText edittextPlaceName;
    ListView listPlaces;
    String text, placeNameintent, lat, lng;
    String commodity;


    public ArrayList<String> datearrays, temperarturearrays, precipitationarrays, windspeedarryshistoric, humidityhistoricarrays,
            dewpointarrays, cloudarrays, snowarrays,snosumarrays, windgustArrays,summarrays,avgarrays;
    EditText weatherDate;
    String sum="Sum:";
    String Avg="Avg:";
    Button weatherhistoricbutton;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    String wdate, itemuserType;
    String name,idname;
    boolean firstTime;
    Button fav;
    String url=ApiConstants.API.concat("userPreferences");
    JSONArray jsonArray;
    ArrayList<String> myJSONArray = new ArrayList<>();
    TransparentProgressDialog transparentProgressDialog;
    Button backbutton;
    TextView textDayFirst, textDaysecond, textDaythird, textDayfourth, textDayfifth, textDaysixth, textDayseventh;
    TextView tempFirst, tempsecond, tempThird, tempfourth, tempfifth, tempsixth, tempseventh;
    TextView  highlowFirst, highlowsecond, highlowThird, highlowfourth, highlowfifth, highlowsixth, highlowseventh;
    TextView windspeedFirst, windspeedsecond, windspeedThird, windspeedfourth, windspeedfifth, windspeedsixth, windspeedseventh;
    TextView hmdtyFirst, hmdtysecond, hmdtythird, hmdtyfourth, hmdtyfifth, hmdtysixth, hmdtyseventh;
    TextView perfFirst, perfsecond, perfThird, perffourth, perffifth, perfsixth, perfseventh;
    TextView img1, img2, img3, img4, img5, img6, img7;
    TextView snow1,snow2,snow3,snow4,snow5,snow6,snow7,gust1,gust2,gust3,gust4,gust5,gust6,gust7;
    RelativeLayout relativeHeaderWeather;
    String temptype1 ;
    //String tempType1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_historiclayout);
        backbutton=findViewById(R.id.backbutton);
        relativeHeaderWeather=findViewById(R.id.relativeHeaderWeather);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fav=findViewById(R.id.fav);
        transparentProgressDialog=new TransparentProgressDialog(this,R.drawable.sgg);
        final String selctCommidtyName=getIntent().getStringExtra("commodity");
       // final String temptype=getIntent().getStringExtra("temp");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefTemp", 0); //
        final String temptype= pref.getString("tem", null);
        if(temptype.equals("F"))
        {

            temptype1 = "F";
        }
        else {
            temptype1 = "e";


        }
        weatherDate = (EditText) findViewById(R.id.weatherdate);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                weatherDate.setText(dateFormatter.format(newDate.getTime()));
                wdate = weatherDate.getText().toString();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        weatherDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });

//Dates
        textDayFirst = (TextView) findViewById(R.id.textDayFirst);
        textDaysecond = (TextView) findViewById(R.id.textDaysecond);
        textDaythird = (TextView) findViewById(R.id.textDaythird);
        textDayfourth = (TextView) findViewById(R.id.textDayfourth);
        textDayfifth = (TextView) findViewById(R.id.textDayfifth);
        textDaysixth = (TextView) findViewById(R.id.textDaysixth);
        textDayseventh = (TextView) findViewById(R.id.textDayseventh);
        //temp
        tempFirst = (TextView) findViewById(R.id.tempFirst);
        tempsecond = (TextView) findViewById(R.id.tempsecond);
        tempThird = (TextView) findViewById(R.id.tempThird);
        tempfourth = (TextView) findViewById(R.id.tempfourth);
        tempfifth = (TextView) findViewById(R.id.tempfifth);
        tempsixth = (TextView) findViewById(R.id.tempsixth);
        tempseventh = (TextView) findViewById(R.id.tempseventh);
        //per
        highlowFirst = (TextView) findViewById(R.id.highlowFirst);
        highlowsecond = (TextView) findViewById(R.id.highlowsecond);
        highlowThird = (TextView) findViewById(R.id.highlowThird);
        highlowfourth = (TextView) findViewById(R.id.highlowfourth);
        highlowfifth = (TextView) findViewById(R.id.highlowfifth);
        highlowsixth = (TextView) findViewById(R.id.highlowsixth);
        highlowseventh = (TextView) findViewById(R.id.highlowseventh);

        //wind
        windspeedFirst = (TextView) findViewById(R.id.perfFirst);
        windspeedsecond = (TextView) findViewById(R.id.perfsecond);
        windspeedThird = (TextView) findViewById(R.id.perfThird);
        windspeedfourth = (TextView) findViewById(R.id.perffourth);
        windspeedfifth = (TextView) findViewById(R.id.perffifth);
        windspeedsixth = (TextView) findViewById(R.id.perfsixth);
        windspeedseventh = (TextView) findViewById(R.id.perfseventh);

        //hu
        hmdtyFirst = (TextView) findViewById(R.id.hmdtyFirst);
        hmdtysecond = (TextView) findViewById(R.id.hmdtysecond);
        hmdtythird = (TextView) findViewById(R.id.hmdtythird);
        hmdtyfourth = (TextView) findViewById(R.id.hmdtyfourth);
        hmdtyfifth = (TextView) findViewById(R.id.hmdtyfifth);
        hmdtysixth = (TextView) findViewById(R.id.hmdtysixth);
        hmdtyseventh = (TextView) findViewById(R.id.hmdtyseventh);
        //dew
        perfFirst = (TextView) findViewById(R.id.windspeedFirst);
        perfsecond = (TextView) findViewById(R.id.windspeedsecond);
        perfThird = (TextView) findViewById(R.id.windspeedThird);
        perffourth = (TextView) findViewById(R.id.windspeedfourth);
        perffifth = (TextView) findViewById(R.id.windspeedfifth);
        perfsixth = (TextView) findViewById(R.id.windspeedsixth);
        perfseventh = (TextView) findViewById(R.id.windspeedseventh);
//cloud
        img1 = (TextView) findViewById(R.id.img1);
        img2 = (TextView) findViewById(R.id.img2);
        img3 = (TextView) findViewById(R.id.img3);
        img4 = (TextView) findViewById(R.id.img4);
        img5 = (TextView) findViewById(R.id.img5);
        img6 = (TextView) findViewById(R.id.img6);
        img7 = (TextView) findViewById(R.id.img7);
        //snow
        snow1=findViewById(R.id.snow1);
        snow2=findViewById(R.id.snow2);
        snow3=findViewById(R.id.snow3);
        snow4=findViewById(R.id.snow4);
        snow5=findViewById(R.id.snow5);
        snow6=findViewById(R.id.snow6);
        snow7=findViewById(R.id.snow7);

        gust1=findViewById(R.id.gust1);
        gust2=findViewById(R.id.gust2);
        gust3=findViewById(R.id.gust3);
        gust4=findViewById(R.id.gust4);
        gust5=findViewById(R.id.gust5);
        gust6=findViewById(R.id.gust6);
        gust7=findViewById(R.id.gust7);


        listPlaces = (ListView) findViewById(R.id.listPlaces);
        edittextPlaceName = (EditText) findViewById(R.id.edittextPlaceName);
        edittextPlaceName.setText("Oxnard, CA, USA");
        //imgPlaceClear = (ImageView) findViewById(R.id.imgPlaceClear);
        text = edittextPlaceName.getText().toString().toLowerCase(Locale.getDefault());
        weatherDate.setText("04/07/2018");
        wdate = weatherDate.getText().toString();
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(text, 50);
            for (Address add : adresses) {
                double lng = add.getLongitude();
                double lat = add.getLatitude();
                System.out.println("latitude...." + lat);
                System.out.println("longitude......" + lng);
                // callWeatherRequest(lat, lng);
                SharedPreferences.Editor editor = getSharedPreferences("Weather", MODE_PRIVATE).edit();
                editor.putString("lat", String.valueOf(lat));
                editor.putString("lng", String.valueOf(lng));
                editor.apply();
                SharedPreferences prefs = getSharedPreferences("Weather", MODE_PRIVATE);
                name = prefs.getString("lat", "");//"No name defined" is the default value.
                idname = prefs.getString("lng", ""); //0 is the default value.


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getHistoric();
        placesModalList = new ArrayList<>();
        Intent i = getIntent();
        placeNameintent = i.getStringExtra("placeName");
        commodity=i.getStringExtra("commodity");

        if (placeNameintent != null && !placeNameintent.equals("")) {
            edittextPlaceName.setText(placeNameintent);
            weatherDate.setText(wdate);
            Geocoder coder1 = new Geocoder(this);
            try {
                ArrayList<Address> adresses = (ArrayList<Address>) coder1.getFromLocationName(placeNameintent, 50);
                for (Address add : adresses) {
                    double lng = add.getLongitude();
                    double lat = add.getLatitude();
                    System.out.println("latitude...." + lat);
                    System.out.println("longitude......" + lng);
                    // callWeatherRequest(lat, lng);
                    SharedPreferences.Editor editor = getSharedPreferences("Weather", MODE_PRIVATE).edit();
                    editor.putString("lat", String.valueOf(lat));
                    editor.putString("lng", String.valueOf(lng));
                    editor.apply();
                    SharedPreferences prefs = getSharedPreferences("Weather", MODE_PRIVATE);
                    name = prefs.getString("lat", "");//"No name defined" is the default value.
                    idname = prefs.getString("lng", ""); //0 is the default value.


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }
        edittextPlaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                weatherDate.setText(wdate);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                placeNameintent = edittextPlaceName.getText().toString().toLowerCase(Locale.getDefault());
                weatherDate.setText(wdate);

                if (placeNameintent != null && !placeNameintent.equals("")) {
                    // edittextPlaceName.setText(text);
                    //relativeHeaderWeather.setVisibility(View.VISIBLE);
                    weatherDate.setText(wdate);
                    Geocoder coder1 = new Geocoder(getApplicationContext());
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder1.getFromLocationName(placeNameintent, 50);
                        for (Address add : adresses) {
                            double lng = add.getLongitude();
                            double lat = add.getLatitude();
                            System.out.println("latitude...." + lat);
                            System.out.println("longitude......" + lng);
                            // callWeatherRequest(lat, lng);
                            SharedPreferences.Editor editor = getSharedPreferences("Weather", MODE_PRIVATE).edit();
                            editor.putString("lat", String.valueOf(lat));
                            editor.putString("lng", String.valueOf(lng));
                            editor.apply();
                            SharedPreferences prefs = getSharedPreferences("Weather", MODE_PRIVATE);
                            name = prefs.getString("lat", "");//"No name defined" is the default value.
                            idname = prefs.getString("lng", ""); //0 is the default value.


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                callGoogleService(placeNameintent);
                //listPlaces.setVisibility(View.INVISIBLE);
            }
        });
        weatherhistoricbutton = (Button) findViewById(R.id.weatherhistoricbutton);
        datearrays = new ArrayList<>();
        temperarturearrays = new ArrayList<>();
        precipitationarrays = new ArrayList<>();
        windspeedarryshistoric = new ArrayList<>();
        humidityhistoricarrays = new ArrayList<>();
        dewpointarrays = new ArrayList<>();
        cloudarrays = new ArrayList<>();
        snowarrays = new ArrayList<>();
        snosumarrays=new ArrayList<>();
        windgustArrays = new ArrayList<>();
        summarrays=new ArrayList<>();
        avgarrays=new ArrayList<>();
        weatherhistoricbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHistoric();
            }
        });
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder coder = new Geocoder(getApplicationContext());
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(text, 50);
                    for (Address add : adresses) {
                        n1 = add.getLongitude();
                        n2 = add.getLatitude();
                        System.out.println("latitude...." + n1);
                        System.out.println("longitude......" + n2);

                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        editor = sharedpreferences.edit();
                        editor.putString(latitude, String.valueOf(n1));
                        editor.putString(longitude, String.valueOf(n2));
                        editor.commit();
                        //callWeatherRequest(n1, lng);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // Map<String, String> params = new HashMap<String, String>();
                JSONObject params=new JSONObject();

                try {
                    params.put("widgetId","5" );
                    params.put("commodityName",selctCommidtyName );
                    params.put("userId", responce);
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put("city",placeNameintent);
                    jsonObject1.put("latitude",n1);
                    jsonObject1.put("longitude",n2);
                    JSONObject jsonObject2=new JSONObject();
                    //String jsonText = jsonObject1.toJSONString();
                    jsonObject2.put("paramValue",jsonObject1.toString());
                    jsonObject2.put("paramKey","selectedStation");
                    Log.d("",""+jsonObject2);
                    JSONObject jsonObject3=new JSONObject();
                    jsonObject3.put("paramKey","date");
                    jsonObject3.put("paramValue","2018-04-07T04:46:07.024Z");
                    jsonArray=new JSONArray();
                    jsonArray.put(jsonObject3);
                    jsonArray.put(jsonObject2);
                    Log.d("js","js"+jsonArray);
                    params.put("options", jsonArray);
                    Log.d("hi","hi"+params);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //pd.show();
                NukeSSLCerts.NukeSSLCerts();
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ApiConstants.Fav_Url, params, null, null){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        //pd.dismiss();
                       // mStatusCode = response.statusCode;
                        Log.d("",""+ response.statusCode);
                        try {
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Log.d("",""+json);
                            if(json.equals(""))
                            {
                                Toaster.toast("Filter Saved");
                               // pd.dismiss();
                            }
                            else {
                                Toaster.toast("Filter Not Saved");
                               // pd.dismiss();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        return super.parseNetworkResponse(response);
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    public void callGoogleService(String placeName) {
        weatherDate.setText(wdate);
        String places_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + placeName + "&components=country:us&key=AIzaSyAWhk74bW6pqjgd0iEeHqxdNX4uxDywIvI";
        System.out.println("place constructed url...." + places_url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(places_url, new JSONObject(callGetParams()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    placesParseResp(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(9000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void placesParseResp(JSONObject response) throws JSONException {
        placesModalList.clear();
        String placesResp = response.toString();
        System.out.println("google places responseeeeeeee" + placesResp);
        JSONObject jsonObject = new JSONObject(placesResp);
        JSONArray predsJsonArray = jsonObject.getJSONArray("predictions");
        for (int i = 0; i < predsJsonArray.length(); i++) {
            String allPlaces = predsJsonArray.getJSONObject(i).getString("description");
            System.out.println("all placesss.." + allPlaces);
            placesModal = new PlacesModal(allPlaces);
            placesModalList.add(placesModal);
        }
        PlaceAdapterWeather placesAdapter = new PlaceAdapterWeather(WeatherHistoricActivity.this, placesModalList);
        listPlaces.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();
    }

    public Map<String, String> callGetParams() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    private void getHistoric() {
        transparentProgressDialog.show();
        String Url = ApiConstants.API.concat("weather/historic/"+commodity+"/0/" + name + "/" + idname + "/e?startDate=" + wdate + "");
        NukeSSLCerts.NukeSSLCerts();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("", "" + response);
                transparentProgressDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String datval = jsonObject.getString("weatherDate");
                        datearrays.add(datval);
                        Log.d("hm", "hm" + datearrays);
                        String jsonObject1 = jsonObject.getString("surfaceTemperatureFahrenheit");
                        Log.d("hm", "hm" + jsonObject1);
                        JSONObject jsonObject2 = new JSONObject(jsonObject1);
                        String temp = jsonObject2.getString("minMax");
                        temperarturearrays.add(temp);
                        Log.d("hmt", "hmt" + temperarturearrays);
                        String precipitation = jsonObject.getString("precipitationPreviousHourInches");
                        JSONObject jsonObject3 = new JSONObject(precipitation);
                        String pr = jsonObject3.getString("minMax");
                        String sum=jsonObject3.getString("sum");
                        summarrays.add(sum);
                        precipitationarrays.add(pr);


                        String windspeed = jsonObject.getString("windSpeedMph");
                        JSONObject jsonObject4 = new JSONObject(windspeed);
                        String wind = jsonObject4.getString("minMax");
                        windspeedarryshistoric.add(wind);


                        String humidity = jsonObject.getString("relativeHumidityPercent");
                        JSONObject jsonObject5 = new JSONObject(humidity);
                        String hm = jsonObject5.getString("minMax");
                        humidityhistoricarrays.add(hm);


                        String dew = jsonObject.getString("surfaceDewpointTemperatureFahrenheit");
                        JSONObject jsonObject6 = new JSONObject(dew);
                        String point = jsonObject6.getString("minMax");
                        dewpointarrays.add(point);


                        String coud = jsonObject.getString("cloudCoveragePercent");
                        JSONObject jsonObject7 = new JSONObject(coud);
                        String c = jsonObject7.getString("minMax");
                        cloudarrays.add(c);


                        String snow = jsonObject.getString("snowfallInches");
                        JSONObject jsonObject8 = new JSONObject(snow);
                        String fall = jsonObject8.getString("minMax");
                        String sumsnow=jsonObject8.getString("sum");
                        snowarrays.add(fall);
                        snosumarrays.add(sumsnow);

                        String wg = jsonObject.getString("surfaceWindGustsMph");
                        JSONObject jsonObject9 = new JSONObject(wg);
                        String g = jsonObject9.getString("minMax");
                        String avg=jsonObject9.getString("avg");
                        windgustArrays.add(g);
                        avgarrays.add(avg);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                textDayFirst.setText(datearrays.get(0));
                textDaysecond.setText(datearrays.get(1));
                textDaythird.setText(datearrays.get(2));
                textDayfourth.setText(datearrays.get(3));
                textDayfifth.setText(datearrays.get(4));
                textDaysixth.setText(datearrays.get(5));
                textDayseventh.setText(datearrays.get(6));
                if(temperarturearrays.size()>0)
                {
                    tempFirst.setText(temperarturearrays.get(0).concat(temptype1));
                    tempsecond.setText(temperarturearrays.get(1).concat(temptype1));
                    tempThird.setText(temperarturearrays.get(2).concat(temptype1));
                    tempfourth.setText(temperarturearrays.get(3).concat(temptype1));
                    tempfifth.setText(temperarturearrays.get(4).concat(temptype1));
                    tempsixth.setText(temperarturearrays.get(5).concat(temptype1));
                    tempseventh.setText(temperarturearrays.get(6).concat(temptype1));
                }



                //per
                highlowFirst.setText(precipitationarrays.get(0).concat(",").concat(sum).concat(summarrays.get(0)));
                highlowsecond.setText(precipitationarrays.get(1).concat(",").concat(sum).concat(summarrays.get(1)));
                highlowThird.setText(precipitationarrays.get(2).concat(",").concat(sum).concat(summarrays.get(2)));
                highlowfourth.setText(precipitationarrays.get(3).concat(",").concat(sum).concat(summarrays.get(3)));
                highlowfifth.setText(precipitationarrays.get(4).concat(",").concat(sum).concat(summarrays.get(4)));
                highlowsixth.setText(precipitationarrays.get(5).concat(",").concat(sum).concat(summarrays.get(5)));
                highlowseventh.setText(precipitationarrays.get(6).concat(",").concat(sum).concat(summarrays.get(6)));
//wind
                windspeedFirst.setText(windspeedarryshistoric.get(0));
                windspeedsecond.setText(windspeedarryshistoric.get(1));
                windspeedThird.setText(windspeedarryshistoric.get(2));
                windspeedfourth.setText(windspeedarryshistoric.get(3));
                windspeedfifth.setText(windspeedarryshistoric.get(4));
                windspeedsixth.setText(windspeedarryshistoric.get(5));
                windspeedseventh.setText(windspeedarryshistoric.get(6));

                hmdtyFirst.setText(humidityhistoricarrays.get(0));
                hmdtysecond.setText(humidityhistoricarrays.get(1));
                hmdtythird.setText(humidityhistoricarrays.get(2));
                hmdtyfourth.setText(humidityhistoricarrays.get(3));
                hmdtyfifth.setText(humidityhistoricarrays.get(4));
                hmdtysixth.setText(humidityhistoricarrays.get(5));
                hmdtyseventh.setText(humidityhistoricarrays.get(6));


                perfFirst.setText(dewpointarrays.get(0));
                perfsecond.setText(dewpointarrays.get(1));
                perfThird.setText(dewpointarrays.get(2));
                perffourth.setText(dewpointarrays.get(3));
                perffifth.setText(dewpointarrays.get(4));
                perfsixth.setText(dewpointarrays.get(5));
                perfseventh.setText(dewpointarrays.get(6));

                //clod
                img1.setText(cloudarrays.get(0));
                img2.setText(cloudarrays.get(1));
                img3.setText(cloudarrays.get(2));
                img4.setText(cloudarrays.get(3));
                img5.setText(cloudarrays.get(4));
                img6.setText(cloudarrays.get(5));
                img7.setText(cloudarrays.get(6));

                snow1.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(0)));
                snow2.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(1)));
                snow3.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(2)));
                snow4.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(3)));
                snow5.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(4)));
                snow6.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(5)));
                snow7.setText(snowarrays.get(0).concat(",").concat(sum).concat(snosumarrays.get(6)));

                gust1.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(0)));
                gust2.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(1)));
                gust3.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(2)));
                gust4.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(3)));
                gust5.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(4)));
                gust6.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(5)));
                gust7.setText(windgustArrays.get(0).concat(",").concat(Avg).concat(avgarrays.get(6)));

                datearrays.clear();
                temperarturearrays.clear();
                precipitationarrays.clear();
                windgustArrays.clear();
                windspeedarryshistoric.clear();
                snowarrays.clear();
                cloudarrays.clear();
                humidityhistoricarrays.clear();
                dewpointarrays.clear();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "" + error);
                transparentProgressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
