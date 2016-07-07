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

import com.nucome.app.forex.helper.SignalInfo;
import com.nucome.app.forex.helper.SwipeSignalListAdapter;

public class SignalActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = SignalActivity.class.getSimpleName();
    private String URL_SIGNAL_INFO = "http://www.wuzhenweb.com:8089/json?operation=getfxtrades";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeSignalListAdapter adapter;
    private ListView listView;
    private List<SignalInfo> signalList;
    private int offSet = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = (ListView) findViewById(R.id.newsListView);
     //   listView.addHeaderView(getLayoutInflater().inflate(findViewById(R.id.signal_head_layout,0,0)));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        signalList = new ArrayList<>();
        adapter = new SwipeSignalListAdapter(this, signalList);
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
        String url = URL_SIGNAL_INFO;
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if (response.length() > 0) {
                    try {
                    JSONObject signalObjs = new JSONObject(response.toString());
                    JSONArray signals = signalObjs.getJSONArray("content");
                        for (int i = 0; i < signals.length(); i++) {
                            JSONObject signalObj = signals.getJSONObject(i);
                            /*
                            String tradeId = "tradeId: " + signalObj.getString("tradeId");
                            String userId = "userId: " + signalObj.getString("userId");
                            String side = "side: " + signalObj.getString("side");
                            String title = "tradeId: " + signalObj.getString("tradeId");
                            String symbol = "symbol: " + signalObj.getString("symbol");
                            String entryPrice = "entryPrice: " + signalObj.getString("entryPrice");
                            String minPrice = "minPrice: " + signalObj.getString("minPrice");
                            String maxPrice = "maxPrice: " + signalObj.getString("maxPrice");
                            String expireDate = "expireDate: " + signalObj.getString("expireDate");
                            String createDate = "createDate: " + signalObj.getString("createDate");
                            String lastPrice = "lastPrice: " + signalObj.getString("lastPrice");
                            String maxGain = "maxGain: " + signalObj.getString("maxGain");
                            String maxLose = "maxLose: " + signalObj.getString("maxLose");
                            String avgGain = "avgGain: " + signalObj.getString("avgGain");
                            String avgLose = "avgLose: " + signalObj.getString("avgLose");
                            String profit = "profit: " + signalObj.getString("profit");
                            String userNickName = "userNickName: " + signalObj.getString("userNickName");
                            String gainPercent = "gainPercent: " + signalObj.getString("gainPercent");
                            String score = "score: " + signalObj.getString("score");
                            String chart = "chart: " + signalObj.getString("chart");
*/

                            String tradeId = signalObj.getString("tradeId");
                          //  String userId =  signalObj.getString("userId");
                            String side =  signalObj.getString("side");
                            String title = signalObj.getString("tradeId");
                            String symbol = signalObj.getString("symbol");
                            String entryPrice =  signalObj.getString("entryPrice");
                          //  String minPrice = signalObj.getString("minPrice");
                            //String maxPrice =  signalObj.getString("maxPrice");
                            String expireDate =  signalObj.getString("expireDate");
                            String createDate =  signalObj.getString("createDate");
                            String lastPrice =  signalObj.getString("lastPrice");
                       //     String maxGain = signalObj.getString("maxGain");
                         //   String maxLose = signalObj.getString("maxLose");
                            //String avgGain = signalObj.getString("avgGain");
                          //  String avgLose = signalObj.getString("avgLose");
                            String profit =signalObj.getString("profit");
                            String userNickName =  signalObj.getString("userNickName");
                         //   String gainPercent = signalObj.getString("gainPercent");
                          String reason =  signalObj.getString("reason");
                           String chart = signalObj.getString("chart");
                            SignalInfo info = new SignalInfo(tradeId, side, symbol, entryPrice,expireDate, createDate, lastPrice, profit, userNickName, reason, chart);
                                signalList.add(info);
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
