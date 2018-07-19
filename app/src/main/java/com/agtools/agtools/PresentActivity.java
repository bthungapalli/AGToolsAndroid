package com.agtools.agtools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

interface BtnClickListener {
    public abstract void onBtnClick(View view);
}

public class PresentActivity extends AppCompatActivity {
    ListView listView;
    JSONObject jsonObject;
    List<ModelList> modelList;
    CustomAdapterPresent customAdapterPresent;
    String dollor="$";String amt="00";String val=".";
    String user="COMMODITY PER USER";
    ModelList model;
    Button fullPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);
        listView = (ListView) findViewById(R.id.listView);
        fullPackages= (Button) findViewById(R.id.fullpackages);
        fullPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        modelList = new ArrayList<>();
        model=new ModelList();
        final Intent intent = getIntent();
        String jsonString = intent.getStringExtra("jsonObject");
        Log.d("",""+jsonString);
        final String packagename=intent.getStringExtra("PackageName");
        try {
            JSONArray jsonArray=new JSONArray(jsonString);
            for(int j=0;j<jsonArray.length();j++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(j);
                ModelList hero = new ModelList(dollor.concat(jsonObject.getString("price")).concat(val).concat(amt),jsonObject.getString("commodityCount").concat(user));
                modelList.add(hero);
            }
            customAdapterPresent = new CustomAdapterPresent(modelList, getApplicationContext(), new BtnClickListener() {
                @Override
                public void onBtnClick(View view) {
                    int position = (Integer) view.getTag();
                    Log.d("", "" + position);
                    model = modelList.get(position);
                    Log.d("",""+model);
                    String com=model.getCommodityCount();
                    Log.d("",""+com);
                    if((view.getId()==R.id.selectplan) &&(model.getCommodityCount().equals("1COMMODITY PER USER")))
                    {
                        Intent intent1=new Intent(PresentActivity.this,UserTypeActivity.class);
                        String price=model.getPrice();
                        String commodityCount=model.getCommodityCount();
                        intent1.putExtra("price",price);
                        intent1.putExtra("commodityCount",commodityCount);
                        intent1.putExtra("PackageName",packagename);
                        Log.d("", "" + price);
                        Log.d("", "" + commodityCount);
                        startActivity(intent1);
                    }
                    else if((view.getId()==R.id.selectplan) && (model.getCommodityCount().equals("2COMMODITY PER USER")))
                    {
                        Intent intent1=new Intent(PresentActivity.this,UserTypeTwoActivity.class);
                        String price=model.getPrice();
                        String commodityCount=model.getCommodityCount();
                        intent1.putExtra("price",price);
                        intent1.putExtra("commodityCount",commodityCount);
                        intent1.putExtra("PackageName",packagename);
                        Log.d("", "" + price);
                        Log.d("", "" + commodityCount);
                        startActivity(intent1);
                    }
                    else if(view.getId()==R.id.selectplan && model.getCommodityCount().equals("3COMMODITY PER USER"))
                    {
                        Intent intent1=new Intent(PresentActivity.this,UserTypeThreeActivity.class);
                        String price=model.getPrice();
                        String commodityCount=model.getCommodityCount();
                        intent1.putExtra("price",price);
                        intent1.putExtra("commodityCount",commodityCount);
                        intent1.putExtra("PackageName",packagename);
                        Log.d("", "" + price);
                        Log.d("", "" + commodityCount);
                        startActivity(intent1);
                    }
                    else if((view.getId()==R.id.selectplan) && (model.getCommodityCount().equals("4COMMODITY PER USER")))
                    {
                        Intent intent1=new Intent(PresentActivity.this,UserTypeFourActivity.class);
                        String price=model.getPrice();
                        String commodityCount=model.getCommodityCount();
                        intent1.putExtra("price",price);
                        intent1.putExtra("commodityCount",commodityCount);
                        intent1.putExtra("PackageName",packagename);
                        Log.d("", "" + price);
                        Log.d("", "" + commodityCount);
                        startActivity(intent1);
                    }
                    else if((view.getId()==R.id.selectplan) && (model.getCommodityCount().equals("5COMMODITY PER USER")))
                    {
                        Intent intent1=new Intent(PresentActivity.this,UserTypeFiveActivity.class);
                        String price=model.getPrice();
                        String commodityCount=model.getCommodityCount();
                        intent1.putExtra("price",price);
                        intent1.putExtra("commodityCount",commodityCount);
                        intent1.putExtra("PackageName",packagename);
                        Log.d("", "" + price);
                        Log.d("", "" + commodityCount);
                        startActivity(intent1);
                    }
                }
            });
            listView.setAdapter(customAdapterPresent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
