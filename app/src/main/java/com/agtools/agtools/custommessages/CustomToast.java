package com.agtools.agtools.custommessages;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by HOME on 12/23/2017.
 */
public class CustomToast {

    public static String Signin_LoadingMessage="Please wait";
    public static String Signin_Invalid_Credentials="Invalid Credentials";
    public static String Error_Response = "Error Response";
    public static String Select_Commudity="Please select commudity type";

    public static void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
