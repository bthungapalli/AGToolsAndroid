package com.agtools.agtools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agtools.agtools.Constants.ApiConstants;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by ranjithkumar.g on 4/2/2018.
 */

public class GasGraphAcitivity extends AppCompatActivity {
    TextView threeweeks,twoweeks,previousweek,currentweek;
    TextView year1,year2,year3,val1,val2,val3;
    TextView year11,year12,year13,val11,val12,val13;
    List<String> weeksList=new ArrayList<>();
    List<String> yearlist=new ArrayList<>();
    List<String> valList=new ArrayList<>();
    List<String> yearlist1=new ArrayList<>();
    List<String> valList1=new ArrayList<>();
    List<String> weeksListgas=new ArrayList<>();
    List<String> yearlistgas=new ArrayList<>();
    List<String> valListgas=new ArrayList<>();
    List<String> yearlistgas1=new ArrayList<>();
    List<String> valListgas1=new ArrayList<>();
    List<String> valListgas11=new ArrayList<>();
    List<String> valListgas22=new ArrayList<>();
    ArrayList<Entry> gasValsFuel = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuelyear1 = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuelyear2 = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuelyear3 = new ArrayList<Entry>();
    public LineChart mChartGas, mChartFuel;
    TextView t1,t2,t3,t4,t5,t6;
    String selctCommidtyName;
    boolean showingFirstImage=true;
    boolean showingSecondImage=true;
    boolean showingThirdImage=true;

    ArrayList <String> keysArray = new ArrayList<String>();

    TextView week18,text34,week19,week112;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_layout_historic);
        Button button=findViewById(R.id.prices);
        week18=findViewById(R.id.week12);
        text34=findViewById(R.id.text34);
        week19=findViewById(R.id.week10);
        week112=findViewById(R.id.week112);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        threeweeks= (TextView) findViewById(R.id.threeweeksago);
        twoweeks= (TextView) findViewById(R.id.twoweeksago);
        previousweek= (TextView) findViewById(R.id.previousweek);
        currentweek= (TextView) findViewById(R.id.currentweek);
        TextView textView=findViewById(R.id.currentlinks);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLinks();
            }
        });
        TextView textView1=findViewById(R.id.yearslink);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentYearLinks();
            }
        });
        year1= (TextView) findViewById(R.id.year1);
        year2= (TextView) findViewById(R.id.year2);
        year3= (TextView) findViewById(R.id.year3);

        val1= (TextView) findViewById(R.id.year11);
        val2= (TextView) findViewById(R.id.year22);
        val3= (TextView) findViewById(R.id.year33);
        year11= (TextView) findViewById(R.id.year111);
        year12= (TextView) findViewById(R.id.year222);;
        year13= (TextView) findViewById(R.id.year333);
        val11= (TextView) findViewById(R.id.year1111);
        val12= (TextView) findViewById(R.id.year2222);
        val13= (TextView) findViewById(R.id.year3333);
        t1= (TextView) findViewById(R.id.year11111);
        t2= (TextView) findViewById(R.id.year22222);
        t3= (TextView) findViewById(R.id.year33333);
        t4= (TextView) findViewById(R.id.year111111);
        t5= (TextView) findViewById(R.id.year222222);
        t6= (TextView) findViewById(R.id.year333333);
        selctCommidtyName=getIntent().getStringExtra("commodity");
        Log.d("",""+selctCommidtyName);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String userid=sharedPreferences.getString("userid","");
        Log.d("",""+userid);
        String url= ApiConstants.API.concat("historic/oilprices/"+userid+"/"+selctCommidtyName+"?type=fuel&subuserType=0");
        Log.d("",""+url);
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("",""+response);
                Iterator<String> iter = response.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    Log.d("",""+key);
                    keysArray.add(key);
                    Log.d("keysArray","keysArray"+keysArray);

                    try {
                        Object value = response.get(key);
                        Log.d("",""+value);
                    } catch (JSONException e) {
                        // Something went wrong!
                    }
                }


                try {
                    text34.setText(keysArray.get(5));
                    JSONArray jsonArray=response.getJSONArray(keysArray.get(5));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray.getJSONObject(i);
                        String holidayDateUsa = jsonObjectUsa.getString("value");
                        //gasHistoric = new ExchangeHistoric(holidayDateUsa);
                        weeksListgas.add(holidayDateUsa);
                        try {
                            gasValsFuel.add(new Entry((Float.valueOf(holidayDateUsa)), i));
                            Log.d("",""+gasValsFuel);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                    week18.setText(keysArray.get(1));
                    JSONArray jsonArray1=response.getJSONArray(keysArray.get(1));
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray1.getJSONObject(i);
                        String holidayDateUsa = jsonObjectUsa.getString("week");
                        yearlistgas.add(holidayDateUsa);

                    }
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray1.getJSONObject(i);
                        String val=jsonObjectUsa.getString("value");
                        valListgas.add(val);
                        try {
                            gasValsFuelyear1.add(new Entry((Float.valueOf(val)), i));
                            Log.d("",""+gasValsFuelyear1);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    week19.setText(keysArray.get(2));
                    JSONArray jsonArray2=response.getJSONArray(keysArray.get(2));
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray2.getJSONObject(i);
                        String holidayDateUsa = jsonObjectUsa.getString("week");
                        yearlistgas1.add(holidayDateUsa);

                    }
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray2.getJSONObject(i);
                        String val=jsonObjectUsa.getString("value");
                        valListgas1.add(val);
                        try {
                            gasValsFuelyear2.add(new Entry((Float.valueOf(val)), i));
                            Log.d("",""+gasValsFuelyear2);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    week112.setText(keysArray.get(3));
                    JSONArray jsonArray3=response.getJSONArray(keysArray.get(3));
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray3.getJSONObject(i);
                        String holidayDateUsa = jsonObjectUsa.getString("week");
                        valListgas11.add(holidayDateUsa);
                    }
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        JSONObject jsonObjectUsa = jsonArray3.getJSONObject(i);
                        String val=jsonObjectUsa.getString("value");
                        valListgas22.add(val);
                        try {
                            gasValsFuelyear3.add(new Entry((Float.valueOf(val)), i));
                            Log.d("",""+gasValsFuelyear3);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                      Log.d("weeksListgas","weeksListgas"+weeksListgas);
                    threeweeks.setText(weeksListgas.get(0));
                    twoweeks.setText(weeksListgas.get(1));
                    previousweek.setText(weeksListgas.get(2));
                    currentweek.setText(weeksListgas.get(3));


                    year1.setText(yearlistgas.get(0));
                    year2.setText(yearlistgas.get(1));
                    year3.setText(yearlistgas.get(2));

                    val1.setText(valListgas.get(0));
                    val2.setText(valListgas.get(1));
                    val3.setText(valListgas.get(2));

                    year11.setText(yearlistgas1.get(0));
                    year12.setText(yearlistgas1.get(1));
                    year13.setText(yearlistgas1.get(2));

                    val11.setText(valListgas1.get(0));
                    val12.setText(valListgas1.get(1));
                    val13.setText(valListgas1.get(2));

                    t1.setText(valListgas11.get(0));
                    t2.setText(valListgas11.get(1));
                    t3.setText(valListgas11.get(2));
                    t4.setText(valListgas22.get(0));
                    t5.setText(valListgas22.get(1));
                    t6.setText(valListgas22.get(2));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GasGraphAcitivity.this);
        requestQueue.add(jsonObjectRequest);
    }
    private  void getCurrentLinks()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.gas_graph, null);
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
        leftAxis.setAxisMaxValue(4.07f);
        leftAxis.setAxisMinValue(2.96f);
        leftAxis.setGranularity(0.001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        ArrayList<String> montharrays6=new ArrayList<>();
        montharrays6.add("3 weeeks ago");
        montharrays6.add("2 weeks ago");
        montharrays6.add("Previous week");
        montharrays6.add("current week");
        mChartFuel.getAxisLeft().setLabelCount(15, true);

        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        LineDataSet set1 = new LineDataSet(gasValsFuel, "");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(5f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        LineData data = new LineData(montharrays6, set1);
        mChartFuel.setData(data);
        mChartFuel.invalidate();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Diesel Fuel Prices (dollars per gallon) :");
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
    private  void getCurrentYearLinks()
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.gas_graph2, null);
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
        leftAxis.setAxisMaxValue(3.20f);
        leftAxis.setAxisMinValue(1.6f);
        leftAxis.setGranularity(0.001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        final ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
        final LineDataSet set1 = new LineDataSet(gasValsFuelyear1, "");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(5f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));

        final LineDataSet set2 = new LineDataSet(gasValsFuelyear2, "");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(5f);
        set2.setDrawFilled(true);
        set2.setColor(Color.WHITE);
        set2.setValueTextColor(Color.WHITE);
        set2.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

        final LineDataSet set3 = new LineDataSet(gasValsFuelyear3, "");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(5f);
        set3.setDrawFilled(true);
        set3.setColor(Color.WHITE);
        set3.setValueTextColor(Color.WHITE);
        set3.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        lineDataSets.add(set1);
        lineDataSets.add(set2);
        lineDataSets.add(set3);
        LineData data = new LineData(yearlistgas, lineDataSets);
        mChartFuel.setData(data);
        mChartFuel.getAxisLeft().setLabelCount(15, true);
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
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
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
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = true;
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingSecondImage==true)
                {
                    lineDataSets.remove(set2);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set1);
                   // lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = false;
                }
                else {
                    lineDataSets.add(set2);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                   // lineDataSets.add(set1);
                   // lineDataSets.add(set3);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = true;
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingThirdImage==true)
                {
                    lineDataSets.remove(set3);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                  //  lineDataSets.add(set1);
                   // lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = false;
                }
                else {
                    lineDataSets.add(set3);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setImageResource(R.drawable.ng);
                    Log.d("",""+lineDataSets);
                  //  lineDataSets.add(set1);
                  //  lineDataSets.add(set2);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(yearlistgas,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = true;
                }
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Diesel Fuel Prices (dollars per gallon) :");
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
