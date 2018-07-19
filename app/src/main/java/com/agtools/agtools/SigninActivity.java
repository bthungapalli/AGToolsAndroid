package com.agtools.agtools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.ErrorMessages.ValidationMessages;
import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.Model.CommudityResponse;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.agtools.agtools.DashboardActivity.ARG_IS_FROM_SPLASH;


public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_ACCESS = 100;
    EditText usernameEdit, passwordEdit;
    String usernameSting, passwordString;
    List<CommudityResponse> commudityResponseList;
    TextView forgot,signup;
    TransparentProgressDialog pd;
    private Runnable r;
    TextView langauge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferencesManager manager = SharedPreferencesManager.getInstance(this);
        langauge=findViewById(R.id.language);
        pd = new TransparentProgressDialog(this, R.drawable.sgg);
        r =new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };
       signup=(TextView)findViewById(R.id.Signup);
        SpannableString content = new SpannableString("SignUp");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signup.setText(content);
//        signup.setPaintFlags(signup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
      //  signup.setText("SignUp");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        initializeVariables();
        langauge.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                String value = "";
                if (manager.getLangauageValue().equals("es")) {
                    value = "en";
                } else {
                    value = "es";
                }
                manager.setLangaugeValue(value);
                setLocale(value);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        recreateActivityCompat(this);
    }
    public static final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
    public void initializeVariables() {
        usernameEdit = (EditText) findViewById(R.id.login_username);
        passwordEdit = (EditText) findViewById(R.id.login_password);
        forgot = (TextView) findViewById(R.id.forgot);
        usernameEdit.setSingleLine(true);
        passwordEdit.setSingleLine(true);
        forgot.setOnClickListener(this);
        passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        commudityResponseList = new ArrayList<>();
    }

    public boolean checkValidation() {
        usernameSting = usernameEdit.getText().toString();
        passwordString = passwordEdit.getText().toString();
        SharedPreferences.Editor editor2 = getSharedPreferences("passwordString", MODE_PRIVATE).edit();
        editor2.putString("passValue", passwordString);
        editor2.putString("user", usernameSting);
        editor2.apply();
        if (usernameSting.toString() != null && passwordString.toString() != null) {
            return true;
        } else {
            return false;
        }

    }

    /*public void signup(View view) {
        Intent signupActivity = new Intent(SigninActivity.this, SignUpActivity.class);
        startActivity(signupActivity);
    }*/

    public void login(View view) {

        if (checkValidation()) {
            if (InternetChecking.isInternetOn(SigninActivity.this)) {
                pd.show();
                NukeSSLCerts.NukeSSLCerts();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiConstants.Login_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        System.out.println("login response....." + response);
                        try {
                           String  sessionid = response.getString("sessionId");
                            SharedPreferences.Editor editor2 = getSharedPreferences("sessionidid", MODE_PRIVATE).edit();
                            editor2.putString("sessionid", sessionid);
                            System.out.println("session response....." + sessionid);
                            getCheckUser(sessionid);
                            System.out.println("login response....." + response);
                            JSONObject jsonObject=response.getJSONObject("user");
                            Log.d("",""+jsonObject);
                            String useremail=jsonObject.getString("userName");
                            Log.d("",""+useremail);
                            String userid=jsonObject.getString("userId");
                            Log.d("",""+userid);
                            String password=jsonObject.getString("password");
                            Log.d("",""+password);
                            SharedPreferences.Editor editor21 = getSharedPreferences("USERID", MODE_PRIVATE).edit();
                            editor21.putString("userid", userid);
                            editor21.putString("password", password);
                            editor21.commit();
                            //
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Email", useremail);
                            editor.commit();
                            String name=sharedPreferences.getString("Email","");
                            Log.d("",""+name);
                            //
                            JSONArray responseJsonArraypackages = response.getJSONObject("user").getJSONArray("userPackages");
                       String ghm=response.getJSONObject("user").getString("trailexpired");
                            Log.d("ghm","ghm"+ghm);
                            SharedPreferences sharedPreferences11 = getApplicationContext().getSharedPreferences("packex", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor11 = sharedPreferences11.edit();
                            editor11.putString("pack", ghm);
                            editor11.commit();
                            String l=sharedPreferences11.getString("pack","");
                            Log.d("name1","name1"+l);
                            if(response.getJSONObject("user").getString("trailexpired").equals("1"))
                            {
                                Intent intent=new Intent(SigninActivity.this,Main2PackagesActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent=new Intent(SigninActivity.this,MainPackagesActivity.class);
                                startActivity(intent);
                            }

                           /* if(responseJsonArraypackages.length()>0)
                           {*/
                               SPHelper.setValueToPrefs(getApplicationContext(), SPHelper.MyPREFERENCES, SPHelper.KEY_LOGIN_RESPONSE, response.toString());
                               JSONArray responseJsonArray = response.getJSONObject("user").getJSONArray("userCommodities");
                               SharedPreferences.Editor editor1 = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                               editor1.putString("key_from_json", responseJsonArray.toString());
                               editor1.commit();
                               String name1=sharedPreferences.getString("key_from_json","");
                               Log.d("",""+name1);
                               for (int i = 0; i < responseJsonArray.length(); i++) {
                                   JSONObject commudityObject = responseJsonArray.getJSONObject(i);
                                   CommudityResponse commudityResponse = new CommudityResponse(commudityObject.getString("commodityId").toString(),
                                           commudityObject.getString("commodityName").toString(),
                                           commudityObject.getString("commodityType").toString(),
                                           commudityObject.getInt("userId"),
                                           commudityObject.getString("name").toString(),
                                           commudityObject.getInt("usertypeId"),
                                           commudityObject.getInt("subusertypeId"),
                                           commudityObject.getInt("subscriptionId"),
                                           commudityObject.getString("usertype").toString(),
                                           commudityObject.getString("subusertype").toString(),
                                           commudityObject.getString("subscriptionDetails").toString(),
                                           commudityObject.getBoolean("subscriptionStatus"),
                                           commudityObject.getString("enddate").toString(),
                                           commudityObject.getInt("months"),
                                           commudityObject.getInt("packageDetailId"),
                                           commudityObject.getInt("packageId"),
                                           commudityObject.getString("packageName").toString(),
                                           commudityObject.getString("userPkgId").toString(),
                                           commudityObject.getString("id").toString(),
                                           commudityObject.getString("paypal_id").toString()
                                   );
                                   commudityResponseList.add(commudityResponse);
                                   //commudities assigning
                                   final GlobalData globalData = (GlobalData) getApplicationContext();
                                   globalData.setCommudityResponse(commudityResponseList);

                                   //Dashboard default widget selection
                                   globalData.setDashboardSelection(1);

                                   String usernameResponse = response.getJSONObject("user").get("userName").toString();
                                   System.out.println("Usernmae:" + usernameResponse);
                                   if (usernameResponse.equals(usernameSting)) {
                                       // startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
                                       Intent intent=new Intent(SigninActivity.this, DashboardActivity.class);
                                       intent.putExtra(ARG_IS_FROM_SPLASH,true);
                                       startActivity(intent);
                                   } else {
                                        CustomToast.displayMessage(SigninActivity.this,CustomToast.Signin_Invalid_Credentials);
                                   }
                               }
                           /*}
                           else {
                               Intent intent=new Intent(SigninActivity.this,MainPackagesActivity.class);
                               startActivity(intent);
                           }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"TimeOut Error",Toast.LENGTH_LONG).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            pd.dismiss();

                            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                            //usernameEdit.setText("");
                            passwordEdit.setText("");
                        } else if (error instanceof ServerError) {
                            //TODO
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(),"NetworkError",Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "ParseError ", Toast.LENGTH_LONG).show();
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();

                        String encodedCredentials = Base64.encodeToString((usernameSting + ":" + passwordString).getBytes(), Base64.NO_WRAP);
                        Log.d("",""+encodedCredentials);
                        headers.put("Authorization", "Basic " + encodedCredentials);

                        return headers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
        } else {
            CustomToast.displayMessage(SigninActivity.this, ValidationMessages.emptyfields());
        }

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

    public void permissionAccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_EXTERNAL_STORAGE
                    }, PERMISSION_ACCESS);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot:
                gotoForgotPassword();
                break;
        }
    }

    public void gotoForgotPassword() {
        Intent forgotPassIntent = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPassIntent);
    }
}
