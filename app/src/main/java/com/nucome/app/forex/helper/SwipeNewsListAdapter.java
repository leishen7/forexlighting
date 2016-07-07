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
public class SwipeNewsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsInfo> newsList;
    private String[] bgColors;

    public SwipeNewsListAdapter(Activity activity, List<NewsInfo> newsList) {
        this.activity = activity;
        this.newsList = newsList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
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
            convertView = inflater.inflate(R.layout.news_list_row, null);
        }
        TextView newsTitle = (TextView) convertView.findViewById(R.id.newsTitle);
        TextView newsDescription = (TextView) convertView.findViewById(R.id.newsDescription);
        TextView newsPubDate = (TextView) convertView.findViewById(R.id.newsPubDate);
        TextView newsSource = (TextView) convertView.findViewById((R.id.newsSource));

        newsTitle.setText(String.valueOf(newsList.get(position).title));
        newsDescription.setText(String.valueOf(newsList.get(position).description));
        newsPubDate.setText(String.valueOf(newsList.get(position).pubDate));
        newsSource.setText(String.valueOf(newsList.get(position).newsSource));
        String color = bgColors[position % bgColors.length];
        newsTitle.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
