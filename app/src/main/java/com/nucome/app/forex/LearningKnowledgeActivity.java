package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nucome.app.websdk.DMWebVideoView;

public class LearningKnowledgeActivity extends AppCompatActivity {
    private DMWebVideoView mVideoView;
    //private WebView webView;

    //String videoURL="https://www.dropbox.com/s/0ghi2gs5nfldtl8/midas_2016-06-06.mp4?dl=0";
     String videoURL="http://www.wuzhenweb.com/forex/learningknowledge.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_learning);
        mVideoView = ((DMWebVideoView) findViewById(R.id.dmWebVideoView));
        mVideoView.setVideoId(videoURL);
        mVideoView.setAutoPlay(true);


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
    public void onBackPressed() {
        mVideoView.handleBackPress(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mVideoView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mVideoView.onResume();
        }
    }

}
