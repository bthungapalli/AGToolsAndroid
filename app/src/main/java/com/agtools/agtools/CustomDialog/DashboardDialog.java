package com.agtools.agtools.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.agtools.agtools.GlobalResponse.GlobalData;
import com.agtools.agtools.R;

/**
 * Created by HOME on 1/18/2018.
 */
public class DashboardDialog implements View.OnClickListener {
    Dialog dashboardDialog;
    ImageButton weather_button, nationalweather_button, liveweather_button,
            volumereports_button, terminal_button, shipping_button, exchange_button,
            holiday_button, freeze_button, juice_button, customer_button, diesel_button, gas_button;
    GlobalData globalData;

    public void ViewDashBoard(Activity dashboardActivity) {
        globalData = (GlobalData) dashboardActivity.getApplicationContext();
        dashboardDialog = new Dialog(dashboardActivity);
        dashboardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dashboardDialog.setContentView(R.layout.dashboard);

        weather_button = (ImageButton) dashboardDialog.findViewById(R.id.weather_imageButton);
        volumereports_button = (ImageButton) dashboardDialog.findViewById(R.id.viewreports_imageButton);
        shipping_button = (ImageButton) dashboardDialog.findViewById(R.id.shipping_imageButton);
        nationalweather_button = (ImageButton) dashboardDialog.findViewById(R.id.nationalweather_imageButton);
        exchange_button = (ImageButton) dashboardDialog.findViewById(R.id.exchangerate_imageButton);


        weather_button.setOnClickListener(this);
        volumereports_button.setOnClickListener(this);
        shipping_button.setOnClickListener(this);
        nationalweather_button.setOnClickListener(this);
        exchange_button.setOnClickListener(this);

        dashboardDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather_imageButton:
                globalData.setDashboardSelection(1);
                dashboardDialog.dismiss();
                break;
            case R.id.viewreports_imageButton:
                globalData.setDashboardSelection(2);
                dashboardDialog.dismiss();
                break;
            case R.id.shipping_imageButton:
                globalData.setDashboardSelection(3);
                dashboardDialog.dismiss();
                break;
            case R.id.exchangerate_imageButton:
                globalData.setDashboardSelection(4);
                dashboardDialog.dismiss();
                break;
            case R.id.nationalweather_imageButton:
                globalData.setDashboardSelection(5);
                dashboardDialog.dismiss();
                break;
        }
        dashboardDialog.dismiss();
    }
}



















