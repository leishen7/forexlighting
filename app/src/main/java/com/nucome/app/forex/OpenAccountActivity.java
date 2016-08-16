package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OpenAccountActivity extends     AppCompatActivity {

    private final String TAG = OpenAccountActivity.this.getClass().getSimpleName();
   // private String URL_HOME = "http://www.midas-fx.com";
    private String URL_HOME = "http://www.wuzhenweb.com/forex/openaccount.html";
    private WebView webView;
    private int offSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(false);
        settings.setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheMaxSize( 1 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        // progressBar = ProgressDialog.show(TechnicalActivity.this, "\n" +"技术分析", "Loading");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                /*
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }*/
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);


                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e(TAG, "Error: " + String.valueOf(error.toString()));
                Toast.makeText(OpenAccountActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(error.toString());
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
        webView.loadUrl(URL_HOME);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trading, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.openAccount:
                Intent registerIntent = new Intent(getApplicationContext(), OpenAccountActivity.class);
                startActivity(registerIntent);
                return  true;
            case R.id.whyforex:
                Intent loginIntent = new Intent(getApplicationContext(), WhyForexActivity.class);
                startActivity(loginIntent);
                return  true;
            case R.id.knowforex:
                Intent calendarIntent = new Intent(getApplicationContext(), KnowForexActivity.class);
                startActivity(calendarIntent);
                return  true;
            case R.id.tradeforex:
                calendarIntent = new Intent(getApplicationContext(), TradeForexActivity.class);
                startActivity(calendarIntent);
                return  true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
