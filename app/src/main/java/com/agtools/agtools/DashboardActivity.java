package com.agtools.agtools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Adapter.CommudityAdapter;
import com.agtools.agtools.Adapter.ExchangeAdapter;
import com.agtools.agtools.Adapter.FreezeAdapter;
import com.agtools.agtools.Adapter.GasAdapter;
import com.agtools.agtools.Adapter.HolidayAdapter;
import com.agtools.agtools.Adapter.JuiceAdapter;
import com.agtools.agtools.Adapter.LiveWeathAdapter1;
import com.agtools.agtools.Adapter.LiveWeathAdapter2;
import com.agtools.agtools.Adapter.LiveWeathAdapter3;
import com.agtools.agtools.Adapter.PlacesAdapter;
import com.agtools.agtools.Adapter.SearchComidityAdapter;
import com.agtools.agtools.Adapter.ShippingAdapter;
import com.agtools.agtools.Adapter.TerminalPriceAdapter;
import com.agtools.agtools.Adapter.VolumeAdapter;
import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.ErrorMessages.ValidationMessages;
import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.Model.CommudityResponse;
import com.agtools.agtools.Model.ExchangeRateModal;
import com.agtools.agtools.Model.FreezeModel;
import com.agtools.agtools.Model.HolidayModal;
import com.agtools.agtools.Model.JuiceModel;
import com.agtools.agtools.Model.PlacesModal;
import com.agtools.agtools.Model.SearchCommidity;
import com.agtools.agtools.Model.ShippingResponse;
import com.agtools.agtools.Model.TerminalPriceResponse;
import com.agtools.agtools.Model.VolumeResponse;
import com.agtools.agtools.Model.WeatherResponse;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;
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
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import xdroid.toaster.Toaster;

import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY1;
import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY2;
import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY3;

public class DashboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnChartValueSelectedListener {
    public static String ARG_IS_FROM_SPLASH = "is_from_splash";
    PieChart pieChart;
    SegmentedRadioGroup segmentText;
    RelativeLayout relativesegbutton, relativeshipping, relativeSearch, relativeHeader, relativeHeaderWeather, relativeFrame;
    String newShippingPriceurl, rateValueCanada = "", rateValueMexico = "", temptype, selectShippingType, locationName,locationFav,
            shippingTypeName;
    private static View view;
    Spinner spinner;
    FloatingActionButton fab_dashboard;
    ArrayList<String> commudityNameList = new ArrayList<>();
    FrameLayout dashboardLayout;
    RelativeLayout relativeselectcomidname, relativeSelectItem;
    List<WeatherResponse>   weatherResponseList = new ArrayList<>();;
    ListView listViewWeather, listViewVolume, listViewVolume1,listViewVolume2,listViewExchange, listShipping;
    Switch switchtemp;
    Button weather_button, nationalweather_button, liveweather_imageButton,
            volumereports_button, terminal_imageButton, shipping_button, exchange_button,
            holiday_button, freeze_button, juice_button, customerprice_imageButton, diesel_button, gasonline_imageButton, fuelonline_imageButton;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";

    List<VolumeResponse> volumeResponseList,volumeResponseList1,volumeResponseList2;
    VolumeResponse volumeResponse,volumeResponse1,volumeResponse2;
    String lastupdate;
    ExchangeRateModal exchangeRateModal;
    List<ExchangeRateModal> exchangeRateModalList;
    ExchangeAdapter exchangeAdapter;

    String[] shippingType = {"All", "Non-Organic", "Organic"};
    Spinner spinnershippingType, spinnerlocation;
    ShippingResponse shippingResponse;
    List<ShippingResponse> shippingResponseList;
    HorizontalScrollView horizontalscrollshipping;
    List<SearchCommidity> searchCommidityList;
    SearchComidityAdapter searchComidityAdapter;
    SearchCommidity searchCommidity;
    ListView listCommidities;
    String selctCommidtyName, tempType;
    ImageView searchIcon, backArrow, clearSearch;
    EditText editseachCommidity;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public GlobalData globalData;
    String Volume_Get_URL;
    String commidityName;

    String[] terminalType = {"All", "Non-Organic", "Organic"};
    Spinner spinnerterminalType, spinnerterminalorigin, spinnerterminallocation;
    ArrayAdapter terminalTypeAdapter;
    List<String> locationsList;
    List<String> originList;
    String terminalLocationName, terminalOriginName, terminalTypeName, selectTerminalType, updatedDateFormat;
    ArrayAdapter<String> locationsAdapter, originAdapter,shiptypeAda;
    TerminalPriceResponse terminalPriceResponse;
    List<TerminalPriceResponse> terminalPriceResponseList;
    ListView listterminal;

    String[] holidays = {"30", "60", "90"};
    Spinner spinnerHolidaydates;
    String holidayDate;
    HolidayModal holidayModal;
    Gas gas;
    Gas gas1;
    List<Gas> gasList=new ArrayList<>();
    List<Gas> fuelList=new ArrayList<>();
    List<HolidayModal> holidayModalList;
    List<HolidayModal> canadaholidayList;
    List<HolidayModal> mexicoholidayList;
    ListView listViewUsa, listViewMexico, listViewCanada;
    RelativeLayout  relativeliveWeather, relativeWeatherLive;
    LinearLayout relativeVolume,relative_resp,relative_exchange;
    double city1lat, city1lng, city2lat, city2lng, city3lat, city3lng;
    Button favShipping,favTerminal,favlive;

    public ArrayList<String> weatherArrays, TempArrays, PerfictionArrayList, windSpeedArrayList, humidityList, uvIndexList, imagesList, highTempList, lowTempList, colorsList;
    TextView textDayFirst, textDaysecond, textDaythird, textDayfourth, textDayfifth, textDaysixth, textDayseventh, textDayeighth,
            tempFirst, tempsecond, tempThird, tempfourth, tempfifth, tempsixth, tempseventh, tempeighth,
            perfFirst, perfsecond, perfThird, perffourth, perffifth, perfsixth, perfseventh, perfeighth,
            windspeedFirst, windspeedsecond, windspeedThird, windspeedfourth, windspeedfifth, windspeedsixth, windspeedseventh, windspeedeighth,
            hmdtyFirst, hmdtysecond, hmdtythird, hmdtyfourth, hmdtyfifth, hmdtysixth, hmdtyseventh, hmdtyeighth,
            uvFirst, uvsecond, uvthird, uvfourth, uvfifth, uvsixth, uvseventh, uveighth,
            highlowFirst, highlowsecond, highlowThird, highlowfourth, highlowfifth, highlowsixth, highlowseventh, highloweighth, textViewweatherAlert;

    ImageView img1, img2, img3, img4, img5, img6, img7, img8, imgPlaceClear, imgPlace1Clear, imgPlace2Clear, imgPlace3Clear;
    Button button_weekly, button_monthly, button_yearly, buttonliveweather;
    String hightemparature1, hightemparature2, hightemparature3, hightemparature4, hightemparature5, hightemparature6, hightemparature7, hightemparature8,
            lowtemparature1, lowtemparature2, lowtemparature3, lowtemparature4, lowtemparature5, lowtemparature6, lowtemparature7, lowtemparature8;

    String cities_value, fresh_fruits_value, fruits_vegetables_value,month;
    TextView indexVal, allFruitsVal, allcitiesVal, textselectcommid, textselectitem,monthtext;
   TextView lasttext;

    public LineChart mChartGas, mChartFuel;
    public ListView listviewGas;
    public ArrayList<String> xValsGas;
    public ArrayList<Entry> yValsGas;
    public ArrayList<String> xValsFuel;
    ArrayList<Entry> yValsFuel;
    ArrayList<Entry> gasValsFuel = new ArrayList<Entry>();

    String city1latstrng, city1lngstrng, city2latstrng, city2lngstrng, city3latstrng, city3lngstrng, placeNameCity1, placeNameCity2, placeNameCity3;
    String live1Temp, live1preciption, live1windSpeed, live1Humidity, live1dewPoint, live1Uv, live2Temp, live2preciption, live2windSpeed, live2Humidity, live2dewPoint, live2Uv, live3Temp, live3preciption, live3windSpeed, live3Humidity, live3dewPoint, live3Uv;

    HorizontalScrollView horizontalliveweather;
    TextView textcity1, textcity2, textcity3, texttemp1, texttemp2, texttemp3, textpreci1, textpreci2, textpreci3, textwindspeed1, textwindspeed2, textwindspeed3, textlivehumidity1, textlivehumidity2, textlivehumidity3,
            textlivedewpoint1, textlivedewpoint2, textlivedewpoint3, textliveuvIndex1, textliveuvIndex2, textliveuvIndex3;

    RelativeLayout relativeliveweather, relativeCity1, relativeLiveCity2, relativeLiveCity3;
    ImageView imageViewProfile;
    TextView textViewMysub, changePass, logout,editProfile,allsubscriptions;
    TextView textnoJuice;
    ListView listjuices;
    List<JuiceModel> juiceModelList;
    JuiceModel juiceModel;
    ListView listfreeze;
    List<FreezeModel> freezeModelList;
    FreezeModel freezeModel;
    Button volumehistoric,volumeyear,volumemonth;

    String liveWeatherUrl1, liveWeatherUrl2, liveWeatherUrl3, placeNameintentLive1, placeNameintentLive2, placeNameintentLive3;

    PlacesAdapter placesAdapter;
    PlacesModal placesModal;
    List<PlacesModal> placesModalList;
    EditText edittextPlaceName;
    ListView listPlaces;
    String text, placeNameintent, lat, lng, liveWeathertext2, liveWeathertext3, liveWeathertext1;
    Button buttonLiveWe;
    EditText editLivWeather1, editLivWeather2, editLivWeather3;
    ListView listPlacesLive;
    RelativeLayout relativelive1, relativelive2, relativelive3;
    boolean isFirstTime;
    ListView listDiesel;
    Button exchangehistoric,weatherHistoric,favweather;
    Button gasHistoric;
    private TransparentProgressDialog pd;
    private Handler h;
    private Runnable r;
    Button fuellbutton;
    String url=ApiConstants.API.concat("userPreferences");
    JSONArray jsonArray;
    double n1;
    double n2;
    private int mStatusCode = 0;
    String finalcheckuser;
    String place1,place2,place3;
    String name,password,locationName1,locationorigon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPreferences1  = getSharedPreferences("passwordString", MODE_PRIVATE);
        password= sharedPreferences1.getString("passValue", "");
        name= sharedPreferences1.getString("user", "");

        isFirstTime = getIntent().getBooleanExtra(ARG_IS_FROM_SPLASH, false);
        h = new Handler();
        pd = new TransparentProgressDialog(this, R.drawable.sgg);
        r =new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };
        //commidityName=getIntent().getStringExtra("fruititem");
        intialization();



    }

    private void getCheckUser(String sessionid) {

        String url=ApiConstants.CHECK_USER.concat(sessionid);
        Log.d("url","url"+url);
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
                url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CHECK_USER","CHECK_USER"+response);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("check", response.toString());
                        editor.apply();
                        SharedPreferences sharedPreferences1 = getSharedPreferences("MyPref", 0);
                        final String finalcheckuser=sharedPreferences1.getString("check",null);
                        Log.d("finalcheckuser","finalcheckuser"+finalcheckuser);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("",""+error);
                    }
                });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(myReq);
        myReq.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    @Override
    public void onBackPressed() { }

    public void intialization() {

        listDiesel=(ListView)findViewById(R.id.listdiesel);
        spinner = (Spinner) findViewById(R.id.spinner_search);
        relativeselectcomidname = (RelativeLayout) findViewById(R.id.relativeselectcomidname);
        relativeSelectItem = (RelativeLayout) findViewById(R.id.relativeSelectItem);
        relativeselectcomidname.setVisibility(View.GONE);
        textselectcommid = (TextView) findViewById(R.id.textselectcommid);
        textselectitem = (TextView) findViewById(R.id.textselectitem);


        fab_dashboard = (FloatingActionButton) findViewById(R.id.fab_dashboardbutton);
        selctCommidtyName = SPHelper.getFromSP(getApplicationContext(), SPHelper.MyPREFERENCES, SPHelper.KEY_COMMUDITYNAME);
        searchCommidityList = new ArrayList<>();
        editseachCommidity = (EditText) findViewById(R.id.editseachCommidity);
        fab_dashboard.setVisibility(View.GONE);
        textselectitem.setVisibility(View.GONE);
        relativeSelectItem.setVisibility(View.GONE);

        if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
            relativeselectcomidname.setVisibility(View.VISIBLE);
            textselectcommid.setText(selctCommidtyName);
            editseachCommidity.setText(selctCommidtyName);
            fab_dashboard.setVisibility(View.VISIBLE);
            textselectitem.setText("Weather Report");
            textselectitem.setVisibility(View.VISIBLE);
            relativeSelectItem.setVisibility(View.VISIBLE);
        } else {
            relativeselectcomidname.setVisibility(View.GONE);
        }
        searchIcon = findViewById(R.id.searchIcon);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        clearSearch = (ImageView) findViewById(R.id.clearSearch);
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        imageViewProfile.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        clearSearch.setVisibility(View.GONE);
        backArrow.setOnClickListener(this);
        clearSearch.setOnClickListener(DashboardActivity.this);
        listCommidities = (ListView) findViewById(R.id.listCommidities);
        relativeSearch = (RelativeLayout) findViewById(R.id.relativeSearch);
        relativeFrame = (RelativeLayout) findViewById(R.id.relativeFrame);
        relativeFrame.setVisibility(View.VISIBLE);
        relativeHeader.setVisibility(View.VISIBLE);
        relativeSearch.setVisibility(View.GONE);
        listCommidities.setVisibility(View.GONE);
        getPriceIndex();
    }

    @Override
    protected void onStart() {
        super.onStart();
        globalData = (GlobalData) getApplicationContext();

        //Reading sign in response
        for (int i = 0; i < globalData.getCommudityResponse().size(); i++) {
             commidityName = globalData.getCommudityResponse().get(i).getCommodityName();
            commudityNameList.add(globalData.getCommudityResponse().get(i).getCommodityName());
            searchCommidity = new SearchCommidity(commidityName);
            searchCommidityList.add(searchCommidity);
           // searchCommidityList.add(commidityName);
        }
        searchComidityAdapter = new SearchComidityAdapter(DashboardActivity.this, searchCommidityList);
        listCommidities.setAdapter(searchComidityAdapter);
        searchComidityAdapter.notifyDataSetChanged();
        editseachCommidity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editseachCommidity.getText().toString().toLowerCase(Locale.getDefault());
                searchComidityAdapter.filter(text);
                if (text != null && text.equals("")) {
                    listCommidities.setVisibility(View.GONE);
                    clearSearch.setVisibility(View.GONE);
                    relativeFrame.setVisibility(View.VISIBLE);

                } else {
                    listCommidities.setVisibility(View.VISIBLE);
                    clearSearch.setVisibility(View.VISIBLE);
                    relativeFrame.setVisibility(View.GONE);
                }


            }
        });


        // Creating ArrayAdapter using the string array and default spinner layout
        CommudityAdapter adapter = new CommudityAdapter(DashboardActivity.this, R.layout.commudity_dropdown, R.id.commudity_textView, commudityNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        fab_dashboard.setOnClickListener(this);
        dashboardLayout = (FrameLayout) findViewById(R.id.dashboard_widget);
        try {
            Log.e("getDashboardSelection", " --- " + globalData.getDashboardSelection());
            if (globalData.getDashboardSelection() == 1) {
                if (!isFirstTime) {
                    if (view == null) {
                        dashboardLayout.removeAllViews();
                        textselectitem.setText("Weather Report");
                        textselectitem.setVisibility(View.VISIBLE);
                            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.weather_dashboard, dashboardLayout, true);
                            weatherIntialization();
                    }
                }

            } else if (globalData.getDashboardSelection() == 2) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Volume Reports (in 10,000 lbs)");
                textselectitem.setVisibility(View.VISIBLE);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.volume_layout, dashboardLayout, true);
                volumeIntialization();
            } else if (globalData.getDashboardSelection() == 3) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Shipping Price Fob");
                textselectitem.setVisibility(View.VISIBLE);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.shippingprice, dashboardLayout, true);
                shippingpriceIntialization();
            } else if (globalData.getDashboardSelection() == 4) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Exchange Rate (1 US Dollar)");
                textselectitem.setVisibility(View.VISIBLE);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.exchangerate, dashboardLayout, true);
                exchangerateIntialization();
            } else if (globalData.getDashboardSelection() == 5) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("National Weather");
                textselectitem.setVisibility(View.VISIBLE);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.nationalweather_dashboard, dashboardLayout, true);
            } else if (globalData.getDashboardSelection() == 6) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Terminal Market Price");
                textselectitem.setVisibility(View.VISIBLE);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.terminalmarketprice, dashboardLayout, true);
                terminalInitialization();
            } else if (globalData.getDashboardSelection() == 7) {
                if (view == null) {
                    dashboardLayout.removeAllViews();
                    textselectitem.setText("Live Weather");
                    textselectitem.setVisibility(View.VISIBLE);
                    LayoutInflater.from(DashboardActivity.this).inflate(R.layout.liveweatheractivity, dashboardLayout, true);
                    liveWeatherInitialization();
                }

            } else if (globalData.getDashboardSelection() == 8) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Consumer Price Index");
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.consumerindex, dashboardLayout, true);
                consumereInitialization();
            } else if (globalData.getDashboardSelection() == 9) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Gasoline Prices");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.activity_gasprice, dashboardLayout, true);
                gasLayoutInitialization();
            } else if (globalData.getDashboardSelection() == 10) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Diesel Fuel Prices");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.activity_fuelprice, dashboardLayout, true);
                fuelLayoutInitialization();
            } else if (globalData.getDashboardSelection() == 11) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Juice Volume");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.juicelayout, dashboardLayout, true);
                juiceLayoutInitialization();

            } else if (globalData.getDashboardSelection() == 12) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Freeze Volume");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.freezelayout, dashboardLayout, true);
                freezeLayoutInitialization();
            } else if (globalData.getDashboardSelection() == 13) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Holiday");
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.holiday_activity, dashboardLayout, true);
                holidayInitialization();
            }
        } finally {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<CommudityResponse> commudityResponseList=new ArrayList<>();
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            //pd.show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiConstants.Login_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //pd.dismiss();
                    System.out.println("login response....." + response);
                    try {
                        String sessionid=response.getString("sessionId");
                        getCheckUser(sessionid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error: " + error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String encodedCredentials = Base64.encodeToString((name + ":" + password).getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", "Basic " + encodedCredentials);
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("" + parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_dashboardbutton: {
                callCustomDialog();
                break;
            }
            case R.id.clearSearch: {
                editseachCommidity.setText("");
                break;
            }
            case R.id.backArrow: {
                relativeSearch.setVisibility(View.GONE);
                relativeHeader.setVisibility(View.VISIBLE);
                relativeFrame.setVisibility(View.VISIBLE);
                listCommidities.setVisibility(View.GONE);
                relativeselectcomidname.setVisibility(View.VISIBLE);
                relativeSelectItem.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.searchIcon: {
                relativeSearch.setVisibility(View.VISIBLE);
                relativeHeader.setVisibility(View.GONE);
                relativeFrame.setVisibility(View.GONE);
                relativeselectcomidname.setVisibility(View.GONE);
                relativeSelectItem.setVisibility(View.GONE);
                break;

            }
            case R.id.imageViewProfile:
                gotoProfilePage();
        }

    }

    public void gotoProfilePage() {
        //Intent profileIntent = new Intent(DashboardActivity.this, ProfileActivity.class);
        //startActivity(profileIntent);
        LayoutInflater layoutInflater
                = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.profilepopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(popupView, Gravity.TOP | Gravity.RIGHT, 150, 150);
        editProfile=popupView.findViewById(R.id.profileedit);
        textViewMysub = (TextView) popupView.findViewById(R.id.textViewMysub);
        changePass = (TextView) popupView.findViewById(R.id.changePass);
        logout = (TextView) popupView.findViewById(R.id.logout);
        allsubscriptions=popupView.findViewById(R.id.sub);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordActivity();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.removeAllValuesFromPrefs(DashboardActivity.this, MyPREFERENCES);
               /* SharedPreferences settings = getApplicationContext().getSharedPreferences("MY_PREFS_NAME", Context.MODE_PRIVATE);
                settings.edit().clear().commit();*/
                SharedPreferences sp1 = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                sp1.edit().clear().commit();
                SharedPreferences sp2= getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                sp2.edit().clear().commit();
                SharedPreferences sp3= getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                sp3.edit().clear().commit();
                SharedPreferences settings1 = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                settings1.edit().clear().commit();
                SharedPreferences settings2 = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                settings2.edit().remove("check").commit();
                SharedPreferences sp4= getApplicationContext().getSharedPreferences("passwordString", Context.MODE_PRIVATE);
                sp4.edit().clear().commit();
                Intent logoutIntent = new Intent(DashboardActivity.this, SigninActivity.class);
                startActivity(logoutIntent);

            }
        });
        textViewMysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences12 = getApplicationContext().getSharedPreferences("packex", Context.MODE_PRIVATE);
                //SharedPreferences.Editor editor12 = sharedPreferences12.edit();

                String pack=sharedPreferences12.getString("pack","");
                Log.d("pack","pack"+pack);
                if(pack.equals("1"))
                {
                    Intent intent=new Intent(DashboardActivity.this,Main2PackagesActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(DashboardActivity.this,MainPackagesActivity.class);
                    startActivity(intent);
                }
               /* Intent intent=new Intent(DashboardActivity.this,Main2PackagesActivity.class);
                startActivity(intent);*/
            }
        });
        allsubscriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AllSubcriptionsActivity.class);
                startActivity(intent);

            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });
    }
    public void changePasswordActivity() {
        Intent changePassIntent = new Intent(DashboardActivity.this, ChangePasswordActivity.class);
        startActivity(changePassIntent);
    }
    public void callCustomDialog() {
        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dashboard);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        weather_button = (Button) dialog.findViewById(R.id.weather_imageButton);
        volumereports_button = (Button) dialog.findViewById(R.id.viewreports_imageButton);
        shipping_button = (Button) dialog.findViewById(R.id.shipping_imageButton);
        nationalweather_button = (Button) dialog.findViewById(R.id.nationalweather_imageButton);
        exchange_button = (Button) dialog.findViewById(R.id.exchangerate_imageButton);
        terminal_imageButton = (Button) dialog.findViewById(R.id.terminal_imageButton);
        liveweather_imageButton = (Button) dialog.findViewById(R.id.liveweather_imageButton);
        customerprice_imageButton = (Button) dialog.findViewById(R.id.customerprice_imageButton);
        gasonline_imageButton = (Button) dialog.findViewById(R.id.gasonline_imageButton);
        fuelonline_imageButton = (Button) dialog.findViewById(R.id.fuelonline_imageButton);
        juice_button = (Button) dialog.findViewById(R.id.juicevolume_imageButton);
        freeze_button = (Button) dialog.findViewById(R.id.freezevolume_imageButton);
        holiday_button = (Button) dialog.findViewById(R.id.holiday_imageButton);
        weather_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callPreviousActivity("weatheractivity");
                    globalData.setDashboardSelection(1);
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    globalData.setDashboardSelection(1);
                    dialog.dismiss();
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
                }
            }
        });
        volumereports_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("volumereportactivity");
                dialog.dismiss();
                globalData.setDashboardSelection(2);
            }
        });
        shipping_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("shippingactivity");
                dialog.dismiss();
                globalData.setDashboardSelection(3);
            }
        });
        nationalweather_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("nationalweatheractivity");
                dialog.dismiss();
                globalData.setDashboardSelection(5);
            }
        });
        exchange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("exchangeactivity");
                dialog.dismiss();
                globalData.setDashboardSelection(4);
            }
        });
        terminal_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("terminalactivity");
                dialog.dismiss();
                globalData.setDashboardSelection(6);
            }
        });
        customerprice_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("consumerindex");
                dialog.dismiss();
                globalData.setDashboardSelection(8);
            }
        });
        gasonline_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("gasonline");
                dialog.dismiss();
                globalData.setDashboardSelection(9);
            }
        });
        holiday_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPreviousActivity("holidaysonline");
                dialog.dismiss();
                globalData.setDashboardSelection(13);
            }
        });
        fuelonline_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callPreviousActivity("fuelonline");
                    dialog.dismiss();
                    globalData.setDashboardSelection(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    globalData.setDashboardSelection(10);
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);

                }
            }
        });
        juice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callPreviousActivity("juicevolume");
                    dialog.dismiss();
                    globalData.setDashboardSelection(11);
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    globalData.setDashboardSelection(11);
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
                }
            }
        });
        freeze_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callPreviousActivity("freezevolume");
                    dialog.dismiss();
                    globalData.setDashboardSelection(12);
                } catch (Exception e) {
                    e.printStackTrace();
                    globalData.setDashboardSelection(12);
                    dialog.dismiss();
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
                }
            }
        });
        liveweather_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY1);
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY2);
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY3);
                try {
                    callPreviousActivity("liveweather");
                    dialog.dismiss();
                    globalData.setDashboardSelection(7);
                } catch (Exception e) {
                    e.printStackTrace();
                    globalData.setDashboardSelection(7);
                    dialog.dismiss();
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
                }

            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void callPreviousActivity(String activityChange) {
        if (activityChange == "weatheractivity") {
            if (view == null) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Weather Report");
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.weather_dashboard, dashboardLayout, true);
                weatherIntialization();
            }

        } else if (activityChange == "volumereportactivity") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Volume Reports (in 10,000 lbs)");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.volume_layout, dashboardLayout, true);
            volumeIntialization();
        } else if (activityChange == "shippingactivity") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Shipping Price Fob");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.shippingprice, dashboardLayout, true);
            shippingpriceIntialization();

        } else if (activityChange == "nationalweatheractivity") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("National Weather");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.nationalweather_dashboard, dashboardLayout, true);
            weatherAlertInitialization();

        } else if (activityChange == "exchangeactivity") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Exchange Rate (1 US Dollar)");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.exchangerate, dashboardLayout, true);
            exchangerateIntialization();
        } else if (activityChange == "terminalactivity") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Terminal Market Price");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.terminalmarketprice, dashboardLayout, true);
            terminalInitialization();
        } else if (activityChange == "liveweather") {
            if (view == null) {
                dashboardLayout.removeAllViews();
                textselectitem.setText("Live Weather");
                LayoutInflater.from(DashboardActivity.this).inflate(R.layout.liveweatheractivity, dashboardLayout, true);
                liveWeatherInitialization();
            }

        } else if (activityChange == "consumerindex") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Consumer Price Index");
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.consumerindex, dashboardLayout, true);
            consumereInitialization();

        } else if (activityChange == "gasonline") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Gasline Prices");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.activity_gasprice, dashboardLayout, true);
            gasLayoutInitialization();
        } else if (activityChange == "fuelonline") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Diesel Fuel Prices");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.activity_fuelprice, dashboardLayout, true);
            fuelLayoutInitialization();
        } else if (activityChange == "juicevolume") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Juice Volume");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.juicelayout, dashboardLayout, true);
            juiceLayoutInitialization();
        } else if (activityChange == "freezevolume") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Freeze Volume");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.freezelayout, dashboardLayout, true);
            freezeLayoutInitialization();
        } else if (activityChange == "holidaysonline") {
            dashboardLayout.removeAllViews();
            textselectitem.setText("Holiday");
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutInflater.from(DashboardActivity.this).inflate(R.layout.holiday_activity, dashboardLayout, true);
            holidayInitialization();
        }
    }

    //Weather
    public void weatherIntialization() {
        //  listViewWeather = (ListView) findViewById(R.id.listViewWeather);
        weatherHistoric= (Button) findViewById(R.id.weatherhistoric);
        favweather=findViewById(R.id.favweather);
        temptype = "e";
        tempType = "F";
        temptype = "e";
        edittextPlaceName = (EditText) findViewById(R.id.edittextPlaceName);
        SharedPreferences sharedPreferences4 = getSharedPreferences("MyPref", 0);
        finalcheckuser = sharedPreferences4.getString("check", null);
        Log.d("finalcheckuser", "finalcheckuser" + finalcheckuser);

            if(finalcheckuser!=null){
                // Do you work here on success
                try {
                    JSONObject jsonObject=new JSONObject(finalcheckuser);
                JSONArray jsonArray=jsonObject.getJSONArray("preferences");
                Log.d("",""+jsonArray);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    if(selctCommidtyName.equals(jsonObject1.getString("commodityName")))
                    {
                        if(jsonObject1.getString("widgetId").equals("1"))
                        {
                            JSONArray jsonObject2=jsonObject1.getJSONArray("options");
                            Log.d("",""+jsonObject2);
                            for (int j=0;j<jsonObject2.length();j++)
                            {
                                JSONObject jsonObject3=jsonObject2.getJSONObject(j);
                                Log.d("",""+jsonObject3);
                                String name=jsonObject3.getString("paramValue");
                                JSONObject jsonObject5=new JSONObject(name);
                                placeNameintent =jsonObject5.getString("city");
                                lat=jsonObject5.getString("latitude");
                                lng=jsonObject5.getString("longitude");
                                Log.d("",""+placeNameintent);
                                edittextPlaceName.setText(placeNameintent);
                                edittextPlaceName.setCursorVisible(true);
                                callWeatherRequest(Double.parseDouble(lat),Double.parseDouble(lng));

                                /*SharedPreferences pref = getApplicationContext().getSharedPreferences("pl", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("place", placeNameintent);  // Saving string
                                // Save the changes in SharedPreferences
                                editor.commit(); // commit changes
                                Geocoder coder = new Geocoder(this);
                                try {
                                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(nm, 50);
                                    for (Address add : adresses) {
                                        double lng = add.getLongitude();
                                        double lat = add.getLatitude();
                                        System.out.println("latitude...." + lat);
                                        System.out.println("longitude......" + lng);
                                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                        editor = sharedpreferences.edit();
                                        editor.putString(latitude, String.valueOf(lat));
                                        editor.putString(longitude, String.valueOf(lng));
                                        editor.putString("PlaceName",placeNameintent);
                                        editor.commit();
                                        callWeatherRequest(lat, lng);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }else{
                // null response or Exception occur
            }

        favweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("pl", MODE_PRIVATE);
                //SharedPreferences.Editor editor = pref.edit();
               placeNameintent=pref.getString("place", "");
               Log.d("00",""+placeNameintent);*/
                if(placeNameintent != null && !placeNameintent.equals(""))
                {
                    Geocoder coder = new Geocoder(getApplicationContext());
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(placeNameintent, 50);
                        for (Address add : adresses) {
                            lat = String.valueOf(add.getLongitude());
                            lng = String.valueOf(add.getLatitude());
                            System.out.println("latitude...." + lat);
                            System.out.println("longitude......" + lng);
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            editor = sharedpreferences.edit();
                            editor.putString(latitude, lat);
                            editor.putString(longitude, lng);
                            editor.commit();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Map<String, String> params = new HashMap<String, String>();
                    JSONObject params=new JSONObject();
                    SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                    final String responce=sharedPreferences1.getString("userid","");
                    Log.d("userid","userid"+responce);
                    try {
                        params.put("widgetId",1);
                        params.put("commodityName",selctCommidtyName );
                        params.put("userId", responce);
                        JSONObject jsonObject1=new JSONObject();
                        jsonObject1.put("city",placeNameintent);
                        jsonObject1.put("latitude",lat);
                        jsonObject1.put("longitude",lng);
                        JSONObject jsonObject2=new JSONObject();
                        jsonObject2.put("paramValue",jsonObject1.toString());
                        jsonObject2.put("paramKey","weatherReport");
                        Log.d("",""+jsonObject2);
                        jsonArray=new JSONArray();
                        jsonArray.put(jsonObject2);
                        Log.d("js","js"+jsonArray);
                        params.put("options", jsonArray);
                        Log.d("hi","hi"+params);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // pd.show();
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
                            pd.dismiss();
                            mStatusCode = response.statusCode;
                            Log.d("mStatusCode","mStatusCode"+mStatusCode);
                            try {
                                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                Log.d("",""+json);
                                if(json.equals(""))
                                {
                                    Toaster.toast("Filter Saved");
                                    pd.dismiss();
                                }
                                else {
                                    Toaster.toast("Filter Not Saved");
                                    pd.dismiss();
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
                else {
                    Toast.makeText(getApplicationContext(),"Please Select place",Toast.LENGTH_LONG).show();
                }

            }
        });
        weatherHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),WeatherHistoricActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
              //  intent.putExtra("temp",tempType);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefTemp", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("tem", tempType);
                editor.commit(); // commit changes
                startActivity(intent);
            }
        });
        imgPlaceClear = (ImageView) findViewById(R.id.imgPlaceClear);
        segmentText = (SegmentedRadioGroup) findViewById(R.id.segment_text);
        relativesegbutton = (RelativeLayout) findViewById(R.id.relativesegbutton);
        relativeHeaderWeather = (RelativeLayout) findViewById(R.id.relativeHeaderWeather);
        relativeHeaderWeather.setVisibility(View.GONE);
        switchtemp = (Switch) findViewById(R.id.switchtemp);
        searchIcon = (ImageView) findViewById(R.id.searchIcon);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        clearSearch = (ImageView) findViewById(R.id.clearSearch);
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        listCommidities = (ListView) findViewById(R.id.listCommidities);
        relativeSearch = (RelativeLayout) findViewById(R.id.relativeSearch);
        relativeFrame = (RelativeLayout) findViewById(R.id.relativeFrame);
        relativeFrame.setVisibility(View.VISIBLE);
        relativeHeader.setVisibility(View.VISIBLE);
        relativeSearch.setVisibility(View.GONE);
        listCommidities.setVisibility(View.GONE);
        highlowFirst = (TextView) findViewById(R.id.highlowFirst);
        highlowsecond = (TextView) findViewById(R.id.highlowsecond);
        highlowThird = (TextView) findViewById(R.id.highlowThird);
        highlowfourth = (TextView) findViewById(R.id.highlowfourth);
        highlowfifth = (TextView) findViewById(R.id.highlowfifth);
        highlowsixth = (TextView) findViewById(R.id.highlowsixth);
        highlowseventh = (TextView) findViewById(R.id.highlowseventh);
        highloweighth = (TextView) findViewById(R.id.highloweighth);
        textDayFirst = (TextView) findViewById(R.id.textDayFirst);
        textDaysecond = (TextView) findViewById(R.id.textDaysecond);
        textDaythird = (TextView) findViewById(R.id.textDaythird);
        textDayfourth = (TextView) findViewById(R.id.textDayfourth);
        textDayfifth = (TextView) findViewById(R.id.textDayfifth);
        textDaysixth = (TextView) findViewById(R.id.textDaysixth);
        textDayseventh = (TextView) findViewById(R.id.textDayseventh);
        textDayeighth = (TextView) findViewById(R.id.textDayeighth);
        tempFirst = (TextView) findViewById(R.id.tempFirst);
        tempsecond = (TextView) findViewById(R.id.tempsecond);
        tempThird = (TextView) findViewById(R.id.tempThird);
        tempfourth = (TextView) findViewById(R.id.tempfourth);
        tempfifth = (TextView) findViewById(R.id.tempfifth);
        tempsixth = (TextView) findViewById(R.id.tempsixth);
        tempseventh = (TextView) findViewById(R.id.tempseventh);
        tempeighth = (TextView) findViewById(R.id.tempeighth);
        perfFirst = (TextView) findViewById(R.id.perfFirst);
        perfsecond = (TextView) findViewById(R.id.perfsecond);
        perfThird = (TextView) findViewById(R.id.perfThird);
        perffourth = (TextView) findViewById(R.id.perffourth);
        perffifth = (TextView) findViewById(R.id.perffifth);
        perfsixth = (TextView) findViewById(R.id.perfsixth);
        perfseventh = (TextView) findViewById(R.id.perfseventh);
        perfeighth = (TextView) findViewById(R.id.perfeighth);
        windspeedFirst = (TextView) findViewById(R.id.windspeedFirst);
        windspeedsecond = (TextView) findViewById(R.id.windspeedsecond);
        windspeedThird = (TextView) findViewById(R.id.windspeedThird);
        windspeedfourth = (TextView) findViewById(R.id.windspeedfourth);
        windspeedfifth = (TextView) findViewById(R.id.windspeedfifth);
        windspeedsixth = (TextView) findViewById(R.id.windspeedsixth);
        windspeedseventh = (TextView) findViewById(R.id.windspeedseventh);
        windspeedeighth = (TextView) findViewById(R.id.windspeedeighth);
        hmdtyFirst = (TextView) findViewById(R.id.hmdtyFirst);
        hmdtysecond = (TextView) findViewById(R.id.hmdtysecond);
        hmdtythird = (TextView) findViewById(R.id.hmdtythird);
        hmdtyfourth = (TextView) findViewById(R.id.hmdtyfourth);
        hmdtyfifth = (TextView) findViewById(R.id.hmdtyfifth);
        hmdtysixth = (TextView) findViewById(R.id.hmdtysixth);
        hmdtyseventh = (TextView) findViewById(R.id.hmdtyseventh);
        hmdtyeighth = (TextView) findViewById(R.id.hmdtyeighth);
        uvFirst = (TextView) findViewById(R.id.uvFirst);
        uvsecond = (TextView) findViewById(R.id.uvsecond);
        uvthird = (TextView) findViewById(R.id.uvthird);
        uvfourth = (TextView) findViewById(R.id.uvfourth);
        uvfifth = (TextView) findViewById(R.id.uvfifth);
        uvsixth = (TextView) findViewById(R.id.uvsixth);
        uvseventh = (TextView) findViewById(R.id.uvseventh);
        uveighth = (TextView) findViewById(R.id.uveighth);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);
        img8 = (ImageView) findViewById(R.id.img8);
        weatherArrays = new ArrayList<String>();
        TempArrays = new ArrayList<String>();

        weatherResponseList = new ArrayList<>();
        PerfictionArrayList = new ArrayList<String>();
        windSpeedArrayList = new ArrayList<String>();
        humidityList = new ArrayList<String>();
        uvIndexList = new ArrayList<String>();
        imagesList = new ArrayList<>();
        highTempList = new ArrayList<>();
        lowTempList = new ArrayList<>();
        colorsList = new ArrayList<>();


        listPlaces = (ListView) findViewById(R.id.listPlaces);
//        listPlaces.setVisibility(View.GONE);

        placesModalList = new ArrayList<>();
        Intent i = getIntent();
        placeNameintent = i.getStringExtra("placeName");
        // segmentText.setVisibility(View.VISIBLE);
        relativesegbutton.setVisibility(View.VISIBLE);
        if (placeNameintent != null && !placeNameintent.equals("")) {
            edittextPlaceName.setText(placeNameintent);
            Geocoder coder = new Geocoder(this);
            try {
                ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(placeNameintent, 50);
                for (Address add : adresses) {
                    double lng = add.getLongitude();
                    double lat = add.getLatitude();
                    System.out.println("latitude...." + lat);
                    System.out.println("longitude......" + lng);

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    editor.putString(latitude, String.valueOf(lat));
                    editor.putString(longitude, String.valueOf(lng));
                    editor.putString("PlaceName",placeNameintent);
                    editor.commit();
                    callWeatherRequest(lat, lng);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }
        imgPlaceClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextPlaceName.setText("");
//                lat = sharedpreferences.getString(latitude, "");
                //lng = sharedpreferences.getString(longitude, "");
                System.out.println("lat long from shared prefs 1111:" + lat);
                System.out.println("lat long from shared prefs 2222:" + lng);
               /* if ((!lat.equals("") || (lat != null)) && (!lng.equals("") || (lng != null))) {
                    sharedpreferences.edit().remove(latitude).commit();
                    sharedpreferences.edit().remove(longitude).commit();
                }*/
                System.out.println("lat long from shared prefs:" + lat);
                System.out.println("lat long from shared prefs:" + lng);
                SharedPreferences settings2 = getApplicationContext().getSharedPreferences("p1", Context.MODE_PRIVATE);
                settings2.edit().remove("place").commit();
            }
        });
        edittextPlaceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPlaces.setVisibility(View.VISIBLE);
                // segmentText.setVisibility(View.GONE);
                relativesegbutton.setVisibility(View.GONE);
                text = edittextPlaceName.getText().toString().toLowerCase(Locale.getDefault());
                edittextPlaceName.setSelection(edittextPlaceName.getText().length());
               // edittextPlaceName.clearFocus();
               // edittextPlaceName.setCursorVisible(false);
              //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
              //  imm.hideSoftInputFromWindow(edittextPlaceName.getWindowToken(), 0);
                if (text.equals("")) {
                    //  relativeHeaderWeather.setVisibility(View.GONE);
                    relativesegbutton.setVisibility(View.VISIBLE);
                    // segmentText.setVisibility(View.VISIBLE);

                } else {
                    relativeHeaderWeather.setVisibility(View.GONE);
                }
                callGoogleService(text);
            }
        });
        edittextPlaceName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        segmentText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
                lat = sharedpreferences.getString(latitude, "");
                lng = sharedpreferences.getString(longitude, "");
                System.out.println("lat long from shared prefs:" + lat);
                System.out.println("lat long from shared prefs:" + lng);
                if (group == segmentText) {
                    if (checkedId == R.id.button_one) {
                        temptype = "e";
                        tempType = "F";
                        if (!lat.toString().equals("") && lat.toString() != null && !lat.toString().isEmpty() && !lng.toString().isEmpty() && lng.toString() != null && !lng.toString().equals("")) {
                            callWeatherRequest(Double.parseDouble(lat), Double.parseDouble(lng));
                        } else {
                            Toast.makeText(DashboardActivity.this, "Please select city", Toast.LENGTH_SHORT).show();
                            System.out.println("service not callllllll faurien heat");
                        }

                    } else if (checkedId == R.id.button_two) {
                        tempType = "C";
                        temptype = "m";
                        if (!lat.toString().equals("") && lat.toString() != null && !lat.toString().isEmpty() && !lng.toString().isEmpty() && lng.toString() != null && !lng.toString().equals("")) {
                            callWeatherRequest(Double.parseDouble(lat), Double.parseDouble(lng));
                        } else {
                            Toast.makeText(DashboardActivity.this, "Please select city", Toast.LENGTH_SHORT).show();
                            System.out.println("service not callllllll celsiusssss heat");
                        }
                    }
                }
            }
        });

        switchtemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
                lat = sharedpreferences.getString(latitude, "");
                lng = sharedpreferences.getString(longitude, "");
                System.out.println("lat long from shared prefs:" + lat);
                System.out.println("lat long from shared prefs:" + lng);

                if (isChecked) {
                    temptype = "e";
                    tempType = "F";
                    if (!lat.toString().equals("") && lat.toString() != null && !lat.toString().isEmpty() && !lng.toString().isEmpty() && lng.toString() != null && !lng.toString().equals("")) {
                        callWeatherRequest(Double.parseDouble(lat), Double.parseDouble(lng));
                    } else {
                        System.out.println("service not callllllll faurien heat");
                    }

                } else {
                    tempType = "C";
                    temptype = "m";
                    if (!lat.toString().equals("") && lat.toString() != null && !lat.toString().isEmpty() && !lng.toString().isEmpty() && lng.toString() != null && !lng.toString().equals("")) {
                        callWeatherRequest(Double.parseDouble(lat), Double.parseDouble(lng));
                    } else {
                        System.out.println("service not callllllll celsiusssss heat");
                    }

                }

            }
        });
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
         final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
    }


    public void callGoogleService(String placeName) {
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
        PlacesAdapter placesAdapter = new PlacesAdapter(DashboardActivity.this, placesModalList);
        listPlaces.setAdapter(placesAdapter);
        placesAdapter.notifyDataSetChanged();
    }

    public Map<String, String> callGetParams() {
        Map<String, String> params = new HashMap<>();
        return params;
    }



    public void callWeatherRequest(Double latitude, Double longitude) {
        weatherResponseList.clear();
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
                String WeatherUpdateUrl = ApiConstants.Weather_URL +latitude  + "/" + longitude + "/" + temptype + "/" + selctCommidtyName;
                System.out.println("Weaher url777777777777" + WeatherUpdateUrl);
                NukeSSLCerts.NukeSSLCerts();
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(WeatherUpdateUrl, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        relativeHeaderWeather.setVisibility(View.VISIBLE);
                        weatherArrays.clear();
                        TempArrays.clear();
                        PerfictionArrayList.clear();
                        windSpeedArrayList.clear();
                        humidityList.clear();
                        uvIndexList.clear();
                        imagesList.clear();
                        highTempList.clear();
                        lowTempList.clear();
                        colorsList.clear();
                        if(response.length()>0)
                        {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String colors = jsonObject.getString("colorCode");
                                    String temparature = jsonObject.getString("temp");
                                    String highTemparature = jsonObject.getString("high");
                                    String lowTemparature = jsonObject.getString("low");
                                    String precipitation = jsonObject.getString("precipitation");
                                    String windSpeed = jsonObject.getString("windspeed");
                                    String humidity = jsonObject.getString("humidity");
                                    String uvIndex = jsonObject.getString("uvIndex");
                                    String day = jsonObject.getString("day");
                                    String imageCode = jsonObject.getString("iconCode");
                                    weatherArrays.add(day);
                                    TempArrays.add(temparature);
                                    PerfictionArrayList.add(precipitation);
                                    windSpeedArrayList.add(windSpeed);
                                    humidityList.add(humidity);
                                    uvIndexList.add(uvIndex);
                                    imagesList.add(imageCode);
                                    highTempList.add(highTemparature);
                                    lowTempList.add(lowTemparature);
                                    colorsList.add(colors);
                                    System.out.println("colors..." + colors);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            textDayFirst.setText(weatherArrays.get(0));
                            textDaysecond.setText(weatherArrays.get(1));
                            textDaythird.setText(weatherArrays.get(2));
                            textDayfourth.setText(weatherArrays.get(3));
                            textDayfifth.setText(weatherArrays.get(4));
                            textDaysixth.setText(weatherArrays.get(5));
                            textDayseventh.setText(weatherArrays.get(6));
                            textDayeighth.setText(weatherArrays.get(7));
                            System.out.println("temp firsttttt" + TempArrays.get(0));
                            if ((highTempList.get(0)).trim() == null || (highTempList.get(0)).trim().equals("null")) {
                                hightemparature1 = "0";
                            } else {
                                hightemparature1 = highTempList.get(0);
                            }
                            if ((highTempList.get(1)).trim() == null || (highTempList.get(1)).trim().equals("null")) {
                                hightemparature2 = "0";
                            } else {
                                hightemparature2 = highTempList.get(1);
                            }
                            if ((highTempList.get(2)).trim() == null || (highTempList.get(2)).trim().equals("null")) {
                                hightemparature3 = "0";
                            } else {
                                hightemparature3 = highTempList.get(2);
                            }
                            if ((highTempList.get(3)).trim() == null || (highTempList.get(3)).trim().equals("null")) {
                                hightemparature4 = "0";
                            } else {
                                hightemparature4 = highTempList.get(3);
                            }
                            if ((highTempList.get(4)).trim() == null || (highTempList.get(4)).trim().equals("null")) {
                                hightemparature5 = "0";
                            } else {
                                hightemparature5 = highTempList.get(4);
                            }
                            if ((highTempList.get(5)).trim() == null || (highTempList.get(5)).trim().equals("null")) {
                                hightemparature6 = "0";
                            } else {
                                hightemparature6 = highTempList.get(5);
                            }
                            if ((highTempList.get(6)).trim() == null || (highTempList.get(6)).trim().equals("null")) {
                                hightemparature7 = "0";
                            } else {
                                hightemparature7 = highTempList.get(6);
                            }
                            if ((highTempList.get(7)).trim() == null || (highTempList.get(7)).trim().equals("null")) {
                                hightemparature8 = "0";
                            } else {
                                hightemparature8 = highTempList.get(7);
                            }


                            if ((lowTempList.get(0)).trim() == null || (lowTempList.get(0)).trim().equals("null")) {
                                lowtemparature1 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature1);
                            } else {
                                lowtemparature1 = lowTempList.get(0);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature1);
                            }
                            if ((lowTempList.get(1)).trim() == null || (lowTempList.get(1)).trim().equals("null")) {
                                lowtemparature2 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature2);
                            } else {
                                lowtemparature2 = lowTempList.get(1);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature1);
                            }
                            if ((lowTempList.get(2)).trim() == null || (lowTempList.get(2)).trim().equals("null")) {
                                lowtemparature3 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature3);
                            } else {
                                lowtemparature3 = lowTempList.get(2);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature3);
                            }
                            if ((lowTempList.get(3)).trim() == null || (lowTempList.get(3)).trim().equals("null")) {
                                lowtemparature4 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature4);
                            } else {
                                lowtemparature4 = lowTempList.get(3);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature4);
                            }
                            if ((lowTempList.get(4)).trim() == null || (lowTempList.get(4)).trim().equals("null")) {
                                lowtemparature5 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature5);
                            } else {
                                lowtemparature5 = lowTempList.get(4);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature5);
                            }
                            if ((lowTempList.get(5)).trim() == null || (lowTempList.get(5)).trim().equals("null")) {
                                lowtemparature6 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature6);
                            } else {
                                lowtemparature6 = lowTempList.get(5);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature6);
                            }
                            if ((lowTempList.get(6)).trim() == null || (lowTempList.get(6)).trim().equals("null")) {
                                lowtemparature7 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature7);
                            } else {
                                lowtemparature7 = lowTempList.get(6);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature7);
                            }
                            if ((lowTempList.get(7)).trim() == null || (lowTempList.get(7)).trim().equals("null")) {
                                lowtemparature8 = "0";
                                System.out.println("temp ifffffffffffff" + lowtemparature8);
                            } else {
                                lowtemparature8 = lowTempList.get(7);
                                System.out.println("temp felseeeeeeeeee" + lowtemparature8);
                            }


                            tempFirst.setText(TempArrays.get(0) + tempType);
                            tempsecond.setText(TempArrays.get(1) + tempType);
                            tempThird.setText(TempArrays.get(2) + tempType);
                            tempfourth.setText(TempArrays.get(3) + tempType);
                            tempfifth.setText(TempArrays.get(4) + tempType);
                            tempsixth.setText(TempArrays.get(5) + tempType);
                            tempseventh.setText(TempArrays.get(6) + tempType);
                            tempeighth.setText(TempArrays.get(7) + tempType);
                            perfFirst.setText(PerfictionArrayList.get(0).concat("%"));
                            perfsecond.setText(PerfictionArrayList.get(1).concat("%"));
                            perfThird.setText(PerfictionArrayList.get(2).concat("%"));
                            perffourth.setText(PerfictionArrayList.get(3).concat("%"));
                            perffifth.setText(PerfictionArrayList.get(4).concat("%"));
                            perfsixth.setText(PerfictionArrayList.get(5).concat("%"));
                            perfseventh.setText(PerfictionArrayList.get(6).concat("%"));
                            perfeighth.setText(PerfictionArrayList.get(7).concat("%"));
                            windspeedFirst.setText(windSpeedArrayList.get(0));
                            windspeedsecond.setText(windSpeedArrayList.get(1));
                            windspeedThird.setText(windSpeedArrayList.get(2));
                            windspeedfourth.setText(windSpeedArrayList.get(3));
                            windspeedfifth.setText(windSpeedArrayList.get(4));
                            windspeedsixth.setText(windSpeedArrayList.get(5));
                            windspeedseventh.setText(windSpeedArrayList.get(6));
                            windspeedeighth.setText(windSpeedArrayList.get(7));
                            hmdtyFirst.setText(humidityList.get(0));
                            hmdtysecond.setText(humidityList.get(1));
                            hmdtythird.setText(humidityList.get(2));
                            hmdtyfourth.setText(humidityList.get(3));
                            hmdtyfifth.setText(humidityList.get(4));
                            hmdtysixth.setText(humidityList.get(5));
                            hmdtyseventh.setText(humidityList.get(6));
                            hmdtyeighth.setText(humidityList.get(7));
                            uvFirst.setText(uvIndexList.get(0));
                            uvsecond.setText(uvIndexList.get(1));
                            uvthird.setText(uvIndexList.get(2));
                            uvfourth.setText(uvIndexList.get(3));
                            uvfifth.setText(uvIndexList.get(4));
                            uvsixth.setText(uvIndexList.get(5));
                            uvseventh.setText(uvIndexList.get(6));
                            uveighth.setText(uvIndexList.get(7));
                            String firstImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(0) + ".png";
                            String secondImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(1) + ".png";
                            String thirdImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(2) + ".png";
                            String fourthImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(3) + ".png";
                            String fifthImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(4) + ".png";
                            String sixthImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(5) + ".png";
                            String seventhImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(6) + ".png";
                            String eighthImageurl = "http://my.ag.tools/agtools/images/climate/" + imagesList.get(7) + ".png";
                            System.out.println("first image url.." + firstImageurl);

                            Glide.with(DashboardActivity.this).load(firstImageurl).into(img1);
                            Glide.with(DashboardActivity.this).load(secondImageurl).into(img2);
                            Glide.with(DashboardActivity.this).load(thirdImageurl).into(img3);
                            Glide.with(DashboardActivity.this).load(fourthImageurl).into(img4);
                            Glide.with(DashboardActivity.this).load(fifthImageurl).into(img5);
                            Glide.with(DashboardActivity.this).load(sixthImageurl).into(img6);
                            Glide.with(DashboardActivity.this).load(seventhImageurl).into(img7);
                            Glide.with(DashboardActivity.this).load(eighthImageurl).into(img8);
                            String highlowfirst = (hightemparature1 + tempType) + "/" + lowtemparature1 + tempType;
                            String highlowSecond = hightemparature2 + tempType + "/" + lowtemparature2 + tempType;
                            String highlowthird = hightemparature3 + tempType + "/" + lowtemparature3 + tempType;
                            String highlowFourth = hightemparature4 + tempType + "/" + lowtemparature4 + tempType;
                            String highlowFifth = hightemparature5 + tempType + "/" + lowtemparature5 + tempType;
                            String highlowSixth = hightemparature6 + tempType + "/" + lowtemparature6 + tempType;
                            String highlowSeventh = hightemparature7 + tempType + "/" + lowtemparature7 + tempType;
                            String highlowEighth = hightemparature8 + tempType + "/" + lowtemparature8 + tempType;
                            highlowFirst.setText(highlowfirst);
                            highlowsecond.setText(highlowSecond);
                            highlowThird.setText(highlowthird);
                            highlowfourth.setText(highlowFourth);
                            highlowfifth.setText(highlowFifth);
                            highlowsixth.setText(highlowSixth);
                            highlowseventh.setText(highlowSeventh);
                            highloweighth.setText(highlowEighth);

                            tempFirst.setBackgroundColor(Color.parseColor(colorsList.get(0)));
                            tempsecond.setBackgroundColor(Color.parseColor(colorsList.get(1)));
                            tempThird.setBackgroundColor(Color.parseColor(colorsList.get(2)));
                            tempfourth.setBackgroundColor(Color.parseColor(colorsList.get(3)));
                            tempfifth.setBackgroundColor(Color.parseColor(colorsList.get(4)));
                            tempsixth.setBackgroundColor(Color.parseColor(colorsList.get(5)));
                            tempseventh.setBackgroundColor(Color.parseColor(colorsList.get(6)));
                            tempeighth.setBackgroundColor(Color.parseColor(colorsList.get(7)));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error Response:" + error);
                       // CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
                requestQueue.add(jsonArrayRequest);

            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please Enable Your Mobile Data",Toast.LENGTH_LONG).show();

        }

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Volume

    public void volumeIntialization() {
          volumehistoric=findViewById(R.id.exchangevolume);
          volumemonth=findViewById(R.id.exchangevolumemonth);
          volumeyear=findViewById(R.id.exchangevolumeyear);
        volumehistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volumemonth.setVisibility(View.INVISIBLE);
                volumeyear.setVisibility(View.INVISIBLE);
                Intent intent=new Intent(getApplicationContext(),VolumeWebActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
        relativeVolume = (LinearLayout) findViewById(R.id.relative_buttons);
        relative_resp = (LinearLayout) findViewById(R.id.relative_resp);
        relative_resp.setVisibility(View.INVISIBLE);
        listViewVolume = (ListView) findViewById(R.id.listViewVolume);
       // listViewVolume1 = (ListView) findViewById(R.id.listViewVolume1);
       // listViewVolume2 = (ListView) findViewById(R.id.listViewVolume2);
        button_weekly = (Button) findViewById(R.id.button_weekly);
        lasttext=(TextView)findViewById(R.id.last);
        button_monthly = (Button) findViewById(R.id.button_monthly);
        button_yearly = (Button) findViewById(R.id.button_yearly);
        volumeResponseList = new ArrayList<>();
        volumeResponseList1 = new ArrayList<>();
        volumeResponseList2 = new ArrayList<>();
        button_weekly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangularbutton));
        getVolumes(0);

    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void volumeClick(View view) {
        String volume_status = view.getTag().toString();
        System.out.println("volume status...." + volume_status);
        if (Integer.parseInt(volume_status) == 0) {
            button_weekly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangularbutton));
            button_monthly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            button_yearly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            volumehistoric.setVisibility(View.VISIBLE);
            getVolumes(0);
            volumehistoric.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    volumemonth.setVisibility(View.INVISIBLE);
                    volumeyear.setVisibility(View.INVISIBLE);
                    volumehistoric.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(getApplicationContext(),VolumeWebActivity.class);
                    intent.putExtra("commodity",selctCommidtyName);
                    startActivity(intent);
                }
            });
        } else if (Integer.parseInt(volume_status) == 1) {
            button_weekly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            button_monthly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangularbutton));
            button_yearly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            volumemonth.setVisibility(View.VISIBLE);
            getVolumes(1);
            volumemonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    volumehistoric.setVisibility(View.INVISIBLE);
                    volumeyear.setVisibility(View.INVISIBLE);
                    volumemonth.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(getApplicationContext(),VolumeWebMonthActivity.class);
                    intent.putExtra("commodity",selctCommidtyName);
                    startActivity(intent);
                }
            });
        } else if (Integer.parseInt(volume_status) == 2) {
            button_weekly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            button_monthly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangbuttonnonselect));
            button_yearly.setBackground(DashboardActivity.this.getResources().getDrawable(R.drawable.rectangularbutton));
            getVolumes(2);
            volumeyear.setVisibility(View.VISIBLE);
            volumeyear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    volumehistoric.setVisibility(View.INVISIBLE);
                    volumemonth.setVisibility(View.INVISIBLE);
                    volumeyear.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(getApplicationContext(),VolumeWebYearActivity.class);
                    intent.putExtra("commodity",selctCommidtyName);
                    startActivity(intent);
                }
            });
        }

    }

    public void getVolumes(int volumeType) {

        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            String pattern = "yyyy-MM-dd";
            final String current_date = new SimpleDateFormat(pattern).format(new Date());
            System.out.println("currentDate:" + current_date);
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {

                 Volume_Get_URL = ApiConstants.GetVolume_URL + selctCommidtyName + "&date=" + current_date + "&reportType=" + volumeType;
                System.out.println("volume new url...." + Volume_Get_URL);
                pd.show();
                NukeSSLCerts.NukeSSLCerts();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Volume_Get_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("weather resp:" + response);
                        pd.dismiss();
                        volumeResponseList.clear();
                        System.out.println("response for volume....:" + response);
                        relative_resp.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = null;
                        if (response.length()>0)
                        {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    jsonObject = response.getJSONObject(i);
                                    String location = jsonObject.getString("location");
                                    String totalCount = jsonObject.getString("totalCount");
                                    volumeResponse = new VolumeResponse(String.valueOf(i+1),location,totalCount);
                                    volumeResponseList.add(volumeResponse);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                JSONObject jsonObject1=response.getJSONObject(0);
                                lastupdate=jsonObject1.getString("volumeDate");
                                lasttext.setText("LastUpdated On :"+lastupdate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            VolumeAdapter volumeAdapter = new VolumeAdapter(DashboardActivity.this, volumeResponseList);
                            listViewVolume.setAdapter(volumeAdapter);
                            volumeAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Volumes for Present Date",Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);
                        pd.dismiss();

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        }
    }

    //Shipping price

    public void shippingpriceIntialization() {
        Button button=findViewById(R.id.exchangeshipping);
         favShipping=findViewById(R.id.favShipping);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ShippingWebActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
        relativeshipping = (RelativeLayout) findViewById(R.id.relativemain);
        relativeshipping.setVisibility(View.INVISIBLE);
        spinnershippingType = (Spinner) findViewById(R.id.spinnershippingType);
        spinnerlocation = (Spinner) findViewById(R.id.spinnerlocation);
        listShipping = (ListView) findViewById(R.id.listShipping);
        horizontalscrollshipping = (HorizontalScrollView) findViewById(R.id.horizontalscrollshipping);
        horizontalscrollshipping.setVisibility(View.INVISIBLE);
        locationsList = new ArrayList<>();
        //locationsList.add("Location");
//        originList.add("Origon");
        shippingResponseList = new ArrayList<>();
        spinnershippingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectShippingType = shippingType[position];
                shippingTypeName = selectShippingType.toString();
                if (locationName != null && !locationName.equals("")) {
                    getShippingPrice();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         shiptypeAda = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shippingType);
        shiptypeAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershippingType.setAdapter(shiptypeAda);
        getLocationsShipping();

        favShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                final String responce=sharedPreferences1.getString("userid","");
                Log.d("userid","userid"+responce);
                //Map<String, String> params = new HashMap<String, String>();
                JSONObject params=new JSONObject();
                try {
                    params.put("widgetId","3" );
                    params.put("commodityName",selctCommidtyName );
                    params.put("userId", responce);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("paramValue", selectShippingType);
                    jsonObject1.put("paramKey", "type");
                    Log.d("", "" + jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("paramValue", locationFav);
                    jsonObject2.put("paramKey", "location");
                    Log.d("", "" + jsonObject2);
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("paramValue", "2018-04-13");
                    jsonObject3.put("paramKey", "date");
                    Log.d("", "" + jsonObject3);
                    jsonArray=new JSONArray();
                    jsonArray.put(jsonObject1);
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject3);
                    Log.d("js", "js" + jsonArray);
                    params.put("options", jsonArray);
                    Log.d("favshipping", "favshipping" + params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.show();
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ApiConstants.Fav_Url, params, null, null){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        pd.dismiss();
                        mStatusCode = response.statusCode;
                        Log.d("",""+mStatusCode);
                        try {
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Log.d("",""+json);
                            if(json.equals(""))
                            {
                                Toaster.toast("Filter Saved");
                                pd.dismiss();
                            }
                            else {
                                Toaster.toast("Filter Not Saved");
                                pd.dismiss();
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

    public void getLocationsShipping() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
                String newLocationURl = ApiConstants.Locactions_Shippingprice_URL + selctCommidtyName + "/1";
              pd.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newLocationURl, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        if (response.length()>0)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONArray jsonArrayLocations = jsonObject.getJSONArray("location");
                                System.out.println("locationssssssssss:" + jsonArrayLocations);
                                for (int i = 0; i < jsonArrayLocations.length(); i++) {
                                    System.out.println("locationssss:" + jsonArrayLocations.get(i).toString());

                                    locationsList.add(jsonArrayLocations.get(i).toString());

                                }
                                locationsAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, locationsList);
                                spinnerlocation.setAdapter(locationsAdapter);
                                spinnerlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        locationName = locationsList.get(position).toString();
                                        if (locationName != null && !locationName.equals("")) {
                                            getShippingPrice();
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"There is No Data",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        }
        if(finalcheckuser!=null)
        {
            try {
                JSONObject jsonObject=new JSONObject(finalcheckuser);
                JSONArray jsonArray=jsonObject.getJSONArray("preferences");
                Log.d("",""+jsonArray);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    if(selctCommidtyName.equals(jsonObject1.getString("commodityName")))
                    {
                        if(jsonObject1.getString("widgetId").equals("3"))
                        {
                            JSONArray jsonObject2=jsonObject1.getJSONArray("options");
                            Log.d("",""+jsonObject2);
                            for (int j=0;j<jsonObject2.length();j++) {
                                JSONObject jsonObject3 = jsonObject2.getJSONObject(j);
                                Log.d("", "" + jsonObject3);
                                if (jsonObject3.getString("paramKey").equals("type")) {
                                    shippingTypeName = jsonObject3.getString("paramValue");
                                    Log.d("", "" + shippingTypeName);
                                    int spinnerPosition = shiptypeAda.getPosition(shippingTypeName);
                                    System.out.println("spinner is -" + spinnerPosition + "-");
                                    spinnershippingType.setSelection(spinnerPosition);

                                } else if (jsonObject3.getString("paramKey").equals("location")) {
                                    locationName1 = jsonObject3.getString("paramValue");
                                    Log.d("", "" + locationName1);
                                    String newLocationURl = ApiConstants.Locactions_Shippingprice_URL + selctCommidtyName + "/1";
                                    pd.show();
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newLocationURl, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    pd.dismiss();
                                                    if (response.length()>0)
                                                    {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response.toString());
                                                            JSONArray jsonArrayLocations = jsonObject.getJSONArray("location");
                                                            System.out.println("locationssssssssss:" + jsonArrayLocations);
                                                            for (int i = 0; i < jsonArrayLocations.length(); i++) {
                                                                System.out.println("locationssss:" + jsonArrayLocations.get(i).toString());

                                                                locationsList.add(jsonArrayLocations.get(i).toString());

                                                            }
                                                            locationsAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, locationsList);
                                                            spinnerlocation.setAdapter(locationsAdapter);
                                                            int spinnerPosition = locationsAdapter.getPosition(locationName1);
                                                            System.out.println("spinner is -" + spinnerPosition + "-");
                                                            spinnerlocation.setSelection(spinnerPosition);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(),"There is No Data",Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);

                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                }


                            }
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {

        }
    }

    public void getShippingPrice() {
        System.out.println("locationnnnnnnnnn" + locationName);
        locationFav=locationName;
        locationName = locationName.replaceAll("&", "%26");
        locationName = locationName.replaceAll(" ", "%20");
        newShippingPriceurl = ApiConstants.ShippingPriceGet_URL + shippingTypeName + "&location=" + locationName + "&commodity=" + selctCommidtyName;
        if (newShippingPriceurl.contains("&") && newShippingPriceurl.contains(" ")) {
            locationName = locationName.replaceAll("&", "%26");
            locationName = locationName.replaceAll(" ", "%20");
            System.out.println("new urllllllll" + locationName);
        }

        if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
            shippingResponseList.clear();
            // newShippingPriceurl = ApiConstants.ShippingPriceGet_URL + shippingTypeName + "&location=" + locationName + "&commodity=" + selctCommidtyName;
            System.out.println("newShippingPriceurl urllllllll" + newShippingPriceurl);
            if (InternetChecking.isInternetOn(DashboardActivity.this)) {
                pd.show();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, newShippingPriceurl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("weather resp:" + response);
                        shippingResponseList.clear();
                        pd.dismiss();
                        if(response.length()>0)
                        {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String report_date = jsonObject.getString("report_date");
                                    String packageName = jsonObject.getString("packageName");
                                    String type = jsonObject.getString("type");
                                    String low_high_price = jsonObject.getString("low_high_price");
                                    String size = jsonObject.getString("size");
                                    try {
                                        SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = sdf.parse(report_date);
                                        updatedDateFormat = month_date.format(date);
                                        System.out.println("date44444444444444444444444" + updatedDateFormat);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    shippingResponse = new ShippingResponse(updatedDateFormat.toString(), packageName, type, low_high_price, size);
                                    shippingResponseList.add(shippingResponse);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ShippingAdapter shippingAdapter = new ShippingAdapter(DashboardActivity.this, shippingResponseList);
                            listShipping.setAdapter(shippingAdapter);
                            horizontalscrollshipping.setVisibility(View.VISIBLE);
                            relativeshipping.setVisibility(View.VISIBLE);
                            shippingAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
        } else {
            CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
        }
    }


    //Exchange

    public void exchangerateIntialization() {
        relative_exchange = (LinearLayout) findViewById(R.id.relative_exchange);
        exchangehistoric= (Button) findViewById(R.id.exchangehistoric);
        relative_exchange.setVisibility(View.INVISIBLE);
        exchangeRateModalList = new ArrayList<>();
        listViewExchange = (ListView) findViewById(R.id.listViewExchange);

        getExchanges();
        exchangehistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(DashboardActivity.this,ExchangeHistoricActivity.class);
                in.putExtra("commodity",selctCommidtyName);
                startActivity(in);
            }

        });
    }

    public void getExchanges() {

        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {

                System.out.println("exchange url...." + ApiConstants.ExchangesGet_URL);
                pd.show();
                NukeSSLCerts.NukeSSLCerts();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiConstants.ExchangesGet_URL, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        relative_exchange.setVisibility(View.VISIBLE);
                        exchangeRateModalList.clear();
                        JSONArray jsonArrayMexico = null;
                        JSONArray jsonArrayCanada=null;
                        int mexicoLength = 0;
                        int canadalength=0;
                        if(response.length()>0)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                if (response.has("Canada")) {
                                     jsonArrayCanada = jsonObject.getJSONArray("Canada");
                                     canadalength = jsonArrayCanada.length();
                                }
                                else {

                                }

                                if (response.has("Mexico")) {
                                     jsonArrayMexico = jsonObject.getJSONArray("Mexico");
                                     mexicoLength = jsonArrayMexico.length();
                                }
                                else {

                                }


                               

                                if (mexicoLength > canadalength) {
                                    for (int i = 0; i < mexicoLength; i++) {
                                        JSONObject exchangemexico = jsonArrayMexico.getJSONObject(i);
                                        String date = exchangemexico.getString("date");
                                        rateValueMexico = exchangemexico.getString("rateValue");
                                        exchangeRateModal = new ExchangeRateModal(date, "", rateValueMexico);
                                        exchangeRateModalList.add(exchangeRateModal);
                                    }
                                    for (int j = 0; j < canadalength; j++) {
                                        JSONObject exchangeCanada = jsonArrayCanada.getJSONObject(j);
                                        rateValueCanada = exchangeCanada.getString("rateValue");
                                        System.out.println("rateValueCanada" + rateValueCanada);
                                        exchangeRateModalList.set(j, new ExchangeRateModal(exchangeRateModalList.get(j).getLastUpdate(), rateValueCanada, exchangeRateModalList.get(j).getMexicoExchange()));
                                        exchangeRateModalList.add(exchangeRateModal);
                                    }
                                } else if (canadalength > mexicoLength) {
                                    for (int i = 0; i < canadalength; i++) {
                                        JSONObject exchangeCanada = jsonArrayCanada.getJSONObject(i);
                                        String date = exchangeCanada.getString("date");
                                        rateValueCanada = exchangeCanada.getString("rateValue");
                                        System.out.println("rateValueCanada" + rateValueCanada);
                                        exchangeRateModal = new ExchangeRateModal(date, rateValueCanada, "");
                                        exchangeRateModalList.add(exchangeRateModal);
                                    }

                                    for (int j = 0; j < mexicoLength; j++) {
                                        JSONObject exchangemexico = jsonArrayMexico.getJSONObject(j);
                                        rateValueMexico = exchangemexico.getString("rateValue");
                                        exchangeRateModalList.set(j, new ExchangeRateModal(exchangeRateModalList.get(j).getLastUpdate(), exchangeRateModalList.get(j).getCanadaExchange(), rateValueMexico));
                                        exchangeRateModalList.add(exchangeRateModal);
                                    }
                                } else {
                                    for (int i = 0; i < jsonArrayCanada.length(); i++) {
                                        JSONObject exchangeCanada = jsonArrayCanada.getJSONObject(i);
                                        String date = exchangeCanada.getString("date");
                                        rateValueCanada = exchangeCanada.getString("rateValue");
                                        JSONObject exchangemexico = jsonArrayMexico.getJSONObject(i);
                                        String rateValueMexico = exchangemexico.getString("rateValue");
                                        exchangeRateModal = new ExchangeRateModal(date, rateValueCanada, rateValueMexico);
                                        exchangeRateModalList.add(exchangeRateModal);
                                    }
                                }
                                HashSet hs = new HashSet();
                                hs.addAll(exchangeRateModalList); // demoArrayList= name of arrayList from which u want to remove duplicates
                                exchangeRateModalList.clear();
                                exchangeRateModalList.addAll(hs);
                                exchangeAdapter = new ExchangeAdapter(DashboardActivity.this, exchangeRateModalList);
                                listViewExchange.setAdapter(exchangeAdapter);
                                exchangeAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            exchangeRateModalList.clear();
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        }

    }

    public void terminalInitialization() {
        Button button=findViewById(R.id.exchangeterminal);
        favTerminal=findViewById(R.id.favterminal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TerminalWebActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
        locationsList = new ArrayList<>();
        originList = new ArrayList<>();
        terminalPriceResponseList = new ArrayList<>();
        spinnerterminalType = (Spinner) findViewById(R.id.spinnerterminalType);
        spinnerterminalorigin = (Spinner) findViewById(R.id.spinnerterminalorigin);
        spinnerterminallocation = (Spinner) findViewById(R.id.spinnerterminallocation);
        listterminal = (ListView) findViewById(R.id.listterminal);
        spinnerterminalType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectTerminalType = terminalType[position];
                terminalTypeName = selectTerminalType.toString();

                System.out.println("selectTerminalType77777777777777777" + terminalTypeName);
                if (terminalOriginName != null && !terminalOriginName.equals("") && terminalLocationName != null && !terminalLocationName.equals("") && terminalTypeName != null && !terminalTypeName.equals("")) {
                    System.out.println("call222222222222222222222222");
                    terminalPriceResponseList.clear();
                    getTerminalPrice();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         terminalTypeAdapter = new ArrayAdapter(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, terminalType);
        terminalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerterminalType.setAdapter(terminalTypeAdapter);
        getLocationsTerminalPrice();
        favTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                final String responce=sharedPreferences1.getString("userid","");
                Log.d("userid","userid"+responce);
                //Map<String, String> params = new HashMap<String, String>();
                JSONObject params=new JSONObject();
                try {
                    params.put("widgetId","4" );
                    params.put("commodityName",selctCommidtyName );
                    params.put("userId", responce);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("paramValue", terminalTypeName);
                    jsonObject1.put("paramKey", "type");
                    Log.d("", "" + jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("paramValue", terminalOriginName);
                    jsonObject2.put("paramKey", "origin");
                    Log.d("", "" + jsonObject2);
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("paramValue", terminalLocationName);
                    jsonObject3.put("paramKey", "location");
                    Log.d("", "" + jsonObject3);
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("paramValue", "2018-04-13");
                    jsonObject4.put("paramKey", "date");
                    Log.d("", "" + jsonObject4);
                    jsonArray=new JSONArray();
                    jsonArray.put(jsonObject1);
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject3);
                    jsonArray.put(jsonObject4);
                    Log.d("js", "js" + jsonArray);
                    params.put("options", jsonArray);
                    Log.d("favshipping", "favshipping" + params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.show();
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ApiConstants.Fav_Url, params, null, null){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        pd.dismiss();
                        mStatusCode = response.statusCode;
                        Log.d("",""+mStatusCode);
                        try {
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Log.d("",""+json);
                            if(json.equals(""))
                            {
                                Toaster.toast("Filter Saved");
                                pd.dismiss();
                            }
                            else {
                                Toaster.toast("Filter Not Saved");
                                pd.dismiss();
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

    public void getLocationsTerminalPrice() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {

                String newTerminalLocationURL = ApiConstants.Terminal_location_URL + selctCommidtyName + "/1";
               pd.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newTerminalLocationURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        System.out.println("response for terminal locations....:" + response);
                        try {
                            JSONObject jsonObjectTerminals = new JSONObject(response.toString());
                            JSONArray jsonArrayLocations = jsonObjectTerminals.getJSONArray("location");
                            JSONArray jsonArrayOrigin = jsonObjectTerminals.getJSONArray("origin");
                            for (int i = 0; i < jsonArrayLocations.length(); i++) {
                                System.out.println("locationssss:" + jsonArrayLocations.get(i).toString());
                                locationsList.add(jsonArrayLocations.get(i).toString());
                            }
                            locationsAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_item, locationsList);
                            spinnerterminallocation.setAdapter(locationsAdapter);
                            spinnerterminallocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    terminalLocationName = locationsList.get(position).toString();
                                    System.out.println("terminal locations:" + terminalLocationName);
                                    if (terminalOriginName != null && !terminalOriginName.equals("") && terminalLocationName != null && !terminalLocationName.equals("") && terminalTypeName != null && !terminalTypeName.equals("")) {
                                        System.out.println("call222222222222222222222222");
                                        terminalPriceResponseList.clear();
                                        getTerminalPrice();
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            for (int j = 0; j < jsonArrayOrigin.length(); j++) {

                                originList.add(jsonArrayOrigin.get(j).toString());
                            }
                            originAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, originList);
                            spinnerterminalorigin.setAdapter(originAdapter);
                            spinnerterminalorigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    terminalOriginName = originList.get(position).toString();
                                    System.out.println("terminal locations:" + terminalOriginName);
                                    if (terminalOriginName != null && !terminalOriginName.equals("") && terminalLocationName != null && !terminalLocationName.equals("") && terminalTypeName != null && !terminalTypeName.equals("")) {
                                        System.out.println("call222222222222222222222222");
                                        terminalPriceResponseList.clear();
                                        getTerminalPrice();
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);
                        CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        }
        if(finalcheckuser!=null)
        {
            try {
                JSONObject jsonObject=new JSONObject(finalcheckuser);
                JSONArray jsonArray=jsonObject.getJSONArray("preferences");
                Log.d("",""+jsonArray);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    if(selctCommidtyName.equals(jsonObject1.getString("commodityName")))
                    {
                        if(jsonObject1.getString("widgetId").equals("4"))
                        {
                            JSONArray jsonObject2=jsonObject1.getJSONArray("options");
                            Log.d("",""+jsonObject2);
                            for (int j=0;j<jsonObject2.length();j++) {
                                JSONObject jsonObject3 = jsonObject2.getJSONObject(j);
                                Log.d("", "" + jsonObject3);
                                if (jsonObject3.getString("paramKey").equals("type")) {
                                    terminalTypeName = jsonObject3.getString("paramValue");
                                    Log.d("", "" + terminalTypeName);
                                    int spinnerPosition = terminalTypeAdapter.getPosition(terminalTypeName);
                                    System.out.println("spinner is -" + spinnerPosition + "-");
                                    spinnerterminalType.setSelection(spinnerPosition);

                                }
                                if (jsonObject3.getString("paramKey").equals("origin")) {
                                    locationName1 = jsonObject3.getString("paramValue");
                                    Log.d("", "" + locationName1);
                                    String newTerminalLocationURL = ApiConstants.Terminal_location_URL + selctCommidtyName + "/1";
                                    pd.show();
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newTerminalLocationURL, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            pd.dismiss();
                                            System.out.println("response for terminal locations....:" + response);
                                            try {
                                                JSONObject jsonObjectTerminals = new JSONObject(response.toString());
                                                JSONArray jsonArrayLocations = jsonObjectTerminals.getJSONArray("location");
                                                JSONArray jsonArrayOrigin = jsonObjectTerminals.getJSONArray("origin");
                                                for (int i = 0; i < jsonArrayLocations.length(); i++) {
                                                    System.out.println("locationssss:" + jsonArrayLocations.get(i).toString());
                                                    locationsList.add(jsonArrayLocations.get(i).toString());
                                                }
                                                locationsAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_item, locationsList);
                                                spinnerterminallocation.setAdapter(locationsAdapter);
                                                int spinnerPosition = locationsAdapter.getPosition(locationName1);
                                                System.out.println("spinner is -" + spinnerPosition + "-");
                                                spinnerterminallocation.setSelection(spinnerPosition);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);
                                            CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                }
                                 if(jsonObject3.getString("paramKey").equals("location"))
                                {
                                    locationorigon = jsonObject3.getString("paramValue");
                                    Log.d("", "" + locationorigon);
                                    String newTerminalLocationURL = ApiConstants.Terminal_location_URL + selctCommidtyName + "/1";
                                    pd.show();
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newTerminalLocationURL, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            pd.dismiss();
                                            System.out.println("response for terminal locations....:" + response);
                                            try {
                                                JSONObject jsonObjectTerminals = new JSONObject(response.toString());
                                                JSONArray jsonArrayLocations = jsonObjectTerminals.getJSONArray("location");
                                                JSONArray jsonArrayOrigin = jsonObjectTerminals.getJSONArray("origin");
                                                for (int j = 0; j < jsonArrayOrigin.length(); j++) {

                                                    originList.add(jsonArrayOrigin.get(j).toString());
                                                }
                                                originAdapter = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, originList);
                                                spinnerterminalorigin.setAdapter(originAdapter);
                                                int spinnerPosition = originAdapter.getPosition(locationorigon);
                                                System.out.println("spinner is -" + spinnerPosition + "-");
                                                spinnerterminalorigin.setSelection(spinnerPosition);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);
                                            CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                }


                            }
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {

        }
    }

    public void getTerminalPrice() {
        terminalPriceResponseList.clear();
        if (terminalLocationName.contains(" ")) {
            terminalLocationName.replaceAll(" ", "%20");
        }
        if (terminalOriginName.contains(" ")) {
            terminalOriginName.replaceAll(" ", "%20");
        }
        terminalPriceResponseList.clear();
        if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
            terminalPriceResponseList.clear();
            String newShippingPriceurl = ApiConstants.TerminalPrice_URL + terminalTypeName + "&origin=" + terminalOriginName + "&location=" + terminalLocationName + "&commodity=" + selctCommidtyName;

            if (InternetChecking.isInternetOn(DashboardActivity.this)) {
               pd.show();
                terminalPriceResponseList.clear();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, newShippingPriceurl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        terminalPriceResponseList.clear();
                        System.out.println("weather resp:" + response);
                        pd.dismiss();
                        terminalPriceResponseList.clear();
                        if (response.length() > 0) {
                            for (int k = 0; k < response.length(); k++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(k);
                                    String terLastUpdateDate = jsonObject.getString("report_date");
                                    String terPackage = jsonObject.getString("packageName");
                                    String terType = jsonObject.getString("type");
                                    String terLowHigh = jsonObject.getString("low_high_price");
                                    String terOrigin = jsonObject.getString("origin");
                                    String terSize = jsonObject.getString("size");
                                    try {
                                        SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = sdf.parse(terLastUpdateDate);
                                        updatedDateFormat = month_date.format(date);
                                        System.out.println("date44444444444444444444444" + updatedDateFormat);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    terminalPriceResponse = new TerminalPriceResponse(updatedDateFormat.toString(), terPackage, terType, terLowHigh, terOrigin, terSize);
                                    terminalPriceResponseList.add(terminalPriceResponse);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            TerminalPriceAdapter terminalPriceAdapter = new TerminalPriceAdapter(DashboardActivity.this, terminalPriceResponseList);
                            listterminal.setAdapter(terminalPriceAdapter);
                            terminalPriceAdapter.notifyDataSetChanged();
                        }
                        else {
                            terminalPriceResponseList.clear();
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
        } else {
            CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
        }
    }

    //Weather Alerts
    public void weatherAlertInitialization() {
        textViewweatherAlert = (TextView) findViewById(R.id.textViewweatherAlert);
        weatherAlert();
    }

    public void weatherAlert() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            if (selctCommidtyName != null && !selctCommidtyName.equals("")) {
               pd.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiConstants.Weather_alert_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String weatherAlert = jsonObject.getString("weatherAlert");
                            System.out.println("weather alert:" + weatherAlert);
                            textViewweatherAlert.setText(weatherAlert);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Error weather: " + error);

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                CustomToast.displayMessage(DashboardActivity.this, CustomToast.Select_Commudity);
            }
        }
    }

    public void liveWeatherInitialization() {
        editLivWeather3 = (EditText) findViewById(R.id.editLivWeather3);
        editLivWeather2 = (EditText) findViewById(R.id.editLivWeather2);
        editLivWeather1 = (EditText) findViewById(R.id.editLivWeather1);
        imgPlace1Clear = (ImageView) findViewById(R.id.imgPlace1Clear);
        imgPlace2Clear = (ImageView) findViewById(R.id.imgPlace2Clear);
        imgPlace3Clear = (ImageView) findViewById(R.id.imgPlace3Clear);
        favlive=findViewById(R.id.favlive);
        placesModalList = new ArrayList<>();
        horizontalliveweather = (HorizontalScrollView) findViewById(R.id.horizontalliveweather);
        relativeCity1 = (RelativeLayout) findViewById(R.id.relativeCity1);
        relativeLiveCity2 = (RelativeLayout) findViewById(R.id.relativeLiveCity2);
        relativeLiveCity3 = (RelativeLayout) findViewById(R.id.relativeLiveCity3);
        listPlacesLive = (ListView) findViewById(R.id.listPlacesLive);
        listPlacesLive.setVisibility(View.GONE);
        relativeWeatherLive = (RelativeLayout) findViewById(R.id.relativeWeatherLive);
        buttonliveweather = (Button) findViewById(R.id.buttonliveweather);
        relativelive1 = (RelativeLayout) findViewById(R.id.relativelive1);
        relativelive2 = (RelativeLayout) findViewById(R.id.relativelive2);
        relativelive3 = (RelativeLayout) findViewById(R.id.relativelive3);
        textcity1 = (TextView) findViewById(R.id.textcity1);
        textcity2 = (TextView) findViewById(R.id.textcity2);
        textcity3 = (TextView) findViewById(R.id.textcity3);
        texttemp1 = (TextView) findViewById(R.id.texttemp1);
        texttemp2 = (TextView) findViewById(R.id.texttemp2);
        texttemp3 = (TextView) findViewById(R.id.texttemp3);
        textpreci1 = (TextView) findViewById(R.id.textpreci1);
        textpreci2 = (TextView) findViewById(R.id.textpreci2);
        textpreci3 = (TextView) findViewById(R.id.textpreci3);
        textwindspeed1 = (TextView) findViewById(R.id.textwindspeed1);
        textwindspeed2 = (TextView) findViewById(R.id.textwindspeed2);
        textwindspeed3 = (TextView) findViewById(R.id.textwindspeed3);
        textlivehumidity1 = (TextView) findViewById(R.id.textlivehumidity1);
        textlivehumidity2 = (TextView) findViewById(R.id.textlivehumidity2);
        textlivehumidity3 = (TextView) findViewById(R.id.textlivehumidity3);
        textlivedewpoint1 = (TextView) findViewById(R.id.textlivedewpoint1);
        textlivedewpoint2 = (TextView) findViewById(R.id.textlivedewpoint2);
        textlivedewpoint3 = (TextView) findViewById(R.id.textlivedewpoint3);
        textliveuvIndex1 = (TextView) findViewById(R.id.textliveuvIndex1);
        textliveuvIndex2 = (TextView) findViewById(R.id.textliveuvIndex2);
        textliveuvIndex3 = (TextView) findViewById(R.id.textliveuvIndex3);

        relativelive1.setVisibility(View.VISIBLE);
        relativelive2.setVisibility(View.VISIBLE);
        relativelive3.setVisibility(View.VISIBLE);
        imgPlace1Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLivWeather1.setText("");
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY1);
            }
        });
        imgPlace2Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLivWeather2.setText("");
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY2);
            }
        });
        imgPlace3Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLivWeather3.setText("");
                SPHelper.removeValueFROMPrefs(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY3);
            }
        });
        placeNameintentLive1 = SPHelper.getFromSP(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY1);
        placeNameintentLive2 = SPHelper.getFromSP(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY2);
        placeNameintentLive3 = SPHelper.getFromSP(DashboardActivity.this, SPHelper.MyPREFERENCES, KEY_LIVEWEATHERCITY3);
        if (placeNameintentLive1 != null && !placeNameintentLive1.equals("")) {
            editLivWeather1.setText(placeNameintentLive1);
            getLatitudeLongitudeValues1(placeNameintentLive1);
        }
        if (placeNameintentLive2 != null && !placeNameintentLive2.equals("")) {
            editLivWeather2.setText(placeNameintentLive2);
            getLatitudeLongitudeValues2(placeNameintentLive2);
        }
        if (placeNameintentLive3 != null && !placeNameintentLive3.equals("")) {
            editLivWeather3.setText(placeNameintentLive3);
            getLatitudeLongitudeValues3(placeNameintentLive3);
        }
        if(finalcheckuser!=null)
        {
            try {
                JSONObject jsonObject=new JSONObject(finalcheckuser);
                JSONArray jsonArray=jsonObject.getJSONArray("preferences");
                Log.d("",""+jsonArray);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    if(selctCommidtyName.equals(jsonObject1.getString("commodityName")))
                    {
                        if(jsonObject1.getString("widgetId").equals("2"))
                        {
                            JSONArray jsonObject2=jsonObject1.getJSONArray("options");
                            Log.d("",""+jsonObject2);
                            for (int j=0;j<jsonObject2.length();j++) {
                                JSONObject jsonObject3 = jsonObject2.getJSONObject(j);
                                Log.d("", "" + jsonObject3);
                                if (jsonObject3.getString("paramKey").equals("location1")) {
                                    String name = jsonObject3.getString("paramValue");
                                    JSONObject jsonObject5 = new JSONObject(name);
                                    place1 = jsonObject5.getString("city");
                                    city1latstrng=jsonObject5.getString("latitude");
                                    city1lngstrng=jsonObject5.getString("longitude");
                                    Log.d("", "" + place1);
                                    liveWeathertext1 = editLivWeather1.getText().toString();
                                    editLivWeather1.setText(place1);
                                    liveWeatherUrl1 = ApiConstants.LiveWeather_URL + city1latstrng + "/" + city1lngstrng+ "/e";
                                    Log.d("",""+liveWeatherUrl1);
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, liveWeatherUrl1, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println("response for live weather....:" + response);
                                            relativeCity1.setVisibility(View.VISIBLE);
                                            relativeWeatherLive.setVisibility(View.VISIBLE);
                                            horizontalliveweather.setVisibility(View.VISIBLE);
                                            try {
                                                live1Temp = response.getString("temp");
                                                live1preciption = response.getString("precipitation");
                                                live1windSpeed = response.getString("windspeed");
                                                live1Humidity = response.getString("humidity");
                                                live1dewPoint = response.getString("dewPoint");
                                                live1Uv = response.getString("uvIndex");
                                                texttemp1.setText(live1Temp + "F");
                                                textpreci1.setText(live1preciption);
                                                textwindspeed1.setText(live1windSpeed);
                                                textlivehumidity1.setText(live1Humidity);
                                                textlivedewpoint1.setText(live1dewPoint);
                                                textliveuvIndex1.setText(live1Uv);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);

                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("place11", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("p1", place1);
                                    editor.apply();
                                    String place=pref.getString("p1", null);
                                   // place = place.replaceAll(" ", "%20");
                                    if (place != null && !place.equals("")) {
                                        editLivWeather1.setText(place);
                                        textcity1.setText(place);
                                        //getLatitudeLongitudeValues1(place);
                                    }

                                   liveWeathertext1 = editLivWeather1.getText().toString();
                                   liveWeathertext1 = liveWeathertext1.replaceAll(" ", "%20");
                                   // googleLiveWeather1(liveWeathertext1);
                                } else if (jsonObject3.getString("paramKey").equals("location2")) {
                                    String name = jsonObject3.getString("paramValue");
                                    JSONObject jsonObject5 = new JSONObject(name);
                                    place2 = jsonObject5.getString("city");
                                    Log.d("", "" + place2);
                                    city2latstrng=jsonObject5.getString("latitude");
                                    city2lngstrng=jsonObject5.getString("longitude");
                                    editLivWeather2.setText(place2);
                                    liveWeatherUrl2 = ApiConstants.LiveWeather_URL + city2latstrng + "/" + city2lngstrng+ "/e";
                                    Log.d("",""+liveWeatherUrl2);

                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, liveWeatherUrl2, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println("response for live weather....:" + response);
                                            relativeLiveCity2.setVisibility(View.VISIBLE);
                                            relativeWeatherLive.setVisibility(View.VISIBLE);
                                            horizontalliveweather.setVisibility(View.VISIBLE);
                                            try {
                                                live2Temp = response.getString("temp");
                                                live2preciption = response.getString("precipitation");
                                                live2windSpeed = response.getString("windspeed");
                                                live2Humidity = response.getString("humidity");
                                                live2dewPoint = response.getString("dewPoint");
                                                live2Uv = response.getString("uvIndex");
                                                texttemp2.setVisibility(View.VISIBLE);
                                                texttemp2.setText(live2Temp + "F");
                                                textpreci2.setText(live2preciption);
                                                textwindspeed2.setText(live2windSpeed);
                                                textlivehumidity2.setText(live2Humidity);
                                                textlivedewpoint2.setText(live2dewPoint);
                                                textliveuvIndex2.setText(live2Uv);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);

                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("place12", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("p2", place2);
                                    editor.apply();
                                    String place123=pref.getString("p2", null);
                                   // place123 = place123.replaceAll(" ", "%20");
                                    if (place123 != null && !place123.equals("")) {
                                        editLivWeather2.setText(place123);
                                        textcity2.setText(place123);
                                       // getLatitudeLongitudeValues2(place123);
                                    }

                                    liveWeathertext2 = editLivWeather2.getText().toString();
                                    liveWeathertext2 = liveWeathertext2.replaceAll(" ", "%20");
                                   // googleLiveWeather2(liveWeathertext2);
                                } else if (jsonObject3.getString("paramKey").equals("location3")) {
                                    String name = jsonObject3.getString("paramValue");
                                    JSONObject jsonObject5 = new JSONObject(name);
                                    place3 = jsonObject5.getString("city");
                                    Log.d("", "" + place3);
                                    city3latstrng=jsonObject5.getString("latitude");
                                    city3lngstrng=jsonObject5.getString("longitude");
                                    editLivWeather3.setText(place3);
                                    liveWeatherUrl3 = ApiConstants.LiveWeather_URL + city3latstrng + "/" + city3lngstrng+ "/e";
                                    Log.d("liveWeatherUrl3","liveWeatherUrl3"+liveWeatherUrl3);
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, liveWeatherUrl3, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println("response for live weather....:" + response);
                                            relativeLiveCity3.setVisibility(View.VISIBLE);
                                            relativeWeatherLive.setVisibility(View.VISIBLE);
                                            horizontalliveweather.setVisibility(View.VISIBLE);
                                            try {
                                                live3Temp = response.getString("temp");
                                                live3preciption = response.getString("precipitation");
                                                live3windSpeed = response.getString("windspeed");
                                                live3Humidity = response.getString("humidity");
                                                live3dewPoint = response.getString("dewPoint");
                                                live3Uv = response.getString("uvIndex");
                                                texttemp3.setText(live3Temp + "F");
                                                textpreci3.setText(live3preciption);
                                                textwindspeed3.setText(live3windSpeed);
                                                textlivehumidity3.setText(live3Humidity);
                                                textlivedewpoint3.setText(live3dewPoint);
                                                textliveuvIndex3.setText(live3Uv);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.print("Error weather: " + error);

                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);
                                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("place13", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("p3", place3);
                                    editor.apply();
                                    String place321=pref.getString("p3", null);
                                   // place321 = place321.replaceAll(" ", "%20");
                                    if (place321 != null && !place321.equals("")) {
                                        editLivWeather3.setText(place321);
                                        textcity3.setText(place321);
                                        //getLatitudeLongitudeValues3(place321);
                                    }
                                    liveWeathertext3 = editLivWeather3.getText().toString();
                                    liveWeathertext3 = liveWeathertext3.replaceAll(" ", "%20");
                                    //googleLiveWeather3(liveWeathertext3);
                                    //ShowDataLive();
                                }


                            }
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {

        }

        editLivWeather1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                liveWeathertext1 = editLivWeather1.getText().toString().toLowerCase(Locale.getDefault());
                if (liveWeathertext1.equals("")) {
                    relativelive2.setVisibility(View.VISIBLE);
                    relativelive3.setVisibility(View.VISIBLE);
                    buttonliveweather.setVisibility(View.VISIBLE);

                } else {
                    relativelive2.setVisibility(View.GONE);
                    relativelive3.setVisibility(View.GONE);
                    buttonliveweather.setVisibility(View.GONE);
                    listPlacesLive.setVisibility(View.VISIBLE);
                }
                googleLiveWeather1(liveWeathertext1);
            }
        });

        editLivWeather2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPlacesLive.setVisibility(View.VISIBLE);
                liveWeathertext2 = editLivWeather2.getText().toString().toLowerCase(Locale.getDefault());
                if (liveWeathertext2.equals("")) {
                    relativelive2.setVisibility(View.VISIBLE);
                    relativelive3.setVisibility(View.VISIBLE);
                    buttonliveweather.setVisibility(View.VISIBLE);

                } else {
                    relativelive3.setVisibility(View.GONE);
                    buttonliveweather.setVisibility(View.GONE);
                    listPlacesLive.setVisibility(View.VISIBLE);
                }
                googleLiveWeather2(liveWeathertext2);
            }
        });
        editLivWeather3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPlacesLive.setVisibility(View.VISIBLE);
                liveWeathertext3 = editLivWeather3.getText().toString().toLowerCase(Locale.getDefault());
                if (liveWeathertext3.equals("")) {
                    relativelive1.setVisibility(View.VISIBLE);
                    relativelive2.setVisibility(View.VISIBLE);
                    relativelive3.setVisibility(View.VISIBLE);
                    buttonliveweather.setVisibility(View.VISIBLE);

                } else {
                    buttonliveweather.setVisibility(View.GONE);
                    listPlacesLive.setVisibility(View.VISIBLE);
                }
                googleLiveWeather3(liveWeathertext3);
            }
        });

        relativeWeatherLive.setVisibility(View.GONE);
        horizontalliveweather.setVisibility(View.GONE);
        relativeCity1.setVisibility(View.GONE);
        relativeLiveCity2.setVisibility(View.GONE);
        relativeLiveCity3.setVisibility(View.GONE);
        buttonliveweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttemp1.setText("");
                textpreci1.setText("");
                textwindspeed1.setText("");
                textlivehumidity1.setText("");
                textlivedewpoint1.setText("");
                textliveuvIndex1.setText("");
                texttemp2.setText("");
                textpreci2.setText("");
                textwindspeed2.setText("");
                textlivehumidity2.setText("");
                textlivedewpoint2.setText("");
                textliveuvIndex2.setText("");
                texttemp3.setText("");
                textpreci3.setText("");
                textwindspeed3.setText("");
                textlivehumidity3.setText("");
                textlivedewpoint3.setText("");
                textliveuvIndex3.setText("");
                ShowDataLive();
            }

        });
        favlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                final String responce=sharedPreferences1.getString("userid","");
                Log.d("userid","userid"+responce);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("widgetId",2 );
                    jsonObject.put("commodityName",selctCommidtyName );
                    jsonObject.put("userId", Integer.parseInt(responce));
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put("city",placeNameintentLive1);
                    jsonObject1.put("latitude",city1lngstrng);
                    jsonObject1.put("longitude",city1latstrng);
                    JSONObject jsonObject2=new JSONObject();
                    jsonObject2.put("paramKey","location1");
                    jsonObject2.put("paramValue",jsonObject1.toString());
                    Log.d("",""+jsonObject2);
                    JSONObject jsonObject3=new JSONObject();
                    jsonObject3.put("city",placeNameintentLive2.toString());
                    jsonObject3.put("latitude",city2lngstrng);
                    jsonObject3.put("longitude",city2latstrng);
                    JSONObject jsonObject4=new JSONObject();
                    jsonObject4.put("paramKey","location2");
                    jsonObject4.put("paramValue",jsonObject3.toString());
                    Log.d("",""+jsonObject4);
                    JSONObject jsonObject5=new JSONObject();
                    jsonObject5.put("city",placeNameintentLive3);
                    jsonObject5.put("latitude",city3lngstrng);
                    jsonObject5.put("longitude",city3latstrng);
                    JSONObject jsonObject6=new JSONObject();
                    jsonObject6.put("paramKey","location3");
                    jsonObject6.put("paramValue",jsonObject5.toString());
                    Log.d("",""+jsonObject6);
                    jsonArray=new JSONArray();
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject4);
                    jsonArray.put(jsonObject6);
                    Log.d("js","js"+jsonArray);
                    jsonObject.put("options", jsonArray);
                    Log.d("lo","lo"+jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //pd.show();
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ApiConstants.Fav_Url, jsonObject, null, null){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        pd.dismiss();
                        mStatusCode = response.statusCode;
                        Log.d("",""+mStatusCode);
                        try {
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Log.d("",""+json);
                            if(json.equals(""))
                            {
                                Toaster.toast("Filter Saved");
                                pd.dismiss();
                            }
                            else {
                                Toaster.toast("Filter Not Saved");
                                pd.dismiss();
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
    private void ShowDataLive() {
        city1latstrng = Double.toString(city1lat);
        city1lngstrng = Double.toString(city1lng);
        city2latstrng = Double.toString(city2lat);
        city2lngstrng = Double.toString(city2lng);
        city3latstrng = Double.toString(city3lat);
        city3lngstrng = Double.toString(city3lng);
        System.out.println("lat1" + city1latstrng + "lng1" + city1lngstrng + "lat 2" + city2latstrng + "lng 2" + city2lngstrng + "lat 3" + city3latstrng + "lng 3" + city3lngstrng);

        if (city1latstrng.equals("0.0") && city1lngstrng.equals("0.0")) {
            liveWeatherUrl1 = null;
        } else {
            //liveWeatherUrl1 = ApiConstants.LiveWeather_URL + city1latstrng + "/" + city1lngstrng + "/e";
            liveWeatherUrl1 = ApiConstants.LiveWeather_URL + city1lngstrng + "/" + city1latstrng+ "/e";
        }

        if (city2latstrng.equals("0.0") && city2lngstrng.equals("0.0")) {
            liveWeatherUrl2 = null;
        } else {
            //liveWeatherUrl2 = ApiConstants.LiveWeather_URL + city2latstrng + "/" + city2lngstrng + "/e";
            liveWeatherUrl2 = ApiConstants.LiveWeather_URL + city2lngstrng + "/" + city2latstrng + "/e";
        }

        if (city3latstrng.equals("0.0") && city3lngstrng.equals("0.0")) {
            liveWeatherUrl3 = null;
        } else {
            //liveWeatherUrl3 = ApiConstants.LiveWeather_URL + city3latstrng + "/" + city3lngstrng + "/e";
            liveWeatherUrl3 = ApiConstants.LiveWeather_URL + city3lngstrng + "/" + city3latstrng + "/e";
        }

        new liveWeather().execute(liveWeatherUrl1, liveWeatherUrl2, liveWeatherUrl3);
    }
    public class liveWeather extends AsyncTask<String, String, String> {
        JSONObject response1 = null, response2 = null, response3 = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Apicalls.showLoader(DashboardActivity.this, "Please wait");
            pd.show();
            relativeWeatherLive.setVisibility(View.VISIBLE);
            horizontalliveweather.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println("" + params[0] + "   " + params[1] + "  " + params[2]);
            if (params[0] != null) {
                response1 = callLiveWeatherRequest(params[0]);
            }
            if (params[1] != null) {
                response2 = callLiveWeatherRequest2(params[1]);
            }
            if (params[2] != null) {
                response3 = callLiveWeatherRequest3(params[2]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Apicalls.dismissLoader(DashboardActivity.this);
            pd.dismiss();
            if (response1 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response1.toString());
                    Log.d("sdsd","sdsd"+jsonObject);
                    relativeCity1.setVisibility(View.VISIBLE);
                    textcity1.setText(placeNameintentLive1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (response2 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response2.toString());
                    System.out.println("responseee 2.." + response2.toString());
                    Log.d("12","12"+response2);
                    relativeLiveCity2.setVisibility(View.VISIBLE);
                    textcity2.setText(placeNameintentLive2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (response3 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response3.toString());
                    Log.d("13","13"+response3);
                    relativeLiveCity3.setVisibility(View.VISIBLE);
                    textcity3.setText(placeNameintentLive3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            city1lat = 0.0;
            city1lng = 0.0;
            city2lat = 0.0;
            city2lng = 0.0;
            city3lat = 0.0;
            city3lng = 0.0;

        }
    }


    public JSONObject callLiveWeatherRequest(String URL) {
        JSONObject response = null;
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            //  String liveWeather1 = ApiConstants.LiveWeather_URL + lat + "/" + lng + "/e";
            System.out.println("raghu" + URL);

            response = Apicalls.getLiveWeatherRequest(DashboardActivity.this, URL, new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("response for live weather....:" + response);
                    try {
                        live1Temp = response.getString("temp");
                        live1preciption = response.getString("precipitation");
                        live1windSpeed = response.getString("windspeed");
                        live1Humidity = response.getString("humidity");
                        live1dewPoint = response.getString("dewPoint");
                        live1Uv = response.getString("uvIndex");
                        texttemp1.setText(live1Temp + "F");
                        textpreci1.setText(live1preciption);
                        textwindspeed1.setText(live1windSpeed);
                        textlivehumidity1.setText(live1Humidity);
                        textlivedewpoint1.setText(live1dewPoint);
                        textliveuvIndex1.setText(live1Uv);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void getError(VolleyError error) {
                   // System.out.print("Error Response:" + error);
                    //  CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                }
            });

        }
        return response;
    }
    public JSONObject callLiveWeatherRequest2(String URL) {
        JSONObject response = null;
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            //  String liveWeather1 = ApiConstants.LiveWeather_URL + lat + "/" + lng + "/e";
            System.out.println("raghu" + URL);

            response = Apicalls.getLiveWeatherRequest(DashboardActivity.this, URL, new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("response for live weather....:" + response);
                    try {
                        live2Temp = response.getString("temp");
                        live2preciption = response.getString("precipitation");
                        live2windSpeed = response.getString("windspeed");
                        live2Humidity = response.getString("humidity");
                        live2dewPoint = response.getString("dewPoint");
                        live2Uv = response.getString("uvIndex");
                        texttemp2.setText(live2Temp + "F");
                        textpreci2.setText(live2preciption);
                        textwindspeed2.setText(live2windSpeed);
                        textlivehumidity2.setText(live2Humidity);
                        textlivedewpoint2.setText(live2dewPoint);
                        textliveuvIndex2.setText(live2Uv);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void getError(VolleyError error) {
                   // System.out.print("Error Response:" + error);
                    //  CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);

                }
            });

        }

        return response;
    }
    public JSONObject callLiveWeatherRequest3(String URL) {
        JSONObject response = null;
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            //  String liveWeather1 = ApiConstants.LiveWeather_URL + lat + "/" + lng + "/e";
            System.out.println("raghu" + URL);

            response = Apicalls.getLiveWeatherRequest(DashboardActivity.this, URL, new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("response for live weather....:" + response);
                    try {

                        live3Temp = response.getString("temp");
                        live3preciption = response.getString("precipitation");
                        live3windSpeed = response.getString("windspeed");
                        live3Humidity = response.getString("humidity");
                        live3dewPoint = response.getString("dewPoint");
                        live3Uv = response.getString("uvIndex");
                        texttemp3.setText(live3Temp + "F");
                        textpreci3.setText(live3preciption);
                        textwindspeed3.setText(live3windSpeed);
                        textlivehumidity3.setText(live3Humidity);
                        textlivedewpoint3.setText(live3dewPoint);
                        textliveuvIndex3.setText(live3Uv);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void getError(VolleyError error) {
                   // System.out.print("Error Response:" + error);
                    //  CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);

                }
            });

        }

        return response;
    }
    public void getPriceIndex() {
        NukeSSLCerts.NukeSSLCerts();
        //pd.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiConstants.ConsumerPriceIndex_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               // pd.dismiss();
                if (response.length()>0)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String all_cities = jsonObject.getString("all_cities");
                        String fresh_fruits = jsonObject.getString("fresh_fruits");
                        String fruits_vegetables = jsonObject.getString("fruits_vegetables");
                        JSONObject jsonObjectallCities = new JSONObject(all_cities);
                        JSONObject jsonObjectfresh_fruits = new JSONObject(fresh_fruits);
                        JSONObject jsonObjectfruits_vegetables = new JSONObject(fruits_vegetables);
                        cities_value = jsonObjectallCities.getString("value");
                        fresh_fruits_value = jsonObjectfresh_fruits.getString("value");
                        fruits_vegetables_value = jsonObjectfruits_vegetables.getString("value");
                        month=jsonObjectfruits_vegetables.getString("month");
                        Log.d("",""+month);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error weather: " + error);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void consumereInitialization() {
        pd.dismiss();
        TextView textView=findViewById(R.id.historicIndex);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HistoricIndexActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);

            }
        });
        TextView textView1=findViewById(R.id.historicfruits);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FreshFruitsHistoricActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);

            }
        });
        TextView textView2=findViewById(R.id.historiccities);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CitiesHistoricActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        indexVal = (TextView) findViewById(R.id.indexVal);
        allFruitsVal = (TextView) findViewById(R.id.allFruitsVal);
        allcitiesVal = (TextView) findViewById(R.id.allcitiesVal);
        monthtext=(TextView) findViewById(R.id.month);
        indexVal.setText("Index: " + fresh_fruits_value);
        allFruitsVal.setText("All Fruits: " +fruits_vegetables_value );
        allcitiesVal.setText("All Cities: " +cities_value );
        monthtext.setText("Month:"+month);
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        if(cities_value!= null)
        {
            float number1= Float.parseFloat(cities_value);
            yvalues.add(new Entry(number1, 0));
        }

        if(fresh_fruits_value!=null)
        {
            float number2= Float.parseFloat(fresh_fruits_value);
            yvalues.add(new Entry(number2, 1));
        }

        if(fruits_vegetables_value!=null)
        {

            float number3= Float.parseFloat(fruits_vegetables_value);

            yvalues.add(new Entry(number3, 2));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Index");
        xVals.add("All Fruits");
        xVals.add("All Cities");

        PieData data = new PieData(xVals, dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //  data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateXY(1400, 1400);
        pd.dismiss();

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
    }

    @Override
    public void onNothingSelected() {

    }

    public void gasLayoutInitialization() {
        gasHistoric= (Button) findViewById(R.id.gashistoric);
        getGasPrices();
        xValsGas = new ArrayList<>();
        yValsGas = new ArrayList<Entry>();
        mChartGas = (LineChart) findViewById(R.id.linechartgas);
        listviewGas=(ListView)findViewById(R.id.listgas);
        //listDiesel=(ListView)findViewById(R.id.listdiesel);
        mChartGas.setDrawGridBackground(false);
        Legend l = mChartGas.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        // mChartGas.setDescription("Demo Line Chart");
        mChartGas.setNoDataTextDescription("You need to provide data for the chart.");
        mChartGas.setTouchEnabled(true);
        mChartGas.setDragEnabled(true);
        mChartGas.setScaleEnabled(true);
        mChartGas.setPinchZoom(true);
        YAxis leftAxis = mChartGas.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(3.2f);
        leftAxis.setAxisMinValue(2.65f);
        leftAxis.setGranularity(0.001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartGas.getAxisRight().setEnabled(false);
        mChartGas.getAxisRight().setTextColor(Color.WHITE);
        mChartGas.getXAxis().setTextColor(Color.WHITE);
        mChartGas.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartGas.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChartGas.invalidate();

        gasHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                h.postDelayed(r,2000);
                //gasHistoricInitial();
                Intent intent=new Intent(getApplicationContext(),FuelGraphActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });

    }
    public void getGasPrices() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            pd.show();
            NukeSSLCerts.NukeSSLCerts();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiConstants.GetGasPrices_URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("weather resp:" + response);
                    pd.dismiss();
                    gasList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String gasvalue = jsonObject.getString("value");
                            String weekDayFormat = jsonObject.getString("weekDayFormat");
                            System.out.println("gas value..." + gasvalue);
                            System.out.println("week day format..." + weekDayFormat);
                            xValsGas.add(weekDayFormat);
                            //xValsGas.add(gasvalue);
                            //yValsGas.add(gasvalue);
                            try {
                                yValsGas.add(new Entry((Float.valueOf(gasvalue)), i));
                                Log.d("",""+yValsGas);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            gas = new Gas(weekDayFormat, gasvalue);

                            gasList.add(gas);
                            GasAdapter gasAdapter = new GasAdapter(DashboardActivity.this, R.layout.gas_layout, gasList);
                            Log.d("sfsfsf","sfsfsf"+gasList);
                            listviewGas.setAdapter(gasAdapter);
                            gasAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    LineDataSet set1 = new LineDataSet(yValsGas, "");
                    set1.setLineWidth(2.5f);
                    set1.setCircleRadius(5f);
                    set1.setDrawFilled(true);
                    set1.setColor(Color.WHITE);
                    set1.setValueTextColor(Color.WHITE);
                    set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
                    LineData data = new LineData(xValsGas, set1);
                    mChartGas.setData(data);
                    mChartGas.invalidate();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error weather: " + error);

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonArrayRequest);
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }


    }

    public void fuelLayoutInitialization() {
        fuellbutton= (Button) findViewById(R.id.fuellhistoric);
        xValsFuel = new ArrayList<>();
        yValsFuel = new ArrayList<Entry>();
        mChartFuel = (LineChart) findViewById(R.id.linechartfuel);
        mChartFuel.setDrawGridBackground(false);
        Legend l = mChartFuel.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        mChartFuel.setDescription("Demo Line Chart");
        mChartFuel.setNoDataTextDescription("You need to provide data for the chart.");
        mChartFuel.setTouchEnabled(true);
        mChartFuel.setDragEnabled(true);
        mChartFuel.setScaleEnabled(true);
        mChartFuel.setPinchZoom(true);
        YAxis leftAxis = mChartFuel.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(3.30f);
        leftAxis.setAxisMinValue(2.97f);
        leftAxis.setGranularity(0.0001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        getFuelPrices();
        fuellbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fuelHistoricInitial();
                Intent intent=new Intent(getApplicationContext(),GasGraphAcitivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
    }


    public void getFuelPrices() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            pd.show();
            NukeSSLCerts.NukeSSLCerts();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiConstants.GetFuelPrices_URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("weather resp:" + response);
                    pd.dismiss();
                    fuelList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String weekDayFormat = jsonObject.getString("weekDayFormat");
                            String value = jsonObject.getString("value");
                            System.out.println("valueeeeeeeeeeeeeeeeeeeeeeeeeee:" + value);
                            System.out.println("wekkkkkkkkkkkkkkkkkkkkkkkkkk:" + weekDayFormat);
                            xValsFuel.add(weekDayFormat);
                            try {
                                yValsFuel.add(new Entry((Float.valueOf(value)), i));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            gas1 = new Gas(weekDayFormat, value);
                            fuelList.add(gas1);
                            GasAdapter gasAdapter = new GasAdapter(DashboardActivity.this, R.layout.gas_layout, fuelList);
                            Log.d("",""+fuelList);
                            listDiesel=(ListView)findViewById(R.id.listdiesel);
                            listDiesel.setAdapter(gasAdapter);
                            gasAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    LineDataSet set1 = new LineDataSet(yValsFuel, "");
                    set1.setLineWidth(2.5f);
                    set1.setCircleRadius(5f);
                    set1.setDrawFilled(true);
                    set1.setColor(Color.WHITE);
                    set1.setValueTextColor(Color.WHITE);
                    set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
                    LineData data = new LineData(xValsFuel, set1);
                    mChartFuel.setData(data);
                    mChartFuel.invalidate();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error weather: " + error);

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonArrayRequest);
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void juiceLayoutInitialization() {
        TextView textView=findViewById(R.id.hi);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(getApplicationContext(),JuiceHistoricActivity.class);
              intent.putExtra("commodity",selctCommidtyName);
              startActivity(intent);
            }
        });
        juiceModelList = new ArrayList<>();
        textnoJuice = (TextView) findViewById(R.id.textnoJuice);
        textnoJuice.setVisibility(View.GONE);
        listjuices = (ListView) findViewById(R.id.listjuices);
        callJuiceService();
    }

    public void callJuiceService() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            pd.show();
            String newJuiceUrl = ApiConstants.JuiceGet_URL + selctCommidtyName;
            System.out.println("new juice urlllll" + newJuiceUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newJuiceUrl,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pd.dismiss();
                    try {

                        if(response.length()>0)
                        {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("2018");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String weekEnding = jsonObject1.getString("dateValue");
                                String month=jsonObject1.getString("month");
                                String week = jsonObject1.getString("weekValue");
                                String accum = jsonObject1.getString("accumValue");
                                String monthweek=month.concat(weekEnding);
                                juiceModel = new JuiceModel(monthweek, week, accum);
                                juiceModelList.add(juiceModel);
                            }
                            JuiceAdapter juiceAdapter = new JuiceAdapter(DashboardActivity.this, juiceModelList);
                            listjuices.setAdapter(juiceAdapter);
                            juiceAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Juice Volumes For Present Date",Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error weather: " + error);
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

    }

    public void freezeLayoutInitialization() {
        TextView textView=findViewById(R.id.historicfreeze);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FreezeHistoricActivity.class);
                intent.putExtra("commodity",selctCommidtyName);
                startActivity(intent);
            }
        });
        listfreeze = (ListView) findViewById(R.id.listfreeze);
        freezeModelList = new ArrayList<>();
        callFreezeVolumeService();
    }

    public void callFreezeVolumeService() {
        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            String newFreezeUrl = ApiConstants.FrrezeGet_URL + selctCommidtyName;
            System.out.println("new juice urlllll" + newFreezeUrl);
            pd.show();
            NukeSSLCerts.NukeSSLCerts();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newFreezeUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pd.dismiss();
                    //freezeModelList.clear();
                    System.out.println("response for juiceeeee....:" + response);
                    freezeModelList.clear();
                    try {

                        if(response.length()>0)
                        {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("2018");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String weekEnding = jsonObject1.getString("dateValue");
                                String month=jsonObject1.getString("month");
                                String week = jsonObject1.getString("weekValue");
                                String accum = jsonObject1.getString("accumValue");
                                String monthweek=month.concat(weekEnding);
                                freezeModel = new FreezeModel(monthweek, week, accum);
                                freezeModelList.add(freezeModel);
                            }
                            FreezeAdapter freezeAdapter = new FreezeAdapter(DashboardActivity.this, freezeModelList);
                            listfreeze.setAdapter(freezeAdapter);
                            freezeAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"No Freeze Volume for presenT Date",Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    freezeModelList.clear();
                    System.out.println("response for juiceeeee....:" + response);
                    if(response.length()>0)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("2018");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String weekEnding = jsonObject1.getString("dateValue");
                                String month=jsonObject1.getString("month");
                                String week = jsonObject1.getString("weekValue");
                                String accum = jsonObject1.getString("accumValue");
                                String monthweek=month.concat(weekEnding);
                                freezeModel = new FreezeModel(monthweek, week, accum);
                                freezeModelList.add(freezeModel);
                            }
                            FreezeAdapter freezeAdapter = new FreezeAdapter(DashboardActivity.this, freezeModelList);
                            listfreeze.setAdapter(freezeAdapter);
                            freezeAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error weather: " + error);

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }


    public void holidayInitialization() {
        spinnerHolidaydates = (Spinner) findViewById(R.id.spinnerHolidaydates);
        listViewUsa = (ListView) findViewById(R.id.listViewUsa);
        listViewMexico = (ListView) findViewById(R.id.listViewMexico);
        listViewCanada = (ListView) findViewById(R.id.listViewCanada);
        holidayModalList = new ArrayList<>();
        canadaholidayList = new ArrayList<>();
        mexicoholidayList = new ArrayList<>();
        spinnerHolidaydates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holidayDate = holidays[position].toString();
                if (holidayDate != null && !holidayDate.equals("")) {
                    getHolidaysList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spinnerholidayAdapter = new ArrayAdapter(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, holidays);
        spinnerholidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHolidaydates.setAdapter(spinnerholidayAdapter);
    }


    public void getHolidaysList() {

        if (InternetChecking.isInternetOn(DashboardActivity.this)) {
            System.out.println("exchange url...." + ApiConstants.ExchangesGet_URL);
            String HolidayUrl = ApiConstants.HolidaysPrice_URL + holidayDate;
            System.out.println("holidayyyyy url....." + HolidayUrl);
            pd.show();
            NukeSSLCerts.NukeSSLCerts();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HolidayUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    holidayModalList.clear();
                    canadaholidayList.clear();
                    mexicoholidayList.clear();
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray jsonArrayUsa = jsonObject.getJSONArray("USA");
                        JSONArray jsonArrayMexico = jsonObject.getJSONArray("MEXICO");
                        JSONArray jsonArrayCanada = jsonObject.getJSONArray("CANADA");
                        int usaHolidayLength = jsonArrayUsa.length();
                        int candaHolidayLength = jsonArrayCanada.length();
                        int mexicoHolidayLength = jsonArrayMexico.length();
                        System.out.println("json" + usaHolidayLength + "candaHolidayLength" + candaHolidayLength + "mexicoHolidayLength" + mexicoHolidayLength);
                        for (int i = 0; i < jsonArrayUsa.length(); i++) {
                            JSONObject jsonObjectUsa = jsonArrayUsa.getJSONObject(i);
                            String holidayDateUsa = jsonObjectUsa.getString("holidayDate");
                            String descriptionUsa = jsonObjectUsa.getString("description");
                            holidayModal = new HolidayModal(holidayDateUsa, descriptionUsa);
                            holidayModalList.add(holidayModal);
                            Log.d("",""+holidayModalList);
                        }



                        for (int j = 0; j < jsonArrayCanada.length(); j++) {
                            JSONObject jsonObjectCanada = jsonArrayCanada.getJSONObject(j);
                            String holidayDateCanada = jsonObjectCanada.getString("holidayDate");
                            String descriptionCanada = jsonObjectCanada.getString("description");
                            holidayModal = new HolidayModal(holidayDateCanada, descriptionCanada);
                            //holidayModalList.add(holidayModal);
                            canadaholidayList.add(holidayModal);

                        }
                        for (int k = 0; k < jsonArrayMexico.length(); k++) {
                            JSONObject jsonObjectMexico = jsonArrayMexico.getJSONObject(k);
                            String holidayDateMexico = jsonObjectMexico.getString("holidayDate");
                            String descriptionMexico = jsonObjectMexico.getString("description");
                            holidayModal = new HolidayModal(holidayDateMexico, descriptionMexico);
                            //holidayModalList.add(holidayModal);
                            mexicoholidayList.add(holidayModal);

                        }


                        HolidayAdapter holidayAdapter = new HolidayAdapter(DashboardActivity.this, R.layout.holidayadapter, holidayModalList);
                        listViewUsa.setAdapter(holidayAdapter);
                        holidayAdapter.notifyDataSetChanged();
                       

                        HolidayAdapter holidayAdapter1 = new HolidayAdapter(DashboardActivity.this, R.layout.holidayadapter, canadaholidayList);
                        listViewCanada.setAdapter(holidayAdapter1);
                        holidayAdapter1.notifyDataSetChanged();

                        HolidayAdapter holidayAdapter2 = new HolidayAdapter(DashboardActivity.this, R.layout.holidayadapter, mexicoholidayList);
                        listViewMexico.setAdapter(holidayAdapter2);
                        holidayAdapter2.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("Error weather: " + error);
                    CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }


    }

    public void googleLiveWeather1(String placeName) {
        String places_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + placeName + "&components=country:us&key=AIzaSyAWhk74bW6pqjgd0iEeHqxdNX4uxDywIvI";
        JSONObject response = Apicalls.googlePlacesRequest(DashboardActivity.this, places_url, new Apicalls.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                System.out.println("response for locations....:" + response);
                Log.d("sss","ssss"+response);
                try {
                    livePlacesParseResp(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getError(VolleyError error) {
               // System.out.print("Error Response:" + error);
               // CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
            }
        });


    }

    public void googleLiveWeather2(String placeName) {
        String places_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + placeName + "&components=country:us&key=AIzaSyAWhk74bW6pqjgd0iEeHqxdNX4uxDywIvI";
        JSONObject response = Apicalls.googlePlacesRequest(DashboardActivity.this, places_url, new Apicalls.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                System.out.println("response for locations....:" + response);
                try {
                    livePlacesParseResp2(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getError(VolleyError error) {
               // System.out.print("Error Response:" + error);
                //CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
            }
        });


    }

    public void googleLiveWeather3(String placeName) {
        String places_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + placeName + "&components=country:us&key=AIzaSyAWhk74bW6pqjgd0iEeHqxdNX4uxDywIvI";
        JSONObject response = Apicalls.googlePlacesRequest(DashboardActivity.this, places_url, new Apicalls.VolleyCallback() {
            @Override
            public void getResponse(JSONObject response) {
                System.out.println("response for locations....:" + response);
                try {
                    livePlacesParseResp3(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getError(VolleyError error) {
               // System.out.print("Error Response:" + error);
               // CustomToast.displayMessage(DashboardActivity.this, CustomToast.Error_Response);
            }
        });


    }


    private void livePlacesParseResp(JSONObject response) throws JSONException {
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
        LiveWeathAdapter1 liveWeathAdapter1 = new LiveWeathAdapter1(DashboardActivity.this, placesModalList);
        listPlacesLive.setAdapter(liveWeathAdapter1);
        liveWeathAdapter1.notifyDataSetChanged();
    }


    private void livePlacesParseResp2(JSONObject response) throws JSONException {
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
        LiveWeathAdapter2 liveWeathAdapter2 = new LiveWeathAdapter2(DashboardActivity.this, placesModalList);
        listPlacesLive.setAdapter(liveWeathAdapter2);
        liveWeathAdapter2.notifyDataSetChanged();
    }

    private void livePlacesParseResp3(JSONObject response) throws JSONException {
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
        LiveWeathAdapter3 liveWeathAdapter3 = new LiveWeathAdapter3(DashboardActivity.this, placesModalList);
        listPlacesLive.setAdapter(liveWeathAdapter3);
        liveWeathAdapter3.notifyDataSetChanged();
    }


    public void getLatitudeLongitudeValues1(String placeName1) {
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(placeName1, 50);
            for (Address add : adresses) {
                city1lat = add.getLongitude();
                city1lng = add.getLatitude();
                System.out.println("latitude...." + city1lat);
                System.out.println("longitude......" + city1lng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLatitudeLongitudeValues2(String placeName2) {
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(placeName2, 50);
            for (Address add : adresses) {
                city2lat = add.getLongitude();
                city2lng = add.getLatitude();
                System.out.println("latitude...." + city2lat);
                System.out.println("longitude......" + city2lng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getLatitudeLongitudeValues3(String placeName3) {
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(placeName3, 50);
            for (Address add : adresses) {
                city3lat = add.getLongitude();
                city3lng = add.getLatitude();
                System.out.println("latitude...." + city3lat);
                System.out.println("longitude......" + city3lng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

