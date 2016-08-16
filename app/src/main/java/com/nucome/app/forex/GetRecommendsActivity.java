package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class GetRecommendsActivity extends TechnicalActivity {
    private String TAG = GetRecommendsActivity.this.getClass().getSimpleName();
    private WebView webView;
    private ProgressDialog progressBar;
    private Button recommendButton;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
        userToken = pref.getString(getString(R.string.PREF_USER_TOKEN), null);
        setContentView(R.layout.activity_get_recommends);
        String url="http://www.wuzhenweb.com/js/index.html";
        this.webView = (WebView) findViewById(R.id.recommentsWebView);
        WebSettings settings = webView.getSettings();
        webView.getSettings().setAppCacheMaxSize( 1 * 1024 * 1024 ); // 5MB
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       // progressBar = ProgressDialog.show(ForexRateActivity.this, "\n" +"实时汇率", "Loading");
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
                if(url.contains("www.wuzhenweb.com")){
                    Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);

                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e(TAG, "Error: " + String.valueOf(error.toString()));
                Toast.makeText(GetRecommendsActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
        webView.loadUrl(url);

        recommendButton = (Button) findViewById(R.id.btnNewRecommend);
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userToken == null || userToken.length() < 12) {
                    Toast.makeText(GetRecommendsActivity.this, "请先注册,你才可以推荐", Toast.LENGTH_LONG).show();
                  //  Toast.makeText(GetRecommendsActivity.this, "你就可以推荐外汇交易", Toast.LENGTH_LONG).show();

                    return;
                }
                Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                startActivity(intent);
            }


        });

      }


}
