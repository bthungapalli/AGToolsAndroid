package com.agtools.agtools;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class UserTypeThreeActivity extends AppCompatActivity {
    private static final String TAG = UserTypeActivity.class.getSimpleName();
    String url = ApiConstants.API.concat("allSubscriptions/1");
    String price,commodityCount;
    Spinner spinner,type,type2,type3;
    Button next;
    String itemuserType,itemtype,itemtype2,itemtype3,fruititem,fruititem1,fruititem2;
    ArrayList<String> countryList = new ArrayList<String>();
    ArrayList<String> typeList = new ArrayList<String>();
    ArrayList<String> fruitList = new ArrayList<String>();
    ArrayList<String> vegList = new ArrayList<String>();
    ArrayList<String> nutList = new ArrayList<String>();
    ArrayList<String> herbsList = new ArrayList<String>();
    ArrayList<String> ornamentalList = new ArrayList<String>();
    String[] countryspinnerArray,typespinnerArray;
    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    CustomSpinnerAdapter customSpinnerAdapter;
    JSONObject obj;
    Spinner fruitSpinner,vegtablespinner,nutspinner;
    Button user3;
    String commoditytype,commodutytype1,commoditytype2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_three);
        price = getIntent().getStringExtra("price");
        commodityCount = getIntent().getStringExtra("commodityCount");
        final String packagename=getIntent().getStringExtra("PackageName");
        Log.d("", "" + price);
        Log.d("", "" + commodityCount);
        Log.d("", "" + packagename);
        user3= (Button) findViewById(R.id.user3);
        user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        spinner= (Spinner) findViewById(R.id.spinner);
        type= (Spinner) findViewById(R.id.type);
        type2= (Spinner) findViewById(R.id.type2);
        type3= (Spinner) findViewById(R.id.type3);
        fruitSpinner= (Spinner) findViewById(R.id.fruititem1);
        vegtablespinner= (Spinner) findViewById(R.id.fruititem2);
        nutspinner= (Spinner) findViewById(R.id.fruititem3);
        next= (Button) findViewById(R.id.next);
        fruitList.add("SelectCommodity");
        vegList.add("SelectCommodity");
        herbsList.add("SelectCommodity");
        nutList.add("SelectCommodity");
        ornamentalList.add("SelectCommodity");
        countryList.add("SelectType");
        countryspinnerArray = new String[1];
        spinnerMap.put(0, "0");
        countryspinnerArray[0] = "SelectType";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest movieReq  = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());
                countryspinnerArray = new String[response.length() + 1];
                spinnerMap.put(0, "0");
                countryspinnerArray[0] = "SelectType";
                try {
                    JSONArray jsonArray=response.getJSONArray("subUserType");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            countryList.add(obj.getString("subusername"));
                            customSpinnerAdapter=new CustomSpinnerAdapter(UserTypeThreeActivity.this,countryList);
                            spinner.setAdapter(customSpinnerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customSpinnerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });
        requestQueue.add(movieReq);
        final CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(UserTypeThreeActivity.this,countryList);
        spinner.setAdapter(customSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemuserType = parent.getItemAtPosition(position).toString();
                Log.d("",""+itemuserType);
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //select types
        NukeSSLCerts.NukeSSLCerts();
        JsonObjectRequest movieReq1  = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());
                try {
                    JSONArray jsonArray=response.getJSONArray("finalcommodities");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            obj = jsonArray.getJSONObject(i);
                            typeList.add(obj.getString("type"));
                            Log.d("",""+typeList);
                            if(obj.getString("type").equals("fruits"))
                            {
                                fruitList.add(obj.getString("fruit_name"));
                                Log.d("fruit","fruit"+fruitList);
                            }
                            else if(obj.getString("type").equals("vegetables"))
                            {
                                vegList.add(obj.getString("fruit_name"));
                                Log.d("veg","veg"+vegList);
                            }
                            else if(obj.getString("type").equals("nuts"))
                            {
                                nutList.add(obj.getString("fruit_name"));
                                Log.d("nut","nut"+nutList);
                            }
                            else if(obj.getString("type").equals("herbs"))
                            {
                                herbsList.add(obj.getString("fruit_name"));
                                Log.d("herb","herb"+herbsList);
                            }
                            else if(obj.getString("type").equals("ornamentals"))
                            {
                                ornamentalList.add(obj.getString("fruit_name"));
                                Log.d("orn","orn"+ornamentalList);
                            }

                            HashSet<String> set = new HashSet<String>(typeList);
                            ArrayList<String> listWithoutDuplicateElements = new ArrayList<String>(set);
                            Collections.sort(listWithoutDuplicateElements);
                            CustomSpinnerAdapter   customSpinnerAdapter=new CustomSpinnerAdapter(UserTypeThreeActivity.this,listWithoutDuplicateElements);
                            type.setAdapter(customSpinnerAdapter);
                            type2.setAdapter(customSpinnerAdapter);
                            type3.setAdapter(customSpinnerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customSpinnerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        requestQueue.add(movieReq1);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemtype = parent.getItemAtPosition(position).toString();
                Log.d("gg","ggg"+itemtype);
                //fruitSpinner.setVisibility(View.VISIBLE);
                if(itemtype.equals("fruits"))
                {
                    //selection commodity
                    fruitList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, fruitList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }
                else if(itemtype.equals("vegetables"))
                {
                    //selection commodity
                    vegList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, vegList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }
                else if(itemtype.equals("nuts"))
                {
                    //selection commodity
                    nutList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, nutList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }
                else if(itemtype.equals("herbs"))
                {
                    //selection commodity
                    herbsList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, herbsList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }

                else if(itemtype.equals("ornamentals"))
                {
                    //selection commodity
                    ornamentalList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ornamentalList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemtype2 = parent.getItemAtPosition(position).toString();
                Log.d("gg","ggg"+itemtype2);
                //fruitSpinner.setVisibility(View.VISIBLE);
                if(itemtype2.equals("fruits"))
                {
                    //selection commodity
                    fruitList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, fruitList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    vegtablespinner.setAdapter(stateAdapter);
                }
                else if(itemtype2.equals("vegetables"))
                {
                    //selection commodity
                    vegList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, vegList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    fruitSpinner.setAdapter(stateAdapter);
                }
                else if(itemtype2.equals("nuts"))
                {
                    //selection commodity
                    nutList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, nutList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    vegtablespinner.setAdapter(stateAdapter);
                }
                else if(itemtype2.equals("herbs"))
                {
                    //selection commodity
                    herbsList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, herbsList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    vegtablespinner.setAdapter(stateAdapter);
                }

                else if(itemtype2.equals("ornamentals"))
                {
                    //selection commodity
                    ornamentalList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ornamentalList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    vegtablespinner.setAdapter(stateAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemtype3 = parent.getItemAtPosition(position).toString();
                Log.d("gg","ggg"+itemtype3);
                //fruitSpinner.setVisibility(View.VISIBLE);
                if(itemtype3.equals("fruits"))
                {
                    //selection commodity
                    fruitList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, fruitList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    nutspinner.setAdapter(stateAdapter);
                }
                else if(itemtype3.equals("vegetables"))
                {
                    //selection commodity
                    vegList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, vegList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    nutspinner.setAdapter(stateAdapter);
                }
                else if(itemtype3.equals("nuts"))
                {
                    //selection commodity
                    nutList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, nutList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    nutspinner.setAdapter(stateAdapter);
                }
                else if(itemtype3.equals("herbs"))
                {
                    //selection commodity
                    herbsList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, herbsList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    nutspinner.setAdapter(stateAdapter);
                }

                else if(itemtype3.equals("ornamentals"))
                {
                    //selection commodity
                    ornamentalList.add("SelectCommodity");
                    ArrayAdapter stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ornamentalList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.WHITE);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    nutspinner.setAdapter(stateAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fruitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fruititem = adapterView.getItemAtPosition(i).toString();
                Log.d("gg","ggg"+fruititem);
                if(fruitList.contains(fruititem))
                {
                    commoditytype="1";
                }
                else if(vegList.contains(fruititem))
                {
                    commoditytype="2";
                }
                else if(herbsList.contains(fruititem))
                {
                    commoditytype="3";
                }
                else if(nutList.contains(fruititem))
                {
                    commoditytype="4";
                }
                else if(ornamentalList.contains(fruititem))
                {
                    commoditytype="5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        vegtablespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fruititem1 = adapterView.getItemAtPosition(i).toString();
                Log.d("gg","ggg"+fruititem1);
                if(fruitList.contains(fruititem1))
                {
                    commodutytype1="1";
                }
                else if(vegList.contains(fruititem1))
                {
                    commodutytype1="2";
                }
                else if(herbsList.contains(fruititem1))
                {
                    commodutytype1="3";
                }
                else if(nutList.contains(fruititem1))
                {
                    commodutytype1="4";
                }
                else if(ornamentalList.contains(fruititem1))
                {
                    commodutytype1="5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nutspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fruititem2 = adapterView.getItemAtPosition(i).toString();
                Log.d("gg","ggg"+fruititem2);
                if(fruitList.contains(fruititem2))
                {
                    commoditytype2="1";
                }
                else if(vegList.contains(fruititem2))
                {
                    commoditytype2="2";
                }
                else if(herbsList.contains(fruititem2))
                {
                    commoditytype2="3";
                }
                else if(nutList.contains(fruititem2))
                {
                    commoditytype2="4";
                }
                else if(ornamentalList.contains(fruititem2))
                {
                    commoditytype2="5";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem().toString().trim().equals("SelectType")) {
                    Toast.makeText(UserTypeThreeActivity.this, "Please Select SelectType", Toast.LENGTH_SHORT).show();
                }
                else  if (fruitSpinner.getSelectedItem().toString().trim().equals("SelectCommodity")) {
                    Toast.makeText(UserTypeThreeActivity.this, "Please Select SelectCommodity", Toast.LENGTH_SHORT).show();
                }
                else if (vegtablespinner.getSelectedItem().toString().trim().equals("SelectCommodity")) {
                    Toast.makeText(UserTypeThreeActivity.this, "Please Select SelectCommodity", Toast.LENGTH_SHORT).show();
                }
                else if (nutspinner.getSelectedItem().toString().trim().equals("SelectCommodity")) {
                    Toast.makeText(UserTypeThreeActivity.this, "Please Select SelectCommodity", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(UserTypeThreeActivity.this, DetailActivityThree.class);
                    intent.putExtra("package", packagename);
                    intent.putExtra("price", price);
                    intent.putExtra("usertype", itemuserType);
                    intent.putExtra("fruititem", fruititem);
                    intent.putExtra("fruititem1", fruititem1);
                    intent.putExtra("fruititem2", fruititem2);
                    intent.putExtra("commodityCount", commodityCount);
                    intent.putExtra("commoditytype",commoditytype);
                    intent.putExtra("commodutytype1",commodutytype1);
                    intent.putExtra("commodutytype2",commoditytype2);
                    startActivity(intent);
                }
            }
        });
    }
}
