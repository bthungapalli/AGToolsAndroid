package com.agtools.agtools;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Adapter.ShippingAdapter;
import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.Model.CommudityResponse;
import com.agtools.agtools.Model.ShippingResponse;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView textssignin, textViewValidEmail;
    EditText signupMail, signupRePassword, signupPassword, signupName;
    Button signup;
    String userMail, userType, userPassword, userRetypePass, userName, countryName, stateName, cityName;
    Spinner spinnercountry, spinnerstate, spinnercities,signupuserType;
    ArrayAdapter countryAdapter, statesAdapter, citiesAdapter,usertypeadpater;
    List<String> citiesList, statesList;
    String[] country = {"Select Country","US", "Mexico",};
    String [] usertypes={"Select UserType","GROWER","BUYER","TRUCKER","GOVERNMENT AGENT"};
    String EMAIL_PATTERN = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

    String usertypeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signUpInitialization();
    }

    public void signUpInitialization() {
        citiesList = new ArrayList<>();
        statesList = new ArrayList<>();
        statesList.add("Select State");
        citiesList.add("Select City");
        textssignin = (TextView) findViewById(R.id.textssignin);
        textViewValidEmail = (TextView) findViewById(R.id.textViewValidEmail);
        textViewValidEmail.setVisibility(View.GONE);
        signupMail = (EditText) findViewById(R.id.signupMail);
        signupuserType = (Spinner) findViewById(R.id.signupuserType);
        signupRePassword = (EditText) findViewById(R.id.signupRePassword);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        signupName = (EditText) findViewById(R.id.signupName);
        spinnercountry = (Spinner) findViewById(R.id.spinnercountry);
        spinnerstate = (Spinner) findViewById(R.id.spinnerstate);
        spinnercities = (Spinner) findViewById(R.id.spinnercities);
        signupMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userMail = signupMail.getText().toString().trim();
                System.out.println("userrr mailll:" + userMail);
                if (userMail == "" || userMail.equals("")) {
                    textViewValidEmail.setVisibility(View.GONE);
                }
                if (!userMail.matches(EMAIL_PATTERN)) {
                    textViewValidEmail.setVisibility(View.VISIBLE);
                } else {
                    textViewValidEmail.setVisibility(View.GONE);
                }

            }
        });
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(SignUpActivity.this);
        spinnercountry.setOnItemSelectedListener(this);
        textssignin.setOnClickListener(SignUpActivity.this);
        countryAdapter = new ArrayAdapter(SignUpActivity.this, R.layout.spinner_item, country);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercountry.setAdapter(countryAdapter);
        //Usertype
        usertypeadpater = new ArrayAdapter(SignUpActivity.this, R.layout.spinner_item, usertypes);
        usertypeadpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupuserType.setAdapter(usertypeadpater);
        signupuserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usertypeItem=(String) adapterView.getItemAtPosition(i);;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getInputData() {
        userMail = signupMail.getText().toString().trim();
        userType = signupuserType.getSelectedItem().toString().trim();
        userPassword = signupPassword.getText().toString().trim();
        userRetypePass = signupRePassword.getText().toString().trim();
        userName = signupName.getText().toString().trim();
        doSignup();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textssignin:
                gotoSignIn();
                break;
            case R.id.signup:
                getInputData();
                break;
        }
    }

    public void gotoSignIn() {
        Intent signinIntent = new Intent(SignUpActivity.this, SigninActivity.class);
        startActivity(signinIntent);
    }

    public void doSignup() {
        if ("".equals(userMail) || userMail == null || "".equals(userPassword) || userPassword == null || "".equals(userRetypePass) || userRetypePass == null || "".equals(userName) || userName == null || "".equals(userType) || userType == null) {
            Toast.makeText(SignUpActivity.this, "Fields Should Not be Empty", Toast.LENGTH_LONG).show();
        } else if (!userMail.matches(EMAIL_PATTERN)) {
            Toast.makeText(SignUpActivity.this, "please enter valid mail", Toast.LENGTH_LONG).show();
        } else if (!userPassword.equals(userRetypePass)) {
            Toast.makeText(SignUpActivity.this, "Password and Retype Password should be same", Toast.LENGTH_LONG).show();
        } else if(userType.equals("Select UserType"))
        {
            Toast.makeText(SignUpActivity.this, "Please Select UserType", Toast.LENGTH_LONG).show();
        }
        else {
            emailValidation();
        }
    }

    public void gotoSignUp() {
        if (InternetChecking.isInternetOn(SignUpActivity.this)) {
            NukeSSLCerts.NukeSSLCerts();
            JSONObject response = Apicalls.sendRequest(SignUpActivity.this, ApiConstants.Signup_URL, CustomToast.Signin_LoadingMessage, new JSONObject(getSignupParams()), new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("signup responseeee....." + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String registrationSuccess = jsonObject.getString("success");
                        Toast.makeText(SignUpActivity.this, registrationSuccess, Toast.LENGTH_LONG).show();
                        gotoSignIn();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void getError(VolleyError error) {
                    System.out.print("Error Response:" + error);
                    CustomToast.displayMessage(SignUpActivity.this, CustomToast.Signin_Invalid_Credentials);
                }
            });

        }

    }

    public Map<String, String> getSignupParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", userMail);
        params.put("password", userPassword);
        params.put("city", cityName);
        params.put("state", stateName);
        params.put("country", countryName);
        params.put("zipcode", "94536");
        params.put("usertype", userType);
        params.put("usertypeid", "3");
        params.put("name", userName);
        System.out.println("paramsssss" + params);
        return params;

    }

    public void emailValidation() {
        if (InternetChecking.isInternetOn(SignUpActivity.this)) {
            String emailValidation_url = ApiConstants.Email_verification_URL + userMail;
            System.out.println("email validation urlllll:" + emailValidation_url);
            JSONArray response = Apicalls.getWeatherRequest(SignUpActivity.this, emailValidation_url, CustomToast.Signin_LoadingMessage, new Apicalls.VolleyWatherCallBack() {
                @Override
                public void getResponse(JSONArray response) {
                    System.out.println("response for emailverification....:" + response);
                    if (response.length() > 0) {
                        try {
                            String emailResp = response.getString(0);
                            System.out.println("First: " + emailResp);
                            if (emailResp.equals("alreadyExist")) {
                                Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                            } else {
                                gotoSignUp();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void getError(VolleyError error) {
                    System.out.print("Error Response:" + error);
                    CustomToast.displayMessage(SignUpActivity.this, CustomToast.Error_Response);
                }
            });
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        countryName = country[position].toString();
        getStates(country[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getStates(String country) {
        String newStatesUrl = ApiConstants.States_URL + country;
        System.out.println("neww states url....." + newStatesUrl);
        if (InternetChecking.isInternetOn(SignUpActivity.this)) {
            JSONArray response = Apicalls.getWeatherRequest(SignUpActivity.this, newStatesUrl, CustomToast.Signin_LoadingMessage, new Apicalls.VolleyWatherCallBack() {
                @Override
                public void getResponse(JSONArray response) {
                    System.out.println("response for shipping price....:" + response);
                    try {
                        //statesList.clear();
                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String cityName = jsonArray.get(i).toString();
                            //statesList.add("Select State");

                            statesList.add(cityName);
                        }
                        statesAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.spinner_item, statesList);
                        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerstate.setAdapter(statesAdapter);
                        spinnerstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                stateName = statesList.get(position).toString();
                                getCities(statesList.get(position).toString());
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
                    CustomToast.displayMessage(SignUpActivity.this, CustomToast.Error_Response);
                }
            });
        }
    }

    public void getCities(String state) {
        if (InternetChecking.isInternetOn(SignUpActivity.this)) {
            String newCityUrl = ApiConstants.Cities_URL + state;
            JSONArray response = Apicalls.getWeatherRequest(SignUpActivity.this, newCityUrl, CustomToast.Signin_LoadingMessage, new Apicalls.VolleyWatherCallBack() {
                @Override
                public void getResponse(JSONArray response) {
                    //citiesList.clear();
                    System.out.println("response for shipping price....:" + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String cityName = jsonArray.get(i).toString();
                            //citiesList.add("Select City");
                            citiesList.add(cityName);
                        }
                        citiesAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.spinner_item, citiesList);
                        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnercities.setAdapter(citiesAdapter);
                        spinnercities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cityName = citiesList.get(position).toString();
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
                    CustomToast.displayMessage(SignUpActivity.this, CustomToast.Error_Response);
                }
            });
        }

    }
}
