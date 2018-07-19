package com.agtools.agtools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ranjithkumar.g on 4/23/2018.
 */

public class DetailActivityFive extends AppCompatActivity {
    TextView nametext,pricetext,usertypetext,finalpricetext,commoditytext;
    Button review;
    Button subscribe;
    String userid;
    JSONArray jsonArray;
    ArrayList<String> myJSONArray = new ArrayList<>();
    String url= ApiConstants.API.concat("subcribePaypal?type=mobile");
    TransparentProgressDialog transparentProgressDialog;
    String commodutytype11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        transparentProgressDialog=new TransparentProgressDialog(this,R.drawable.sgg);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        userid=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+userid);
        subscribe=findViewById(R.id.subscribe);
        review= (Button) findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final String name=getIntent().getStringExtra("package");
        final String price=getIntent().getStringExtra("price");
        final String commoditytype=getIntent().getStringExtra("commoditytype");
        Log.d("",""+price);
        Log.d("",""+commoditytype);
        final String commodutytype1=getIntent().getStringExtra("commodutytype1");
        Log.d("",""+commodutytype1);
        final String commodutytype2=getIntent().getStringExtra("commodutytype2");
        Log.d("",""+commodutytype2);
        final String commodutytype3=getIntent().getStringExtra("commodutytype3");
        Log.d("",""+commodutytype3);
        final String commodutytype4=getIntent().getStringExtra("commodutytype4");
        Log.d("",""+commodutytype4);
        final String finalpri=(price.substring(1, price.length()-3));
        Log.d("",""+finalpri);
        final String usertype=getIntent().getStringExtra("usertype");
        final String fruititem=getIntent().getStringExtra("fruititem");
        String commodityCount=getIntent().getStringExtra("commodityCount");
        final String fruititem1=getIntent().getStringExtra("fruititem1");
        final String fruititem2=getIntent().getStringExtra("fruititem2");
        final String fruititem3=getIntent().getStringExtra("fruititem3");
        String fruititem4=getIntent().getStringExtra("fruititem4");
        nametext= (TextView) findViewById(R.id.name);
        pricetext= (TextView) findViewById(R.id.price);
        usertypetext= (TextView) findViewById(R.id.usertype);
        finalpricetext= (TextView) findViewById(R.id.price1);
        commoditytext= (TextView) findViewById(R.id.Commodities);
        if(commodityCount.equals("1COMMODITY PER USER"))
        {
            nametext.setText("Package :"+name);
            pricetext.setText("Price :"+price);
            finalpricetext.setText("Final Price :"+price);
            usertypetext.setText("User Type :"+usertype);
            commoditytext.setText("Commodities :"+fruititem);
        }
        else if(commodityCount.equals("2COMMODITY PER USER")){
            nametext.setText("Package :"+name);
            pricetext.setText("Price :"+price);
            finalpricetext.setText("Final Price :"+price);
            usertypetext.setText("User Type :"+usertype);
            commoditytext.setText("Commodities :"+fruititem.concat(",").concat(fruititem1));
        }
        else if(commodityCount.equals("3COMMODITY PER USER"))
        {
            nametext.setText("Package :"+name);
            pricetext.setText("Price :"+price);
            finalpricetext.setText("Final Price :"+price);
            usertypetext.setText("User Type :"+usertype);
            commoditytext.setText("Commodities :"+fruititem.concat(",").concat(fruititem1).concat(",").concat(fruititem2));
        }
        else if(commodityCount.equals("4COMMODITY PER USER")){

            nametext.setText("Package :"+name);
            pricetext.setText("Price :"+price);
            finalpricetext.setText("Final Price :"+price);
            usertypetext.setText("User Type :"+usertype);
            commoditytext.setText("Commodities :"+fruititem.concat(",").concat(fruititem1).concat(",").concat(fruititem2).concat(",").concat(fruititem3));
        }
        else if(commodityCount.equals("5COMMODITY PER USER"))
        {
            nametext.setText("Package :"+name);
            pricetext.setText("Price :"+price);
            finalpricetext.setText("Final Price :"+price);
            usertypetext.setText("User Type :"+usertype);
            commoditytext.setText("Commodities :"+fruititem.concat(",").concat(fruititem1).concat(",").concat(fruititem2).concat(",").concat(fruititem3).concat(",").concat(fruititem4));
        }
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transparentProgressDialog.show();
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("userId", Integer.valueOf(userid));
                    // jsonObject.put("name", "Demo User");
                    // jsonObject.put("usertypeId",1 );
                    // jsonObject.put("subusertypeId",1);
                    //jsonObject.put("subscriptionId",1);
                    jsonObject.put("usertype", "GROWER");
                    //jsonObject.put("subusertype", usertype);
                    //jsonObject.put("subscriptionDetails", "PRESENT");
                    //jsonObject.put("subscriptionStatus",true);
                    //jsonObject.put("months",1);
                    // jsonObject.put("packageDetailId",6);
                    // jsonObject.put("packageId",2);
                    jsonObject.put("packageName", name);
                    jsonObject.put("price", finalpri);
                    jsonObject.put("finalPrice", finalpri);
                    //jsonObject.put("discount",0);
                    try {
                        JSONObject jsonObject1=new JSONObject();
                        jsonObject1.put("commodityId","5a1313dfb85c899bd0f2442d");
                        jsonObject1.put("commodityName",fruititem);
                        jsonObject1.put("commodityType",commoditytype);
                        JSONObject jsonObject2=new JSONObject();
                        jsonObject2.put("commodityId","5a1313dfb85c899bd0f2442d");
                        jsonObject2.put("commodityName",fruititem1);
                        jsonObject2.put("commodityType",commodutytype1);
                        JSONObject jsonObject3=new JSONObject();
                        jsonObject3.put("commodityId","5a1313dfb85c899bd0f2442d");
                        jsonObject3.put("commodityName",fruititem2);
                        jsonObject3.put("commodityType",commodutytype2);
                        JSONObject jsonObject4=new JSONObject();
                        jsonObject4.put("commodityId","5a1313dfb85c899bd0f2442d");
                        jsonObject4.put("commodityName",fruititem3);
                        jsonObject4.put("commodityType",commodutytype3);
                        JSONObject jsonObject5=new JSONObject();
                        jsonObject5.put("commodityId","5a1313dfb85c899bd0f2442d");
                        jsonObject5.put("commodityName",fruititem3);
                        jsonObject5.put("commodityType",commodutytype4);
                        jsonArray=new JSONArray();
                        jsonArray.put(jsonObject1);
                        jsonArray.put(jsonObject2);
                        jsonArray.put(jsonObject3);
                        jsonArray.put(jsonObject4);
                        jsonArray.put(jsonObject5);
                        Log.d("js","js"+jsonArray);
                        jsonObject.put("userpkgCommodity", jsonArray);
                        Log.d("hi","hi"+jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NukeSSLCerts.NukeSSLCerts();
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,  url,jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("",""+response);
                        transparentProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        // WebView webView = new WebView(getApplicationContext());
                        try {
                            String url = response.getString("approvalUrl");
                            Intent intent=new Intent(getApplicationContext(),WebActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // webView.loadUrl(url);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("",""+error.getMessage());
                        transparentProgressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "TimeOut Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailureError Error",
                                    Toast.LENGTH_LONG).show();
                            //TODO
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), "ServerError Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), "NetworkError Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), "ParseError Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });

    }
}
