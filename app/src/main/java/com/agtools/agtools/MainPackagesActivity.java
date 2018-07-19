package com.agtools.agtools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.agtools.agtools.Constants.ApiConstants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPackagesActivity extends AppCompatActivity {
    String url = ApiConstants.API.concat("allSubscriptions/1");
    ListView listView,listPresent;
    List<ModelPackage> heroList;
    ListViewAdapter adapter;

    List<ModelList> modelList;
    CustomAdapterPresent customAdapterPresent;

    JSONObject jsonObject;JSONArray jsonArray;
    Button packagebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        packagebutton= (Button) findViewById(R.id.packagebutton);
        packagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        heroList = new ArrayList<>();
        modelList = new ArrayList<>();

        loadHeroList();
    }
    private void loadHeroList()
    {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonArrayRequest instance
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        // Process the JSON
                        try{
                            Log.d("",""+response);
                                    jsonArray = response.getJSONArray("newPackages");
                                for(int j=0;j<jsonArray.length();j++)
                                {
                                     jsonObject=jsonArray.getJSONObject(j);
                                    ModelPackage hero = new ModelPackage(jsonObject.getString("packageName"));
                                    heroList.add(hero);
                                    adapter = new ListViewAdapter(heroList, getApplicationContext());
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            String schoolName = adapter.getItem(i).getPackagename();
                                            Log.d("",""+schoolName);
                                            if(schoolName.equals("Trial Package"))
                                            {
                                                try {
                                                    jsonObject=jsonArray.getJSONObject(0);
                                                    Log.d("",""+jsonObject);
                                                    JSONArray newJson=jsonObject.getJSONArray("packageDetails");
                                                    Log.d("",""+newJson);
                                                    Intent intent=new Intent(MainPackagesActivity.this,TrailPackageActivity.class);
                                                    intent.putExtra("jsonObject", newJson.toString());
                                                    intent.putExtra("PackageName",schoolName);
                                                    startActivity(intent);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                          else if(schoolName.equals("Present Package paid monthly"))
                                            {
                                            try {
                                                 jsonObject=jsonArray.getJSONObject(1);
                                                Log.d("",""+jsonObject);
                                                JSONArray newJson=jsonObject.getJSONArray("packageDetails");
                                                Log.d("",""+newJson);
                                                Intent intent=new Intent(MainPackagesActivity.this,PresentActivity.class);
                                                intent.putExtra("jsonObject", newJson.toString());
                                                intent.putExtra("PackageName",schoolName);
                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            }
                                            else if(schoolName.equals("Full Package paid monthly")){
                                            try {
                                                 jsonObject=jsonArray.getJSONObject(2);
                                                Log.d("",""+jsonObject);
                                                JSONArray newJson=jsonObject.getJSONArray("packageDetails");
                                                Log.d("",""+newJson);
                                                Intent intent=new Intent(MainPackagesActivity.this,FullActivity.class);
                                                intent.putExtra("jsonObject", newJson.toString());
                                                intent.putExtra("PackageName",schoolName);
                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            }
                                        }
                                    });
                                }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
}
