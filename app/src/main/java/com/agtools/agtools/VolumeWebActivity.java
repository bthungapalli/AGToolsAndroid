package com.agtools.agtools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agtools.agtools.Constants.ApiConstants;

public class VolumeWebActivity extends AppCompatActivity {
    WebView webView;

    TransparentProgressDialog transparentProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_web);
        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("USERID", Context.MODE_PRIVATE);
        final String responce=sharedPreferences1.getString("userid","");
        Log.d("userid","userid"+responce);
        String commodity=getIntent().getStringExtra("commodity");
        webView=findViewById(R.id.webview);
        String volumeurl= ApiConstants.API.concat("#/weeklyVolumeMobileReport/"+responce+"/"+commodity+"/1/0");
        transparentProgressDialog=new TransparentProgressDialog(this,R.drawable.sgg);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
       // webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
       /* webView.setWebViewClient(new WebViewClient() {
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
        transparentProgressDialog.dismiss();*/
        webView.setWebViewClient(new SSLTolerentWebViewClient());
        transparentProgressDialog.show();
        webView.loadUrl(volumeurl);
        transparentProgressDialog.dismiss();
    }

    private class SSLTolerentWebViewClient extends WebViewClient {
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

            AlertDialog.Builder builder = new AlertDialog.Builder(VolumeWebActivity.this);
            AlertDialog alertDialog = builder.create();
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }

            message += " Do you want to continue anyway?";
            alertDialog.setTitle("SSL Certificate Error");
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Ignore SSL certificate errors
                    handler.proceed();
                   // transparentProgressDialog.show();
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    handler.cancel();
                }
            });
            alertDialog.show();
        }
    }
}
