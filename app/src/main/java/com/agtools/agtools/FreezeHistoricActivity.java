package com.agtools.agtools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class FreezeHistoricActivity extends AppCompatActivity {
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;

    ArrayList<Jsuice> dataModels= new ArrayList<>();
    ArrayList<Jsuice> dataModels2015= new ArrayList<>();
    ArrayList<Jsuice>  dataModels2016= new ArrayList<>();
    ArrayList<Jsuice>  dataModels20161= new ArrayList<>();
    ArrayList<Jsuice>  dataModels2017= new ArrayList<>();
    ArrayList<Jsuice>  dataModels20171= new ArrayList<>();
    ListView listView,listsecond,listthird,listfourth;
    Jsuice jsuice;
    Jsuice jsuice1;
    Jsuice jsuice2; Jsuice jsuice3;
    private  JuiceCustomAdapter adapter;
    TextView firstyear,thirdyear,fourthyear;
    TextView weblinks;
    public LineChart mChartGas, mChartFuel;
    boolean showingFirstImage=true;
    boolean showingSecondImage=true;
    boolean showingThirdImage=true;
    boolean showingFourthImage=true;
    boolean showingFifthImage=true;
    boolean showingSixthImage=true;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<Entry> gasValsFuel2015week = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuel2015accum = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuel2016week = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuel2016accum = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuel2017week = new ArrayList<Entry>();
    ArrayList<Entry> gasValsFuel2017accum = new ArrayList<Entry>();
    String year1,year2,year3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice_historic);
        Button button=findViewById(R.id.user1);
        weblinks=findViewById(R.id.weblinks);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listView=findViewById(R.id.list);
        listfourth=findViewById(R.id.listfourth);
        String commodity=getIntent().getStringExtra("commodity");
        listsecond=findViewById(R.id.listsecond);
        listthird=findViewById(R.id.listthird);
        firstyear=findViewById(R.id.secondyear);
        fourthyear=findViewById(R.id.fourthyear);
        thirdyear=findViewById(R.id.thirdyear);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url= ApiConstants.API.concat("historic/juiceVolume/"+responce+"/"+commodity+"/3?subuserType=0");
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("",""+response);
                if(response.length()>0){
                    JSONArray jsonObject= null;
                    try {
                        jsonObject = response.getJSONArray("2018");
                        for(int i=0;i<jsonObject.length();i++)
                        {
                            JSONObject jsonObject1=jsonObject.getJSONObject(i);
                            year1=jsonObject1.getString("year");
                            String month=jsonObject1.getString("month");
                            String datevalue=jsonObject1.getString("dateValue");
                            String weekvalue=jsonObject1.getString("weekValue");
                            String accumvalue=jsonObject1.getString("accumValue");
                            String datefield=jsonObject1.getString("dateField");
                            String graphval=month.concat(datevalue);
                            try {
                                gasValsFuel2015week.add(new Entry((Float.valueOf(weekvalue)), i));
                                Log.d("",""+gasValsFuel2015week);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            try {
                                gasValsFuel2015accum.add(new Entry((Float.valueOf(accumvalue)), i));
                                Log.d("acc","acc"+gasValsFuel2015accum);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            arrayList.add(graphval);
                            firstyear.setText(year1);
                            dataModels= new ArrayList<>();
                            /*dataModels2015=new ArrayList<>();*/
                            jsuice=new Jsuice(month,datevalue);
                            jsuice1=new Jsuice(accumvalue,weekvalue);
                            dataModels.add(jsuice);
                            dataModels2015.add(jsuice1);
                            Log.d("",""+dataModels2015);

                        }
                        adapter= new JuiceCustomAdapter(dataModels,getApplicationContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter= new JuiceCustomAdapter(dataModels2015,getApplicationContext());
                        listsecond.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        JSONArray jsonArray=response.getJSONArray("2016");
                        Log.d("",""+jsonArray);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            year2=jsonObject1.getString("year");
                            String month=jsonObject1.getString("month");
                            String datevalue=jsonObject1.getString("dateValue");
                            String weekvalue=jsonObject1.getString("weekValue");
                            String accumvalue=jsonObject1.getString("accumValue");
                            String datefield=jsonObject1.getString("dateField");
                            String graphval=month.concat(datevalue);
                            try {
                                gasValsFuel2016week.add(new Entry((Float.valueOf(weekvalue)), i));
                                Log.d("",""+gasValsFuel2016week);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            try {
                                gasValsFuel2016accum.add(new Entry((Float.valueOf(accumvalue)), i));
                                Log.d("acc","acc"+gasValsFuel2016accum);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            arrayList.add(graphval);
                            thirdyear.setText(year2);
                            jsuice=new Jsuice(month,datevalue);
                            jsuice2=new Jsuice(accumvalue,weekvalue);
                            dataModels2016.add(jsuice);
                            dataModels20161.add(jsuice2);

                        }
                        adapter= new JuiceCustomAdapter(dataModels2016,getApplicationContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter= new JuiceCustomAdapter(dataModels20161,getApplicationContext());
                        listthird.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        JSONArray jsonArray1=response.getJSONArray("2017");
                        for(int i=0;i<jsonArray1.length();i++)
                        {
                            JSONObject jsonObject1=jsonArray1.getJSONObject(i);
                             year3=jsonObject1.getString("year");
                            String month=jsonObject1.getString("month");
                            String datevalue=jsonObject1.getString("dateValue");
                            String weekvalue=jsonObject1.getString("weekValue");
                            String accumvalue=jsonObject1.getString("accumValue");
                            String datefield=jsonObject1.getString("dateField");
                            String graphval=month.concat(datevalue);
                            try {
                                gasValsFuel2017week.add(new Entry((Float.valueOf(weekvalue)), i));
                                Log.d("",""+gasValsFuel2017week);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            try {
                                gasValsFuel2017accum.add(new Entry((Float.valueOf(accumvalue)), i));
                                Log.d("acc","acc"+gasValsFuel2017accum);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            arrayList.add(graphval);
                            jsuice=new Jsuice(month,datevalue);
                            fourthyear.setText(year3);
                            jsuice3=new Jsuice(accumvalue,weekvalue);
                            dataModels2017.add(jsuice);
                            dataModels20171.add(jsuice3);
                        }
                        adapter= new JuiceCustomAdapter(dataModels2017,getApplicationContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter= new JuiceCustomAdapter(dataModels20171,getApplicationContext());
                        listfourth.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Log.d("fg","fg"+arrayList);
                        final Comparator<String> dateCompare = new Comparator<String>() {

                            public int compare(String o1, String o2) {

                                SimpleDateFormat s = new SimpleDateFormat("MMM");
                                Date s1 = null;
                                Date s2 = null;
                                try {
                                    s1 = s.parse(o1);
                                    s2 = s.parse(o2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return s1.compareTo(s2);
                            }
                        };

                        Collections.sort(arrayList, dateCompare);

                        System.out.print(arrayList);
                        Log.d("fgg","fgg"+arrayList);
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
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
        weblinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGraphValues();
            }
        });
    }

    private void getGraphValues() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.freezegraph, null);
        mChartFuel = (LineChart)alertLayout.findViewById(R.id.currentlinkschart);
        final ImageView th1=alertLayout.findViewById(R.id.image1);
        final ImageView th2=alertLayout.findViewById(R.id.image2);
        final ImageView th3=alertLayout.findViewById(R.id.image3);
        final ImageView th4=alertLayout.findViewById(R.id.image4);
        final ImageView th5=alertLayout.findViewById(R.id.image5);
        final ImageView th6=alertLayout.findViewById(R.id.image6);
        TextView th11=alertLayout.findViewById(R.id.th1);
        TextView th12=alertLayout.findViewById(R.id.th2);
        TextView th13=alertLayout.findViewById(R.id.th3);
        TextView th14=alertLayout.findViewById(R.id.th4);
        TextView th15=alertLayout.findViewById(R.id.th5);
        TextView th16=alertLayout.findViewById(R.id.th6);
        if(year1!=null){
            // Do you work here on success
            th11.setText(year1.concat("Week"));
            th12.setText(year1.concat("Accum"));
            th13.setText(year2.concat("Week"));
            th14.setText(year2.concat("Accum"));
            th15.setText(year3.concat("Week"));
            th16.setText(year3.concat("Accum"));
        }else{
            // null response or Exception occur
        }




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
        leftAxis.setAxisMaxValue(160000);
        leftAxis.setAxisMinValue(0);
        leftAxis.setGranularity(0.001f);
        leftAxis.setTextColor(Color.WHITE);
        mChartFuel.getAxisRight().setEnabled(false);
        mChartFuel.getAxisRight().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setTextColor(Color.WHITE);
        mChartFuel.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartFuel.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        XAxis xaxis = mChartFuel.getXAxis();
        xaxis.setAxisMaxValue(30f);
        xaxis.setAxisMinValue(0f);

        //  dont forget to refresh the drawing
        mChartFuel.invalidate();
        final ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
        final LineDataSet set1 = new LineDataSet(gasValsFuel2015week, "");
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(5f);
        set1.setDrawFilled(true);
        set1.setColor(Color.WHITE);
        set1.setValueTextColor(Color.WHITE);
        set1.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.weather_stroke));
        final LineDataSet set2 = new LineDataSet(gasValsFuel2015accum, "");
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(5f);
        set2.setDrawFilled(true);
        set2.setColor(Color.WHITE);
        set2.setValueTextColor(Color.WHITE);
        set2.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.graphcolorcode));

        final LineDataSet set3 = new LineDataSet(gasValsFuel2016week, "");
        set3.setLineWidth(2.5f);
        set3.setCircleRadius(5f);
        set3.setDrawFilled(true);
        set3.setColor(Color.WHITE);
        set3.setValueTextColor(Color.WHITE);
        set3.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        final LineDataSet set4 = new LineDataSet(gasValsFuel2016accum, "");
        set4.setLineWidth(2.5f);
        set4.setCircleRadius(5f);
        set4.setDrawFilled(true);
        set4.setColor(Color.WHITE);
        set4.setValueTextColor(Color.WHITE);
        set4.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        final LineDataSet set5 = new LineDataSet(gasValsFuel2017week, "");
        set5.setLineWidth(2.5f);
        set5.setCircleRadius(5f);
        set5.setDrawFilled(true);
        set5.setColor(Color.WHITE);
        set5.setValueTextColor(Color.WHITE);
        set5.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.weather_stroke));
        final LineDataSet set6 = new LineDataSet(gasValsFuel2017accum, "");
        set6.setLineWidth(2.5f);
        set6.setCircleRadius(5f);
        set6.setDrawFilled(true);
        set6.setColor(Color.WHITE);
        set6.setValueTextColor(Color.WHITE);
        set6.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        lineDataSets.add(set1);
        lineDataSets.add(set1);
        lineDataSets.add(set2);
        lineDataSets.add(set3);  lineDataSets.add(set4);
        lineDataSets.add(set5);
        lineDataSets.add(set6);
        LineData data = new LineData(arrayList, lineDataSets);
        mChartFuel.setData(data);
        mChartFuel.invalidate();
        th1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingFirstImage==true)
                {
                    lineDataSets.remove(set1);
                    th1.setVisibility(View.VISIBLE);
                    th1.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = false;
                }
                else {
                    lineDataSets.add(set1);
                    th1.setVisibility(View.VISIBLE);
                    th1.setImageResource(R.drawable.weather_textview_top_background);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFirstImage = true;
                }
            }
        });
        th2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingSecondImage==true)
                {
                    lineDataSets.remove(set2);
                    th2.setVisibility(View.VISIBLE);
                    th2.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = false;
                }
                else {
                    lineDataSets.add(set2);
                    th2.setVisibility(View.VISIBLE);
                    th2.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSecondImage = true;
                }
            }
        });
        th3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingThirdImage==true)
                {
                    lineDataSets.remove(set3);
                    th3.setVisibility(View.VISIBLE);
                    th3.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = false;
                }
                else {
                    lineDataSets.add(set3);
                    th3.setVisibility(View.VISIBLE);
                    th3.setImageResource(R.drawable.ng);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingThirdImage = true;
                }
            }
        });
        th4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingFourthImage==true)
                {
                    lineDataSets.remove(set4);
                    th4.setVisibility(View.VISIBLE);
                    th4.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFourthImage = false;
                }
                else {
                    lineDataSets.add(set4);
                    th4.setVisibility(View.VISIBLE);
                    th4.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFourthImage = true;
                }
            }
        });
        th5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingFifthImage==true)
                {
                    lineDataSets.remove(set5);
                    th5.setVisibility(View.VISIBLE);
                    th5.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFifthImage = false;
                }
                else {
                    lineDataSets.add(set5);
                    th5.setVisibility(View.VISIBLE);
                    th5.setImageResource(R.drawable.ng);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingFifthImage = true;
                }
            }
        });
        th6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingSixthImage==true)
                {
                    lineDataSets.remove(set6);
                    th6.setVisibility(View.VISIBLE);
                    th6.setImageResource(R.drawable.st);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSixthImage = false;
                }
                else {
                    lineDataSets.add(set6);
                    th6.setVisibility(View.VISIBLE);
                    th6.setImageResource(R.drawable.gn);
                    Log.d("",""+lineDataSets);
                    LineData data1 = new LineData(arrayList,lineDataSets);
                    mChartFuel.setData(data1);
                    mChartFuel.invalidate();
                    showingSixthImage = true;
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Freeze Volume Chart");
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
