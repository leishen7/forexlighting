package com.nucome.app.forex;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nucome.app.forex.helper.NewsInfo;
import com.nucome.app.forex.helper.SwipeNewsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FxStreetActivity extends NewsActivity {
    private String TAG = FxStreetActivity.class.getSimpleName();
    private String URL_ANALYSIS_INFO = "http://www.wuzhenweb.com:8089/json?operation=getfxnews&item=fxstreet";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeNewsListAdapter adapter;
    private ListView listView;
    private List<NewsInfo> newsList;
    private int offSet = 0;


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
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchNews();
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchNews();
    }
    private void fetchNews() {
        swipeRefreshLayout.setRefreshing(true);
        String url = URL_ANALYSIS_INFO;
                StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if (response.length() > 0) {
                    try {
                    JSONObject newsObjs = new JSONObject(response.toString());
                    JSONArray rates = newsObjs.getJSONArray("content");
                        for (int i = 0; i < rates.length(); i++) {
                                JSONObject newsObj = rates.getJSONObject(i);
                                String title = newsObj.getString("title");
                                String description = newsObj.getString("description");
                                String pubDate = newsObj.getString("pubDate");
                                String source = newsObj.getString("source");

                            NewsInfo info = new NewsInfo(title, description, pubDate, source);
                                newsList.add(info);
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
