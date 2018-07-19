package com.agtools.agtools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.agtools.agtools.Constants.ApiConstants.API;

/**
 * Created by Ranjith on 3/30/2018.
 */

public class CitiesHistoricActivity extends Activity {
    private static final int MY_SOCKET_TIMEOUT_MS =10000 ;


    TextView month1,month2,month3,value1,value2,value3;
    TextView month22,month33,value22,value33;
    TextView month2222,value2222,month3333,value3333;

    TextView monthplus1,monthplus2,valueplus1,valueplus2;

    public ArrayList<String> montharrays;
    public ArrayList<String> valuearrays=new ArrayList<>();

    public ArrayList<String> montharrays1=new ArrayList<>();
    public ArrayList<String> valuearrays1=new ArrayList<>();

    public ArrayList<String> montharrays2=new ArrayList<>();
    public ArrayList<String> valuearrays2=new ArrayList<>();

    public ArrayList<String> montharrays3=new ArrayList<>();
    public ArrayList<String> valuearrays3=new ArrayList<>();

    public ArrayList<String> montharrays4=new ArrayList<>();
    public ArrayList<String> valuearrays4=new ArrayList<>();


    public ArrayList<String> montharrays5=new ArrayList<>();
    public ArrayList<String> valuearrays5=new ArrayList<>();
    ArrayList<Entry> yValsFuel = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear1 = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear2 = new ArrayList<Entry>();

    ArrayList<Entry> yValsFuelyear3 = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear4 = new ArrayList<Entry>();
    ArrayList<Entry> yValsFuelyear5 = new ArrayList<Entry>();

    public ArrayList<String> montharrays6=new ArrayList<>();
    public ArrayList<String> valuearrays6=new ArrayList<>();

    TextView year1,year2,year3;
    TextView mon1,mon2,mon3,mon4,mon5,mon6,mon7,mon8,mon9,mon10,mon11,mon12;
    TextView val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11,val12;


    TextView mon01,mon02,mon13,mon14,mon15,mon16,mon17,mon18,mon19,mon110,mon111,mon112;
    TextView val01,val02,val13,val14,val15,val16,val17,val18,val19,val110,val111,val112;


    TextView m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12;
    TextView v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12;
    String commodity;
    TextView currentYearLink,yearlinks,historicallink;
    public LineChart mChartGas, mChartFuel;
    boolean showingFirstImage=true;
    boolean showingSecondImage=true;
    boolean showingThirdImage=true;
    boolean showingFirstImage1=true;
    boolean showingSecondImage1=true;
    boolean showingThirdImage1=true;
    TextView textmonth,textmonth2,textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_cities_index);
        Button button=findViewById(R.id.back);
        textmonth=findViewById(R.id.month3333);
        textmonth2=findViewById(R.id.month2222);
        textView3=findViewById(R.id.monthplus1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        currentYearLink=findViewById(R.id.currentlinks);
        yearlinks=findViewById(R.id.yearslink);
        historicallink=findViewById(R.id.historicallink);
        year1=findViewById(R.id.year1);
        year2=findViewById(R.id.year2);
        year3=findViewById(R.id.year3);
        month1=findViewById(R.id.month1);
        month2=findViewById(R.id.month2);
        month3=findViewById(R.id.month3);
        value1=findViewById(R.id.value1);
        value2=findViewById(R.id.value2);
        value3=findViewById(R.id.value3);
        month22=findViewById(R.id.month31);
        month33=findViewById(R.id.month32);
        value22=findViewById(R.id.month312);
        value33=findViewById(R.id.month322);

        month2222=findViewById(R.id.month222);
        month3333=findViewById(R.id.month333);
        value2222=findViewById(R.id.month22222);
        value3333=findViewById(R.id.month33333);

        monthplus1=findViewById(R.id.monthplus11);
        monthplus2=findViewById(R.id.monthplus22);


        valueplus1=findViewById(R.id.monthplus1111);
        valueplus2=findViewById(R.id.monthplus2222);

        mon1=findViewById(R.id.mon1);
        mon2=findViewById(R.id.mon2);
        mon3=findViewById(R.id.mon3);
        mon4=findViewById(R.id.mon4);
        mon5=findViewById(R.id.mon5);
        mon6=findViewById(R.id.mon6);
        mon7=findViewById(R.id.mon7);
        mon8=findViewById(R.id.mon8);
        mon9=findViewById(R.id.mon9);
        mon10=findViewById(R.id.mon11);
        mon11=findViewById(R.id.mon10);
        mon12=findViewById(R.id.mon12);

        val1=findViewById(R.id.val1);
        val2=findViewById(R.id.val2);
        val3=findViewById(R.id.val3);
        val4=findViewById(R.id.val4);
        val5=findViewById(R.id.val5);
        val6=findViewById(R.id.val6);
        val7=findViewById(R.id.val7);
        val8=findViewById(R.id.val8);
        val9=findViewById(R.id.val9);
        val10=findViewById(R.id.val10);
        val11=findViewById(R.id.val11);
        val12=findViewById(R.id.val12);


        mon01=findViewById(R.id.mon01);
        mon02=findViewById(R.id.mon02);
        mon13=findViewById(R.id.mon13);
        mon14=findViewById(R.id.mon13);
        mon15=findViewById(R.id.mon14);
        mon16=findViewById(R.id.mon15);
        mon17=findViewById(R.id.mon16);
        mon18=findViewById(R.id.mon17);
        mon19=findViewById(R.id.mon18);
        mon110=findViewById(R.id.mon19);
        mon111=findViewById(R.id.mon110);
        mon112=findViewById(R.id.mon111);

        val01=findViewById(R.id.val01);
        val02=findViewById(R.id.val02);
        val13=findViewById(R.id.val13);
        val14=findViewById(R.id.val14);
        val15=findViewById(R.id.val15);
        val16=findViewById(R.id.val16);
        val17=findViewById(R.id.val17);
        val18=findViewById(R.id.val18);
        val19=findViewById(R.id.val19);
        val110=findViewById(R.id.val110);
        val111=findViewById(R.id.val111);
        val112=findViewById(R.id.val112);

        m1=findViewById(R.id.m1);
        m2=findViewById(R.id.m2);
        m3=findViewById(R.id.m3);
        m4=findViewById(R.id.m4);
        m5=findViewById(R.id.m5);
        m6=findViewById(R.id.m6);
        m7=findViewById(R.id.m7);
        m8=findViewById(R.id.m8);
        m9=findViewById(R.id.m9);
        m10=findViewById(R.id.m10);
        m11=findViewById(R.id.m111);
        m12=findViewById(R.id.m12);

        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        v4=findViewById(R.id.v4);
        v5=findViewById(R.id.v5);
        v6=findViewById(R.id.v6);
        v7=findViewById(R.id.v7);
        v8=findViewById(R.id.v8);
        v9=findViewById(R.id.v9);
        v10=findViewById(R.id.v10);
        v11=findViewById(R.id.v11);
        v12=findViewById(R.id.v12);



        montharrays=new ArrayList<>();
        commodity=getIntent().getStringExtra("commoidty");
        currentYearLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCHart();
            }
        });
        yearlinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearsCHart();
            }
        });
        historicallink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistoricalYearsCHart();
            }
        });
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url=API.concat("historic/cpi/"+responce+"/"+commodity+"?className=all_cities&subuserType=0");
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("",""+response);

                try {
                    JSONArray jsonArray=response.getJSONArray("currentYear");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String month=jsonObject.getString("month");
                        String value=jsonObject.getString("value");
                        montharrays.add(month);
                        valuearrays.add(value);
                        Log.d("",""+valuearrays);

                        try {
                            yValsFuel.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    JSONArray jsonArray1=response.getJSONArray("Month+3");
                    for(int i=0;i<jsonArray1.length();i++)
                    {
                        JSONObject jsonObject=jsonArray1.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        montharrays1.add(year);
                        valuearrays1.add(value);
                        String val="Month+3";
                        String v1="[";String v2="]";
                        textmonth.setText(val.concat(v1).concat(month).concat(v2));
                        Log.d("",""+valuearrays);

                        try {
                            yValsFuelyear3.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray jsonArray2=response.getJSONArray("Month+2");
                    for(int i=0;i<jsonArray2.length();i++)
                    {
                        JSONObject jsonObject=jsonArray2.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        montharrays2.add(year);
                        valuearrays2.add(value);
                        String val="Month+2";
                        String v1="[";String v2="]";
                        textmonth2.setText(val.concat(v1).concat(month).concat(v2));
                        Log.d("",""+valuearrays);
                        try {
                            yValsFuelyear4.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }

                    JSONArray jsonArray3=response.getJSONArray("Month+1");
                    for(int i=0;i<jsonArray3.length();i++)
                    {
                        JSONObject jsonObject=jsonArray3.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        montharrays3.add(year);
                        valuearrays3.add(value);
                        String val="Month+1";
                        String v1="[";String v2="]";
                        textView3.setText(val.concat(v1).concat(month).concat(v2));
                        Log.d("",""+valuearrays);

                        try {
                            yValsFuelyear5.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    JSONArray jsonArray4=response.getJSONArray("Year-0");
                    for(int i=0;i<jsonArray4.length();i++)
                    {
                        JSONObject jsonObject=jsonArray4.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        year1.setText(year);
                        montharrays4.add(month);
                        valuearrays4.add(value);
                        try {
                            yValsFuelyear.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray jsonArray5=response.getJSONArray("Year-1");
                    for(int i=0;i<jsonArray5.length();i++)
                    {
                        JSONObject jsonObject=jsonArray5.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        year2.setText(year);
                        montharrays5.add(month);
                        valuearrays5.add(value);
                        try {
                            yValsFuelyear1.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray jsonArray6=response.getJSONArray("Year-2");
                    for(int i=0;i<jsonArray6.length();i++)
                    {
                        JSONObject jsonObject=jsonArray6.getJSONObject(i);
                        String year=jsonObject.getString("year");
                        String value=jsonObject.getString("value");
                        String month=jsonObject.getString("month");
                        year3.setText(year);
                        montharrays6.add(month);
                        valuearrays6.add(value);
                        try {
                            yValsFuelyear2.add(new Entry((Float.valueOf(value)), i));
                            Log.d("",""+yValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }


                    month1.setText(montharrays.get(0));
                    month2.setText(montharrays.get(1));
                    month3.setText(montharrays.get(2));
                    value1.setText(valuearrays.get(0));
                    value2.setText(valuearrays.get(1));
                    value3.setText(valuearrays.get(2));
                    month22.setText(montharrays1.get(0));
                    month33.setText(montharrays1.get(1));
                    value22.setText(valuearrays1.get(0));
                    value33.setText(valuearrays1.get(1));
                    month2222.setText(montharrays2.get(0));
                    month3333.setText(montharrays2.get(1));
                    value2222.setText(valuearrays2.get(0));
                    value3333.setText(valuearrays2.get(1));
                    monthplus1.setText(montharrays3.get(0));
                    monthplus2.setText(montharrays3.get(1));
                    valueplus1.setText(valuearrays3.get(0));
                    valueplus2.setText(valuearrays3.get(1));

                    mon1.setText(montharrays4.get(0));
                    mon2.setText(montharrays4.get(1));
                    mon3.setText(montharrays4.get(2));
                    mon4.setText(montharrays4.get(3));
                   /* mon5.setText(montharrays4.get(4));
                    mon6.setText(montharrays4.get(5));
                    mon7.setText(montharrays4.get(6));
                    mon8.setText(montharrays4.get(7));
                    mon9.setText(montharrays4.get(8));
                    mon10.setText(montharrays4.get(9));
                    mon11.setText(montharrays4.get(10));
                    mon12.setText(montharrays4.get(11));*/

                    val1.setText(valuearrays4.get(0));
                    val2.setText(valuearrays4.get(1));
                    val3.setText(valuearrays4.get(2));
                    val4.setText(valuearrays4.get(3));
                   /* val5.setText(valuearrays4.get(4));
                    val6.setText(valuearrays4.get(5));
                    val7.setText(valuearrays4.get(6));
                    val8.setText(valuearrays4.get(7));
                    val9.setText(valuearrays4.get(8));
                    val10.setText(valuearrays4.get(9));
                    val11.setText(valuearrays4.get(10));
                    val12.setText(valuearrays4.get(10));*/

                    mon01.setText(montharrays5.get(0));
                    mon02.setText(montharrays5.get(1));
                    mon13.setText(montharrays5.get(2));
                    mon14.setText(montharrays5.get(3));
                    mon15.setText(montharrays5.get(4));
                    mon16.setText(montharrays5.get(5));
                    mon17.setText(montharrays5.get(6));
                    mon18.setText(montharrays5.get(7));
                    mon19.setText(montharrays5.get(8));
                    mon110.setText(montharrays5.get(9));
                    mon111.setText(montharrays5.get(10));
                    mon112.setText(montharrays5.get(11));

                    val01.setText(valuearrays5.get(0));
                    val02.setText(valuearrays5.get(1));
                    val13.setText(valuearrays5.get(2));
                    val14.setText(valuearrays5.get(3));
                    val15.setText(valuearrays5.get(4));
                    val16.setText(valuearrays5.get(5));
                    val17.setText(valuearrays5.get(6));
                    val18.setText(valuearrays5.get(7));
                    val19.setText(valuearrays5.get(8));
                    val110.setText(valuearrays5.get(9));
                    val111.setText(valuearrays5.get(10));
                    val112.setText(valuearrays5.get(11));

                    m1.setText(montharrays6.get(0));
                    m2.setText(montharrays6.get(1));
                    m3.setText(montharrays6.get(2));
                    m4.setText(montharrays6.get(3));
                    m5.setText(montharrays6.get(4));
                    m6.setText(montharrays6.get(5));
                    m7.setText(montharrays6.get(6));
                    m8.setText(montharrays6.get(7));
                    m9.setText(montharrays6.get(8));
                    m10.setText(montharrays6.get(9));
                    m11.setText(montharrays6.get(10));
                    m12.setText(montharrays6.get(11));
                    v1.setText(valuearrays6.get(0));
                    v2.setText(valuearrays6.get(1));
                    v3.setText(valuearrays6.get(2));
                    v4.setText(valuearrays6.get(3));
                    v5.setText(valuearrays6.get(4));
                    v6.setText(valuearrays6.get(5));
                    v7.setText(valuearrays6.get(6));
                    v8.setText(valuearrays6.get(7));
                    v9.setText(valuearrays6.get(8));
                    v10.setText(valuearrays6.get(9));
                    v11.setText(valuearrays6.get(10));
                    v12.setText(valuearrays6.get(11));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
    private void showCHart() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.currentlinks_layout, null);
        mChartFuel = (LineChart)alertLayout.findViewById(R.id.currentlinkschart);
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
        leftAxis.setAxisMaxValue(263.1f);
        leftAxis.setAxisMinValue(230.2f);
        leftAxis.setGranularity(0.01f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChartFuel.getAxisLeft().setLabelCount(15, true);
        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        LineDataSet set1 = new LineDataSet(yValsFuel, "");
        set1.setLineWidth(5f);
        set1.setCircleRadius(3f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
        LineData data = new LineData(montharrays, set1);
        mChartFuel.setData(data);
        mChartFuel.invalidate();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Consumer Cities Avg Price Current Year Chart :");
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

    private void showYearsCHart()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.line_layout, null);
        final ImageView imageView=alertLayout.findViewById(R.id.image1);
        final ImageView imageView1=alertLayout.findViewById(R.id.image);
        final ImageView imageView2=alertLayout.findViewById(R.id.image2);
        mChartFuel = (LineChart)alertLayout.findViewById(R.id.currentlinkschart);
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
        leftAxis.setAxisMaxValue(253.9f);
        leftAxis.setAxisMinValue(245.9f);
        leftAxis.setGranularity(0.001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChartFuel.getAxisLeft().setLabelCount(15, true);

        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        final ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
        final LineDataSet set1 = new LineDataSet(yValsFuelyear, "");
        set1.setLineWidth(0f);
        set1.setCircleRadius(0f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
        //year1
        final LineDataSet set2 = new LineDataSet(yValsFuelyear1, "");
        set2.setLineWidth(1.5f);
        set2.setCircleRadius(2f);
        set2.setDrawFilled(true);
        set2.setColor(Color.WHITE);
        set2.setValueTextColor(Color.WHITE);
        set2.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        final LineDataSet set3 = new LineDataSet(yValsFuelyear2, "");
        set3.setLineWidth(0.5f);
        set3.setCircleRadius(2f);
        set3.setDrawFilled(true);
        set3.setColor(Color.WHITE);
        set3.setValueTextColor(Color.WHITE);
        set3.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        lineDataSets.add(set1);
        lineDataSets.add(set2);
        lineDataSets.add(set3);
        LineData data1 = new LineData(montharrays5,lineDataSets);
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
                   // lineDataSets.add(set2);
                   // lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = false;
                }
                else {
                    lineDataSets.add(set1);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.weather_textview_top_background);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set2);
                   // lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = true;
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showingSecondImage==true)
                {
                    lineDataSets.remove(set2);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                  //  lineDataSets.add(set1);
                  //  lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = false;
                }
                else {
                    lineDataSets.add(set2);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set1);
                   // lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = true;
                }

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showingThirdImage==true)
                {
                    lineDataSets.remove(set3);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set1);
                   // lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = false;
                }
                else {
                    lineDataSets.add(set3);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.ng);
                    Log.d("",""+lineDataSets);
                    //lineDataSets.add(set1);
                    //lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays5,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = true;
                }

            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Consumer Index Avg Price Yearly Chart :");
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

    private void showHistoricalYearsCHart ()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.yearchart_layout, null);
        mChartFuel = (LineChart)alertLayout.findViewById(R.id.currentlinkschart);
        final ImageView imageView1=alertLayout.findViewById(R.id.image);
        final ImageView imageView2=alertLayout.findViewById(R.id.image1);
        final ImageView imageView3=alertLayout.findViewById(R.id.image2);
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
        leftAxis.setAxisMaxValue(249.9f);
        leftAxis.setAxisMinValue(240.9f);
        leftAxis.setGranularity(0.1f);

        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChartFuel.getAxisLeft().setLabelCount(15, true);
        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        final LineDataSet set1 = new LineDataSet(yValsFuelyear3, "");
        set1.setLineWidth(0f);
        set1.setCircleRadius(0f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));
        //LineData data = new LineData(montharrays1, set1);
       // mChartFuel.setData(data);
        //month2
        final LineDataSet set2 = new LineDataSet(yValsFuelyear4, "");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(5f);
        set2.setDrawFilled(true);
        set2.setColor(Color.WHITE);
        set2.setValueTextColor(Color.WHITE);
        set2.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
       // LineData data2 = new LineData(montharrays2, set2);
        //mChartFuel.setData(data2);
        //month3
        final LineDataSet set32 = new LineDataSet(yValsFuelyear5, "");
        set32.setLineWidth(2.5f);
        set32.setCircleRadius(5f);
        set32.setDrawFilled(true);
        set32.setColor(Color.WHITE);
        set32.setValueTextColor(Color.WHITE);
        set32.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        final ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
        lineDataSets.add(set1);
        lineDataSets.add(set2);
        lineDataSets.add(set32);
        LineData data3 = new LineData(montharrays3, lineDataSets);
        mChartFuel.setData(data3);
        mChartFuel.invalidate();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingFirstImage1==true)
                {
                    lineDataSets.remove(set1);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set2);
                   // lineDataSets.add(set32);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage1 = false;
                }
                else {
                    lineDataSets.add(set1);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.weather_textview_top_background);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set2);
                   // lineDataSets.add(set32);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage1 = true;
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingSecondImage1==true)
                {
                    lineDataSets.remove(set2);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                  //  lineDataSets.add(set1);
                  //  lineDataSets.add(set32);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage1 = false;
                }
                else {
                    lineDataSets.add(set2);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                    ///lineDataSets.add(set1);
                    //lineDataSets.add(set32);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage1 = true;
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingThirdImage1==true)
                {
                    lineDataSets.remove(set32);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    //lineDataSets.add(set1);
                    //lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage1 = false;
                }
                else {
                    lineDataSets.add(set32);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setImageResource(R.drawable.nn);
                    Log.d("",""+lineDataSets);
                    //lineDataSets.add(set1);
                    //lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(montharrays3,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage1 = true;
                }
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Consumer Cities Avg Price Current Year Chart :");
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
}
