package com.agtools.agtools;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.agtools.agtools.Constants.SPHelper.MyPREFERENCES;

/**
 * Created by soulsticesoftware on 26-02-2018.
 */

public class ProfileActivity extends Activity implements View.OnClickListener {
    Button profileUpdate;
    String path;
    ImageView changepassword, profilemail, imageProfilePic,settingback;
    String loginResp, name, userEmail, userCountry, userCity, userState, userImagePath;
    EditText EditProfileMail, profileName;
    Spinner spinnercountry, spinnerstate, spinnercities;
    ArrayList<String> countrylist, statelist, citylist;
    ArrayAdapter countrySpinnerAdapter, statesAdapter, cityAdpater;
    ImageView imageView;
    private static final int SELECT_PICTURE = 100;
    String password,username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SharedPreferences sharedPreferences1  = getSharedPreferences("passwordString", MODE_PRIVATE);
        password= sharedPreferences1.getString("passValue", "");
        username= sharedPreferences1.getString("user", "");

        profileInitialization();
    }

    public void profileInitialization() {
        imageView= (ImageView) findViewById(R.id.upload);
        imageProfilePic = (ImageView) findViewById(R.id.imageProfilePic);
        profilemail = (ImageView) findViewById(R.id.profilemail);
        profileUpdate = (Button) findViewById(R.id.profileUpdate);
        changepassword = (ImageView) findViewById(R.id.changepassword);
        EditProfileMail = (EditText) findViewById(R.id.EditProfileMail);
        profileName = (EditText) findViewById(R.id.profileName);
        spinnercountry = (Spinner) findViewById(R.id.spinnercountry);
        spinnerstate = (Spinner) findViewById(R.id.spinnerstate);
        spinnercities = (Spinner) findViewById(R.id.spinnercity);
        settingback = (ImageView)findViewById(R.id.settingback);
        loginResp = SPHelper.getFromSP(getApplicationContext(), MyPREFERENCES, SPHelper.KEY_LOGIN_RESPONSE);
        try {
            JSONObject jsonObject = new JSONObject(loginResp.toString());
            JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
            System.out.println("Login responseee in profile..." + jsonObjectUser);
            name = jsonObjectUser.getString("name");
            userEmail = jsonObjectUser.getString("userName");
            userCountry = jsonObjectUser.getString("country");
            userState = jsonObjectUser.getString("state");
            userCity = jsonObjectUser.getString("city");
            userImagePath = jsonObjectUser.getString("userImagePath");
            EditProfileMail.setText(userEmail);
            profileName.setText(name);
            byte[] imageAsBytes = Base64.decode(userImagePath.getBytes(), Base64.DEFAULT);
            imageProfilePic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            countrylist=new ArrayList<>();
            countrylist.add(userCountry);
            if(userCountry.equals("US"))
            {
                countrylist.add("Mexico");
            }
            else if(userCountry.equals("Mexico"))
            {
                countrylist.add("US");
            }
            countrySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countrylist);
            countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnercountry.setAdapter(countrySpinnerAdapter);

            spinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    userCountry = spinnercountry.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //state
            statelist = new ArrayList<>();
            statelist.add(userState);
            statesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, statelist);
            statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerstate.setAdapter(statesAdapter);

            String newStatesUrl = ApiConstants.States_URL + userCountry;
            System.out.println("neww states url....." + newStatesUrl);
            if (InternetChecking.isInternetOn(ProfileActivity.this)) {
                JSONArray response = Apicalls.getWeatherRequest(ProfileActivity.this, newStatesUrl, CustomToast.Signin_LoadingMessage, new Apicalls.VolleyWatherCallBack() {
                    @Override
                    public void getResponse(JSONArray response) {
                        System.out.println("response for shipping price....:" + response);
                        try {
                            statelist.clear();
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String cityName = jsonArray.get(i).toString();
                                statelist.add(cityName);
                            }
                            statesAdapter = new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_item, statelist);
                            statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerstate.setAdapter(statesAdapter);
                            spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    userState = statelist.get(position).toString();
                                    getCities(statelist.get(position).toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void getError(VolleyError error) {
                        System.out.print("Error Response:" + error);
                        CustomToast.displayMessage(ProfileActivity.this, CustomToast.Error_Response);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileUpdate.setText("Edit");
        profileUpdate.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        settingback.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                path=selectedImageUri.getPath();
                if (null != path) {
                    // Get the path from the Uri
                    // path = getPathFromURI(selectedImageUri);
                    Log.i("Tag", "Image Path : " + path);
                    // Set the image in ImageView
                    ((ImageView) findViewById(R.id.imageProfilePic)).setImageURI(selectedImageUri);

                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileUpdate:
                updateProfile(userEmail,name,userCountry,userState,userCity);
                break;


        }
    }

    /*   *//* Get the real path from the URI *//*
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/


    public void getCities(String state) {
        if (InternetChecking.isInternetOn(ProfileActivity.this)) {
            String newCityUrl = ApiConstants.Cities_URL + state;
            JSONArray response = Apicalls.getWeatherRequest(ProfileActivity.this, newCityUrl, CustomToast.Signin_LoadingMessage, new Apicalls.VolleyWatherCallBack() {
                @Override
                public void getResponse(JSONArray response) {
                    citylist=new ArrayList<>();
                    citylist.clear();
                    System.out.println("response for shipping price....:" + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String cityName = jsonArray.get(i).toString();
                            citylist.add(cityName);
                        }
                        cityAdpater = new ArrayAdapter<String>(ProfileActivity.this, R.layout.spinner_item, citylist);
                        cityAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnercities.setAdapter(cityAdpater);
                        spinnercities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                userCity = citylist.get(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void getError(VolleyError error) {
                    System.out.print("Error Response:" + error);
                    CustomToast.displayMessage(ProfileActivity.this, CustomToast.Error_Response);
                }
            });
        }

    }
    public void updateProfile(String userEmail,String name,String userCountry,String userState,String userCity) {
        /*updateProfile123();
        //profileUpdate.setText("Update");*/
        //File uploadFile2 = new File("C:/Users/ranjithkumar.g/Pictures/ranga.png");
        NukeSSLCerts.NukeSSLCerts();
        String charset = "UTF-8";
        try {
            MultipartUtility multipart = new MultipartUtility("https://my.ag.tools/agtools/updateProfile",charset);

            multipart.addHeaderField("Content-Type", "multipart/form-data");
            String encodedCredentials = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
            Log.d("",""+encodedCredentials);
            //headers.put("Authorization", "Basic " + encodedCredentials);
            multipart.addHeaderField("Authorization", "Basic "+"Basic " + encodedCredentials);


            //  multipart.addFilePart("uploadImage", uploadFile2);
            multipart.addFormField("name", name);
            multipart.addFormField("city", userCity);
            multipart.addFormField("state", userState);
            multipart.addFormField("country", userCountry);
            multipart.addFormField("zipcode", "1234");

            String response = multipart.finish();
            Log.d("res","res"+response);

            System.out.println("SERVER REPLIED:");

        } catch (IOException ex) {
            System.err.println(ex);
        }



    }
}
