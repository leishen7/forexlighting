package com.nucome.app.forex;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ChartImageActivity extends ForexRateActivity  {
    private String TAG = ChartImageActivity.class.getSimpleName();
private String rateChartURL=null;

    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_image);
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(getString(R.string.INTENT_RateChartURL)) != null) {
            rateChartURL=intent.getStringExtra(getString(R.string.INTENT_RateChartURL));
         view = (ImageView) findViewById(R.id.chartImage);
           // view.setAdjustViewBounds(true);
            Picasso.with(view.getContext()).load(rateChartURL).into(view);
        }


    }


}
