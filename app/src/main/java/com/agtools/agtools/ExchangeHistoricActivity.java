package com.agtools.agtools;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Adapter.ExchangeAdapter;
import com.agtools.agtools.Adapter.HolidayAdapter;
import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Model.HolidayModal;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import xdroid.toaster.Toaster;

public class ExchangeHistoricActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    ListView DateListview,cadListView,PesoLsitView;
    Button exchange;
    ExchangeHistoric exchangeHistoric;
    public ArrayList<String> montharrays1=new ArrayList<>();
    List<ExchangeHistoric> holidayModalList=new ArrayList<>();
    List<ExchangeHistoric> canadaholidayList=new ArrayList<>();
    List<ExchangeHistoric> mexicoholidayList=new ArrayList<>();
    String commodity;
    String fdate,tdate;
    TextView weblink;
    public LineChart mChartGas, mChartFuel;
    boolean showingFirstImage=true;
    boolean showingSecondImage=true;
    ArrayList<Entry> yValsFuel = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear = new ArrayList<Entry>();
    TransparentProgressDialog pd;
    private int mStatusCode = 0;
    private Runnable r;
    Button favex;
    JSONArray jsonArray;
    String finalcheckuser;
    ExchangeHistoricAdapter holidayAdapter,holidayAdapter1,holidayAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_historic);
        SharedPreferences sharedPreferences4 = getSharedPreferences("MyPref", 0);
        finalcheckuser = sharedPreferences4.getString("check", null);
        Log.d("finalcheckuser", "finalcheckuser" + finalcheckuser);
        pd = new TransparentProgressDialog(this, R.drawable.sgg);
        r =new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };
        favex=findViewById(R.id.favex);
        weblink=findViewById(R.id.weblink);
        commodity=getIntent().getStringExtra("commodity");
        commodity=getIntent().getStringExtra("commodity");
        Button historicbackexchange=findViewById(R.id.historicbackexchange);
        historicbackexchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        exchange= (Button) findViewById(R.id.exhistoric);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateListview=(ListView) findViewById(R.id.exchangeListDate);
        cadListView=(ListView) findViewById(R.id.exchangeListcad);
        PesoLsitView=(ListView) findViewById(R.id.exchangeListPeco);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String strDate = mdformat.format(calendar.getTime());
        toDateEtxt.setText(strDate);
        tdate=toDateEtxt.getText().toString();
        fdate=fromDateEtxt.getText().toString();
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                fdate=fromDateEtxt.getText().toString();

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                tdate=toDateEtxt.getText().toString();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        holidayModalList.clear();
        canadaholidayList.clear();
        mexicoholidayList.clear();
        String url= ApiConstants.API.concat("historic/exchange/2/"+commodity+"?fromDate="+fdate+"&toDate="+tdate+"&subuserType=0") ;
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("",""+response);
                holidayModalList.clear();
                canadaholidayList.clear();
                mexicoholidayList.clear();
                if(response.length()>0)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray jsonArrayCanada = jsonObject.getJSONArray("Canada");
                        JSONArray jsonArrayMexico = jsonObject.getJSONArray("Mexico");
                        Log.d("",""+jsonArrayCanada);
                        Log.d("",""+jsonArrayMexico);
                        for (int i = 0; i < jsonArrayCanada.length(); i++) {
                            JSONObject jsonObjectUsa = jsonArrayCanada.getJSONObject(i);
                            String holidayDateUsa = jsonObjectUsa.getString("date");
                            //String descriptionUsa = jsonObjectUsa.getString("description");
                            exchangeHistoric = new ExchangeHistoric(holidayDateUsa);
                            montharrays1.add(holidayDateUsa);
                            holidayModalList.add(exchangeHistoric);
                            ExchangeHistoricAdapter holidayAdapter = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, holidayModalList);
                            DateListview.setAdapter(holidayAdapter);
                            holidayAdapter.notifyDataSetChanged();

                        }
                        for (int i = 0; i < jsonArrayCanada.length(); i++) {
                            JSONObject jsonObjectUsa = jsonArrayCanada.getJSONObject(i);
                            String holidayDateUsa = jsonObjectUsa.getString("rateValue");
                            try {
                                yValsFuel.add(new Entry((Float.valueOf(holidayDateUsa)), i));
                                Log.d("",""+yValsFuel);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            exchangeHistoric = new ExchangeHistoric(holidayDateUsa);
                            canadaholidayList.add(exchangeHistoric);
                            ExchangeHistoricAdapter holidayAdapter1 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, canadaholidayList);
                            cadListView.setAdapter(holidayAdapter1);
                            holidayAdapter1.notifyDataSetChanged();

                        }
                        for (int i = 0; i < jsonArrayMexico.length(); i++) {
                            JSONObject jsonObjectUsa = jsonArrayMexico.getJSONObject(i);
                            String holidayDateUsa = jsonObjectUsa.getString("rateValue");
                            try {
                                yValsFuelyear.add(new Entry((Float.valueOf(holidayDateUsa)), i));
                                Log.d("",""+yValsFuel);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            exchangeHistoric = new ExchangeHistoric(holidayDateUsa);
                            mexicoholidayList.add(exchangeHistoric);
                            ExchangeHistoricAdapter holidayAdapter2 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, mexicoholidayList);
                            PesoLsitView.setAdapter(holidayAdapter2);
                            holidayAdapter2.notifyDataSetChanged();
                        }
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
                Log.d("",""+error);
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ExchangeHistoricActivity.this);
        requestQueue.add(jsonArrayRequest);

        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://vserver.amicuswa.com/agtools/historic/exchange/2/"+commodity+"?fromDate="+fdate+"&toDate="+tdate+"&subuserType=0" ;
                JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",""+response);
                        holidayModalList.clear();
                        canadaholidayList.clear();
                        mexicoholidayList.clear();
                        yValsFuel.clear();
                        yValsFuelyear.clear();
                        holidayAdapter = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this,R.layout.exchange_layout ,holidayModalList);
                        holidayAdapter.notifyDataSetChanged();
                        holidayAdapter1 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout,canadaholidayList);
                        holidayAdapter1.notifyDataSetChanged();
                        holidayAdapter2 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this,R.layout.exchange_layout,  mexicoholidayList);
                        PesoLsitView.setAdapter(holidayAdapter2);
                        holidayAdapter2.notifyDataSetChanged();
                        if(response.length()>0)
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONArray jsonArrayCanada = jsonObject.getJSONArray("Canada");
                                JSONArray jsonArrayMexico = jsonObject.getJSONArray("Mexico");
                                Log.d("",""+jsonArrayCanada);
                                Log.d("",""+jsonArrayMexico);
                                for (int i = 0; i < jsonArrayCanada.length(); i++) {
                                    JSONObject jsonObjectUsa = jsonArrayCanada.getJSONObject(i);
                                    String holidayDateUsa = jsonObjectUsa.getString("date");

                                    //String descriptionUsa = jsonObjectUsa.getString("description");
                                    exchangeHistoric = new ExchangeHistoric(holidayDateUsa);

                                    holidayModalList.add(exchangeHistoric);
                                    ExchangeHistoricAdapter holidayAdapter = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, holidayModalList);
                                    DateListview.setAdapter(holidayAdapter);
                                    holidayAdapter.notifyDataSetChanged();

                                }
                                for (int i = 0; i < jsonArrayCanada.length(); i++) {
                                    JSONObject jsonObjectUsa = jsonArrayCanada.getJSONObject(i);
                                    String holidayDateUsa = jsonObjectUsa.getString("rateValue");

                                    exchangeHistoric = new ExchangeHistoric(holidayDateUsa);
                                    canadaholidayList.add(exchangeHistoric);
                                    ExchangeHistoricAdapter holidayAdapter1 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, canadaholidayList);
                                    cadListView.setAdapter(holidayAdapter1);
                                    holidayAdapter1.notifyDataSetChanged();

                                }
                                for (int i = 0; i < jsonArrayMexico.length(); i++) {
                                    JSONObject jsonObjectUsa = jsonArrayMexico.getJSONObject(i);
                                    String holidayDateUsa = jsonObjectUsa.getString("rateValue");

                                    exchangeHistoric = new ExchangeHistoric(holidayDateUsa);
                                    mexicoholidayList.add(exchangeHistoric);
                                    ExchangeHistoricAdapter holidayAdapter2 = new ExchangeHistoricAdapter(ExchangeHistoricActivity.this, R.layout.exchange_layout, mexicoholidayList);
                                    PesoLsitView.setAdapter(holidayAdapter2);
                                    holidayAdapter2.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            holidayModalList.clear();
                            canadaholidayList.clear();
                            mexicoholidayList.clear();
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("",""+error);
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(ExchangeHistoricActivity.this);
                requestQueue.add(jsonArrayRequest);

            }
        });
        favex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
                final String responce=sharedPreferences1.getString("userid","");
                Log.d("userid","userid"+responce);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("widgetId",11);
                    jsonObject.put("userId",responce);
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put("paramKey","startDate");
                    jsonObject1.put("paramValue",fdate);
                    JSONObject jsonObject2=new JSONObject();
                    jsonObject2.put("paramKey","endDate");
                    jsonObject2.put("paramValue",tdate);
                    jsonArray=new JSONArray();
                    jsonArray.put(jsonObject1);
                    jsonArray.put(jsonObject2);
                    jsonObject.put("options",jsonArray);
                    Log.d("",""+jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // pd.show();
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
        weblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGraph();
            }
        });
    }
    private void getGraph()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.grpah, null);
        mChartFuel = (LineChart)alertLayout.findViewById(R.id.currentlinkschart);
        final ImageView imageView1=alertLayout.findViewById(R.id.image);
        final ImageView imageView2=alertLayout.findViewById(R.id.image1);
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
        leftAxis.setAxisMaxValue(21);
        leftAxis.setAxisMinValue(0);
        leftAxis.setGranularity(0.0001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChartFuel.getAxisLeft().setLabelCount(8, true);
        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        final ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
        final LineDataSet set1 = new LineDataSet(yValsFuel, "");
        set1.setLineWidth(5f);
        set1.setCircleRadius(3f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        //set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
        //year1
        final LineDataSet set2 = new LineDataSet(yValsFuelyear, "");
        set2.setLineWidth(3f);
        set2.setCircleRadius(2f);
        set2.setDrawFilled(true);
        set2.setColor(Color.WHITE);
        set2.setValueTextColor(Color.WHITE);
        set2.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

        lineDataSets.add(set1);
        lineDataSets.add(set2);
        LineData data1 = new LineData(montharrays1,lineDataSets);
        mChartFuel.setData(data1);
        mChartFuel.invalidate();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingFirstImage==true)
                {
                    lineDataSets.remove(set1);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    //  lineDataSets.add(set2);
                    // Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays1,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = false;
                }
                else {
                    lineDataSets.add(set1);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.weather_textview_top_background);
                    Log.d("",""+lineDataSets);
                    //  lineDataSets.add(set2);
                    //  Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays1,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = true;
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (showingSecondImage == true) {
                                                  lineDataSets.remove(set2);
                                                  imageView2.setVisibility(View.VISIBLE);
                                                  imageView2.setImageResource(R.drawable.st);
                                                  Log.d("", "" + lineDataSets);
                                                  // lineDataSets.add(set1);
                                                  // Log.d("",""+lineDataSets);
                                                  LineData data1 = new LineData(montharrays1, lineDataSets);
                                                  mChartFuel.setData(data1);
                                                  mChartFuel.invalidate();
                                                  showingSecondImage = false;
                                              } else {
                                                  lineDataSets.add(set2);
                                                  imageView2.setVisibility(View.VISIBLE);
                                                  imageView2.setImageResource(R.drawable.ng);
                                                  Log.d("", "" + lineDataSets);
                                                  //lineDataSets.add(set1);
                                                  //Log.d("",""+lineDataSets);
                                                  LineData data1 = new LineData(montharrays1, lineDataSets);
                                                  mChartFuel.setData(data1);
                                                  mChartFuel.invalidate();
                                                  showingSecondImage = true;
                                              }
                                          }
            });
        AlertDialog.Builder alert = new AlertDialog.Builder(ExchangeHistoricActivity.this);
        alert.setTitle("Exchange Chart :");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
}

