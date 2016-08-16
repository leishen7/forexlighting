package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class CalendarActivity extends BaseActivity {
    private WebView webView;
    private final String TAG = CalendarActivity.this.getClass().getSimpleName();
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String calurl="http://www.wuzhenweb.com/forex/calendar.html";
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);
        this.webView = (WebView) findViewById(R.id.calendarWebView);
        WebSettings settings = webView.getSettings();
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
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if ( !isNetworkAvailable() ) { // loading offline
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //progressBar = ProgressDialog.show(CalendarActivity.this, "财经日历", "Loading");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
               // if (progressBar.isShowing()) {
                   // progressBar.dismiss();
               // }
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
                Toast.makeText(CalendarActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
        //webView.setWebViewClient(new MyWebViewClient());
        webView.setFocusableInTouchMode(false);
        webView.setFocusable(false);
        webView.setClickable(false);
        webView.loadUrl(calurl);
    }

    /* Class for webview client */
    class MyWebViewClient extends WebViewClient {

        // show the web page in webview but not in web browser
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if(url.contains("www.wuzhenweb.com")){
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);

            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
