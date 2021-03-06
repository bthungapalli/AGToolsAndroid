package com.agtools.agtools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agtools.agtools.Constants.ApiConstants;

/**
 * Created by ranjithkumar.g on 4/17/2018.
 */

public class VolumeWebMonthActivity extends AppCompatActivity {
    WebView webView;

    TransparentProgressDialog transparentProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_web);
        webView=findViewById(R.id.webview);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        String commodity=getIntent().getStringExtra("commodity");
        NukeSSLCerts.NukeSSLCerts();
        String volumeurl= ApiConstants.API.concat("#/monthlyVolumeMobileReport/"+responce+"/"+commodity+"/1/0");
        transparentProgressDialog=new TransparentProgressDialog(this,R.drawable.sgg);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        // webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                transparentProgressDialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                transparentProgressDialog.dismiss();
                super.onPageFinished(view, url);
            }
        });
        NukeSSLCerts.NukeSSLCerts();
        webView.loadUrl(volumeurl);
        transparentProgressDialog.dismiss();
    }

}