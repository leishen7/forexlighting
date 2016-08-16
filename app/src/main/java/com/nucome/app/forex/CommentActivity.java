package com.nucome.app.forex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class CommentActivity extends TechnicalActivity {
    private String TAG = CommentActivity.class.getSimpleName();
    private String URL_COMMENT_INFO = "http://www.wuzhenweb.com:8089/json?operation=newcomment";


    private EditText symbol;
  //  private RadioGroup radioGroup;
    private EditText comment;
    private Button commentButton;
   // String side;
    Spinner symbolSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        symbol = (EditText) findViewById(R.id.commentSymbolEditText);
        comment = (EditText) findViewById(R.id.commentEditText);
        symbolSpin=(Spinner) findViewById(R.id.symbolspin);

        symbolSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                symbol.setText(symbolSpin.getSelectedItem().toString());
            }



            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(getString(R.string.INTENT_COMMENT)) != null) {
            symbol.setText(intent.getStringExtra(getString(R.string.INTENT_COMMENT)));
        }
      comment.setSelected(true);
        commentButton = (Button) findViewById(R.id.comment_Button);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(symbol==null || symbol.length()!=6){
                    Toast.makeText(getApplicationContext(), "The symbol is not correct",Toast.LENGTH_LONG).show();
                    return;
                }
                if(comment==null || comment.length()<5){
                    Toast.makeText(getApplicationContext(), "Please input comment",Toast.LENGTH_LONG).show();
                    return;
                }


                String url = URL_COMMENT_INFO;
                StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject recommendObj = new JSONObject(response.toString());
                                if (recommendObj.getString("errorCode")==null || "null".equals(recommendObj.getString("errorCode"))) {

                                    Toast.makeText(getApplicationContext(), "this comment has been recommended successfully.", Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(getApplicationContext(), "Failed to submit comment",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }
                            return;

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
                       // params.put("Content-Type","application/x-www-form-urlencoded");
                        params.put("Content-type", "application/json; charset=utf-8");
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
                            jsonObject.put("comment", comment.getText());
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
