package com.agtools.agtools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.ApiConstants;
import com.agtools.agtools.Constants.InternetChecking;
import com.agtools.agtools.ServiceProvider.Apicalls;
import com.agtools.agtools.custommessages.CustomToast;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by soulsticesoftware on 03-03-2018.
 */

public class ForgotPasswordActivity extends Activity implements View.OnClickListener {
    TextView textLogin, textRegister;
    Button forgotpassword;
    EditText forgot_mail;
    String emailText;

    public void onCreate(Bundle savedInstanseState) {
        super.onCreate(savedInstanseState);
        setContentView(R.layout.forgotpassword);
        forgotPassInitialization();
    }

    public void forgotPassInitialization() {
        textLogin = (TextView) findViewById(R.id.textLogin);
        textRegister = (TextView) findViewById(R.id.textRegister);
        forgotpassword = (Button) findViewById(R.id.forgotpassword);
        forgot_mail = (EditText) findViewById(R.id.forgot_mail);
        textLogin.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);
        textRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textLogin:
                gotoLogin();
                break;
            case R.id.textRegister:
                gotoRegister();
                break;
            case R.id.forgotpassword:
                calForgotService();
        }

    }

    public void gotoLogin() {
        Intent loginIntent = new Intent(ForgotPasswordActivity.this, SigninActivity.class);
        startActivity(loginIntent);
    }

    public void gotoRegister() {
        Intent registerIntent = new Intent(ForgotPasswordActivity.this, SignUpActivity.class);
        startActivity(registerIntent);
    }

    public void calForgotService() {
        emailText = forgot_mail.getText().toString().trim();
        if (InternetChecking.isInternetOn(ForgotPasswordActivity.this)) {
            System.out.println("forgot password url.." + ApiConstants.ForgotPassword_URL);
            NukeSSLCerts.NukeSSLCerts();
            JSONObject response = Apicalls.sendRequest(ForgotPasswordActivity.this, ApiConstants.ForgotPassword_URL, CustomToast.Signin_LoadingMessage, new JSONObject(getForgotParams()), new Apicalls.VolleyCallback() {
                @Override
                public void getResponse(JSONObject response) {
                    System.out.println("forgot responseeee....." + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String forgotResp = jsonObject.getString("status");
                        System.out.println("forgot resp....." + forgotResp);
                        if (forgotResp.equals("success")) {
                           // gotoLogin();
                            Toast.makeText(ForgotPasswordActivity.this, "Mail Sent SucessFully", Toast.LENGTH_LONG).show();
                        } else if (forgotResp.equals("You are not Registred User")) {
                            Toast.makeText(ForgotPasswordActivity.this, forgotResp, Toast.LENGTH_LONG).show();
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void getError(VolleyError error) {
                    System.out.print("Error Response:" + error);
                    CustomToast.displayMessage(ForgotPasswordActivity.this, CustomToast.Signin_Invalid_Credentials);
                }
            });

        }

    }

    public Map<String, String> getForgotParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", emailText);
        System.out.println("paramsssss" + params);
        return params;

    }

}
