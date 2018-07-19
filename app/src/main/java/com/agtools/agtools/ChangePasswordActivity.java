package com.agtools.agtools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by soulsticesoftware on 27-02-2018.
 */

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    EditText editchange_password, changepass_confirmpassword;
    Button changePassword;
    String changePassData, confirmPassData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        changePasswordInitialiation();
    }

    public void changePasswordInitialiation() {
        changepass_confirmpassword = (EditText) findViewById(R.id.changepass_confirmpassword);
        editchange_password = (EditText) findViewById(R.id.editchange_password);
        changePassword = (Button) findViewById(R.id.changePassword);
        changePassword.setOnClickListener(ChangePasswordActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePassword:
                changePasswordService();
                break;
        }
    }

    public void changePasswordService() {
        changePassData = editchange_password.getText().toString().trim();
        confirmPassData = changepass_confirmpassword.getText().toString().trim();
        if (changePassData.equals(confirmPassData)) {
            callChangePasswordService();
        } else {
            Toast.makeText(ChangePasswordActivity.this, "password and confirm password should be same", Toast.LENGTH_LONG).show();
        }
    }

    public void callChangePasswordService() {
        if (InternetChecking.isInternetOn(ChangePasswordActivity.this)) {
            /*JSONObject response = Apicalls.sendRequest(ChangePasswordActivity.this, ApiConstants.ChangePassword_URL, CustomToast.Signin_LoadingMessage, new JSONObject(getChangeParams()), new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("changepass responseeee....." + response);
                }

                @Override
                public void getError(VolleyError error) {
                    System.out.print("Error Response:" + error);
                    CustomToast.displayMessage(ChangePasswordActivity.this, CustomToast.Error_Response);
                }
            });*/
            /*RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiConstants.ChangePassword_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("",""+response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("",""+error.toString());
                }
            }){
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("password", confirmPassData);
                    return params;

                }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;

                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);*/
          /*  JSONObject input1 = new JSONObject();
            try {
                input1.put("password", changePassData);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            Map<String, String> params = new HashMap<String, String>();
            params.put("password", confirmPassData);
            NukeSSLCerts.NukeSSLCerts();
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, ApiConstants.ChangePassword_URL, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                            Log.d("123", "response:" + response.toString());
                            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                            startActivity(intent);
                        }
                    }

                    , new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("123", "Error: " + error.getMessage());

                    Toast.makeText(ChangePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);


        }

    /*public Map<String, String> getChangeParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", confirmPassData);
        return params;

    }*/
    }
}
