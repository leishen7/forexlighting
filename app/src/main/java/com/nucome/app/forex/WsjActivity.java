package com.nucome.app.forex;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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

import com.nucome.app.forex.helper.NewsInfo;
import com.nucome.app.forex.helper.SwipeNewsListAdapter;

public class WsjActivity extends NewsActivity  {
    private String TAG = WsjActivity.class.getSimpleName();
    private String URL_NEWS_INFO = "http://www.wuzhenweb.com:8089/json?operation=getfxnews&item=wsj";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeNewsListAdapter adapter;
    private ListView listView;
    private List<NewsInfo> newsList;
    private int offSet = 0;
    private String fileName = "WsjData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = (ListView) findViewById(R.id.newsListView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        newsList = new ArrayList<>();
        adapter = new SwipeNewsListAdapter(this, newsList);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        String cachedResponse=readFromCache(fileName);
        if(cachedResponse.length()>0){
            swipeRefreshLayout.setRefreshing(true);
            parseResponse(cachedResponse,newsList);
            swipeRefreshLayout.setRefreshing(false);
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchNews(URL_NEWS_INFO);
            }
        });
    }
    @Override
    public void onRefresh() {
        fetchNews(URL_NEWS_INFO);
    }

    private void fetchNews(String url) {
        swipeRefreshLayout.setRefreshing(true);
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                saveToCache(response.toString(),fileName);
                parseResponse(response.toString(),newsList);
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
