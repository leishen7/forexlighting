package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class LearningSeminarActivity extends AppCompatActivity {
    private WebView webView;
    private final String TAG = LearningSeminarActivity.this.getClass().getSimpleName();
    private ProgressDialog progressBar;
    String url="http://www.midasfx.me/";
    //String url="http://www.wuzhenweb.com/forex/learningseminar.html";
   // String url="https://app.webinarjeo.com/node/webinar/view/6434-midasfx-weekly-webinar-2016-06-13?token=1335141b8fe40f9074b9533dd052c95b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_learning_seminar);
        this.webView = (WebView) findViewById(R.id.learningSeminarWebView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       // progressBar = ProgressDialog.show(LearningSeminarActivity.this, "外汇学堂", "Loading");
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
                if(url.contains("midasfx.")){
                //    Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);
                }else{
                    Uri webpage = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(Intent.createChooser(intent, "Chose browser"));
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e(TAG, "Error: " + String.valueOf(error.toString()));
                Toast.makeText(LearningSeminarActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learning, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.learning:
                Intent registerIntent = new Intent(getApplicationContext(), LearningActivity.class);
                startActivity(registerIntent);
                return  true;
            case R.id.learningseminar:
                Intent loginIntent = new Intent(getApplicationContext(), LearningSeminarActivity.class);
                startActivity(loginIntent);
                return  true;
            case R.id.learningknowledge:
                Intent calendarIntent = new Intent(getApplicationContext(), LearningKnowledgeActivity.class);
                startActivity(calendarIntent);
                return  true;
            case R.id.technical:
                Intent learningIntent = new Intent(getApplicationContext(), TechnicalActivity.class);
                startActivity(learningIntent);
                return  true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }


}
