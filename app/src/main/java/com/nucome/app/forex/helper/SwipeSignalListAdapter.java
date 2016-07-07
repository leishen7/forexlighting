package com.nucome.app.forex.helper;

import android.app.Activity;
import android.content.Context;
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
public class SwipeSignalListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<SignalInfo> signalList;
    private String[] bgColors;

    public SwipeSignalListAdapter(Activity activity, List<SignalInfo> signalList) {
        this.activity = activity;
        this.signalList = signalList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.rate_bg);
    }

    @Override
    public int getCount() {
        return signalList.size();
    }

    @Override
    public Object getItem(int position) {
        return signalList.get(position);
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
            convertView = inflater.inflate(R.layout.signal_list_row, null);
        }
       // TextView tradeId = (TextView) convertView.findViewById(R.id.signalTradeId);
        //TextView userId = (TextView) convertView.findViewById(R.id.signalUserId);
        TextView symbol = (TextView) convertView.findViewById(R.id.signalSymbol);
        TextView side = (TextView) convertView.findViewById(R.id.signalSide);
        TextView entryPrice = (TextView) convertView.findViewById((R.id.signalEntryPrice));
       // TextView minPrice = (TextView) convertView.findViewById((R.id.signalMinPrice));
       // TextView maxPrice = (TextView) convertView.findViewById((R.id.signalMaxPrice));
     //   TextView expireDate = (TextView) convertView.findViewById((R.id.signalExpireDate));
       // TextView createDate = (TextView) convertView.findViewById((R.id.signalCreateDate));
     //   TextView lastPrice = (TextView) convertView.findViewById((R.id.signalLastPrice));
       // TextView maxGain = (TextView) convertView.findViewById((R.id.signalMaxGain));
      //  TextView maxLose = (TextView) convertView.findViewById((R.id.signalMaxLose));
     //   TextView avgGain = (TextView) convertView.findViewById((R.id.signalAvgGain));
     //   TextView avyLose = (TextView) convertView.findViewById((R.id.signalAvgLose));
        TextView profit = (TextView) convertView.findViewById((R.id.signalProfit));
        TextView reason = (TextView) convertView.findViewById((R.id.reason));
        TextView userNickName = (TextView) convertView.findViewById((R.id.signalUserNickName));
      //  TextView gainPercent = (TextView) convertView.findViewById((R.id.signalGainPercent));
     //   TextView score = (TextView) convertView.findViewById((R.id.signalScore));
        ImageView chart = (ImageView) convertView.findViewById((R.id.signalChart));

     //   tradeId.setText(String.valueOf(signalList.get(position).tradeId));
     //   userId.setText(String.valueOf(signalList.get(position).userId));
        symbol.setText(String.valueOf(signalList.get(position).symbol));
        side.setText(String.valueOf(signalList.get(position).side));
        entryPrice.setText(String.valueOf(signalList.get(position).entryPrice));
     //   minPrice.setText(String.valueOf(signalList.get(position).minPrice));
     //   maxPrice.setText(String.valueOf(signalList.get(position).maxPrice));
     //   expireDate.setText(String.valueOf(signalList.get(position).expireDate));
     //   createDate.setText(String.valueOf(signalList.get(position).createDate));
     //   lastPrice.setText(String.valueOf(signalList.get(position).lastPrice));
     //   maxGain.setText(String.valueOf(signalList.get(position).maxGain));
     //   maxLose.setText(String.valueOf(signalList.get(position).maxLose));
     //   avgGain.setText(String.valueOf(signalList.get(position).avgGain));
     //   avyLose.setText(String.valueOf(signalList.get(position).avgLose));
        profit.setText(String.valueOf(signalList.get(position).profit));
        reason.setText(String.valueOf(signalList.get(position).reason));
        userNickName.setText(String.valueOf(signalList.get(position).userNickName));
       // gainPercent.setText(String.valueOf(signalList.get(position).gainPercent));
//        score.setText(String.valueOf(signalList.get(position).score));
        if (signalList.get(position).chartSrc != null) {
            Picasso.with(chart.getContext()).load(signalList.get(position).chartSrc.replace("chart: ", "")).into(chart);
        }

        String color = bgColors[position % bgColors.length];
  //      tradeId.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
