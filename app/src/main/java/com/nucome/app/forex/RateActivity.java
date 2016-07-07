package com.nucome.app.forex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.nucome.app.forex.helper.RateInfo;
import com.nucome.app.forex.helper.SwipeRealTimeRateListAdapter;

public class RateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = RateActivity.class.getSimpleName();
    private String URL_RATE_INFO =  "http://www.wuzhenweb.comjson?operation=getfxquote";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRealTimeRateListAdapter adapter;
    private ListView listView;
    private List<RateInfo> rateList;
    private int offSet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        rateList = new ArrayList<>();
        adapter = new SwipeRealTimeRateListAdapter(this, rateList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences preferences = getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                if (! preferences.contains(getString(R.string.PREF_USER_TOKEN))) {
                    Toast.makeText(getApplicationContext(), "Please Register or Login before Recommend", Toast.LENGTH_SHORT).show();
                } else {
                    RateInfo info = (RateInfo) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                    intent.putExtra(getString(R.string.INTENT_RECOMMEND), info.currency);
                    startActivity(intent);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchRates();
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchRates();
    }
    private void fetchRates() {
        swipeRefreshLayout.setRefreshing(true);
        String url = "http://www.wuzhenweb.com:8089/json?operation=getfxquote";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if (response.length() > 0) {
                    try {
                    JSONObject ratesObj = new JSONObject(response.toString());
                    JSONArray rates = ratesObj.getJSONArray("content");
                        for (int i = 0; i < rates.length(); i++) {
                                JSONObject rateObj = rates.getJSONObject(i);
                                String currency = rateObj.getString("symbol");
                                String rate = rateObj.getString("price");
                                String chartURL = rateObj.getString("chart");
                                RateInfo info = new RateInfo(currency, rate, chartURL);
                                rateList.add(info);
                        }
                    adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                    }

                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        TradeApplication.getInstance().addToRequestQuest(req);
    }
}
