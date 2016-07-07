package com.nucome.app.forex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RecommendationActivity extends UserInfoActivity {
    private String TAG = RecommendationActivity.class.getSimpleName();
    private String URL_RECOMMENDATION_INFO = "http://www.wuzhenweb.com:8089/json?operation=newtrade";
/*
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRecommendationListAdapter adapter;
    private ListView listView;
    private List<RecommendationInfo> recommendationList;
    private int offSet = 0;
*/
    private RadioGroup radioGroup;
    private EditText symbol;
    private EditText effectiveDate;
    private Button recommendButton;
    String side;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        symbol = (EditText) findViewById(R.id.recommendation_symbolEditText);
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(getString(R.string.INTENT_RECOMMEND)) != null) {
            symbol.setText(intent.getStringExtra(getString(R.string.INTENT_RECOMMEND)));
        }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.recommendation_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                side = String.valueOf(radioButton.getText());
            }
        });
        effectiveDate = (EditText) findViewById(R.id.recommendation_effectiveDaysEditText);
        recommendButton = (Button) findViewById(R.id.recommendation_Button);
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = URL_RECOMMENDATION_INFO;
                StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject recommendObj = new JSONObject(response.toString());
                                if ("104".equals(recommendObj.getString("errorCode"))) {
                                    Toast.makeText(getApplicationContext(), "This signal already recommended, please select a different one.",Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "this Recommendation has been recommended successfully.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Message", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                    @Override
                    public byte[] getBody()
                    {

                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try
                        {
                            jsonObject.put("symbol", symbol.getText());
                            jsonObject.put("side", side);
                            jsonObject.put("effectiveDays", effectiveDate.getText());
                            SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                            jsonObject.put(getString(R.string.JSON_TOKEN), pref.getString(getString(R.string.PREF_USER_TOKEN), null));

                            body = jsonObject.toString();
                        } catch (JSONException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        try
                        {
                            return body.toString().getBytes("utf-8");
                        } catch (UnsupportedEncodingException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                TradeApplication.getInstance().addToRequestQuest(req);
            }
        });
    }
}
