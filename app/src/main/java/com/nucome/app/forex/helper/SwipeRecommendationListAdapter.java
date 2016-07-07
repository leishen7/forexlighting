package com.nucome.app.forex.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.nucome.app.forex.R;

/**
 * Created by david on 4/29/2016.
 */
public class SwipeRecommendationListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RecommendationInfo> recommendationInfos;
    private String[] bgColors;

    public SwipeRecommendationListAdapter(Activity activity, List<RecommendationInfo> recommendationList) {
        this.activity = activity;
        this.recommendationInfos = recommendationList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return recommendationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return recommendationInfos.get(position);
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
            convertView = inflater.inflate(R.layout.recommendation_list_row, null);
        }
        TextView symbol = (TextView) convertView.findViewById(R.id.recommendationSymbol);
        TextView side = (TextView) convertView.findViewById(R.id.recommendationSide);
        TextView effectiveDate = (TextView) convertView.findViewById(R.id.recommendationEffectiveDate);
        TextView token = (TextView) convertView.findViewById((R.id.recommendationToken));

        symbol.setText(String.valueOf(recommendationInfos.get(position).symbol));
        side.setText(String.valueOf(recommendationInfos.get(position).side));
        effectiveDate.setText(String.valueOf(recommendationInfos.get(position).effectiveDays));
        token.setText(String.valueOf(recommendationInfos.get(position).token));
        String color = bgColors[position % bgColors.length];
        convertView.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
