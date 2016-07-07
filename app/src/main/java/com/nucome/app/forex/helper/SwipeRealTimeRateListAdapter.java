package com.nucome.app.forex.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.nucome.app.forex.R;

/**
 * Created by david on 4/29/2016.
 */
public class SwipeRealTimeRateListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RateInfo> rateList;
    private String[] bgColors;

    public SwipeRealTimeRateListAdapter(Activity activity, List<RateInfo> rateList) {
        this.activity = activity;
        this.rateList = rateList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return rateList.size();
    }

    @Override
    public Object getItem(int position) {
        return rateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rate_list_row, null);
        }
        TextView curreny = (TextView) convertView.findViewById(R.id.currency);
        //TextView rate = (TextView) convertView.findViewById(R.id.rate);
        ImageView chart = (ImageView) convertView.findViewById(R.id.rateChart);
        curreny.setText(String.valueOf(rateList.get(position).currency));
       // rate.setText(String.valueOf(rateList.get(position).rate));
        Picasso.with(chart.getContext()).load(rateList.get(position).rateChartURL).into(chart);

        String color = bgColors[position % bgColors.length];
        curreny.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
