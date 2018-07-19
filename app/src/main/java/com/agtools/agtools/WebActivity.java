package com.agtools.agtools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    WebView webView;

    TransparentProgressDialog transparentProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url=getIntent().getStringExtra("url");
        webView=findViewById(R.id.webview);
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
               // transparentProgressDialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //transparentProgressDialog.dismiss();
                super.onPageFinished(view, url);
            }
        });
        NukeSSLCerts.NukeSSLCerts();
        webView.loadUrl(url);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(WebActivity.this, DashboardActivity.class);
                WebActivity.this.startActivity(mainIntent);
                WebActivity.this.finish();
            }
        }, 120000);
        //transparentProgressDialog.dismiss();
    }
}
